package com.biblioteca.biblioteca_digital.controller;

import java.io.File;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biblioteca.biblioteca_digital.enums.TipoLibro;
import com.biblioteca.biblioteca_digital.model.Categoria;
import com.biblioteca.biblioteca_digital.model.Libro;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.LibroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {
	
	private final LibroService libroService;
	private final UsuarioRepository usuarioRepository;
	
	@GetMapping
    public List<Libro> listar() {
        return libroService.listar();
    }

    @GetMapping("/{id}")
    public Libro obtener(@PathVariable Integer id) {
        return libroService.obtener(id);
    }

    @GetMapping("/buscar")
    public List<Libro> buscar(@RequestParam String titulo) {
        return libroService.buscar(titulo);
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public Libro crear(
            @RequestParam("titulo") String titulo,
            @RequestParam("autor") String autor,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("tipo") String tipo,
            @RequestParam("categoriaId") Integer categoriaId,
            @RequestParam("portada") MultipartFile portada,
            @RequestParam("archivo") MultipartFile archivo,
            Authentication authentication
    ) throws Exception {

        // Crear carpetas si no existen
        File carpetaPortadas = new File("uploads/portadas");
        File carpetaPdfs = new File("uploads/pdfs");
        carpetaPortadas.mkdirs();
        carpetaPdfs.mkdirs();

        // Generar nombres únicos
        String nombrePortada = System.currentTimeMillis() + "_" + portada.getOriginalFilename();
        String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();

        // Guardar archivos
        File archivoPortada = new File("uploads/portadas/" + nombrePortada);
        File archivoPdf = new File("uploads/pdfs/" + nombreArchivo);

        portada.transferTo(archivoPortada);
        archivo.transferTo(archivoPdf);

        // Crear objeto libro
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setDescripcion(descripcion);
        libro.setTipo(TipoLibro.valueOf(tipo));
        libro.setPortadaUrl("/uploads/portadas/" + nombrePortada);
        libro.setArchivoUrl("/uploads/pdfs/" + nombreArchivo);

        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        libro.setCategoria(categoria);

        return libroService.crear(libro, authentication.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public Libro actualizar(@PathVariable Integer id,
                            @RequestBody Libro libro) {

        return libroService.actualizar(id, libro);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public String eliminar(@PathVariable Integer id) {

        libroService.eliminar(id);
        return "Libro eliminado correctamente";
    }
    
    //se valida si esta suscrito o no para leer libros si es premiun
    @GetMapping("/{id}/leer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> leerLibro(
            @PathVariable Integer id,
            Authentication authentication
    ) {
    	String email = authentication.getName();
    	
    	Usuario usuario = usuarioRepository.findByEmail(email)
    	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String archivo = libroService.leerLibro(id, usuario.getId());

        return ResponseEntity.ok(archivo);
    }

}
