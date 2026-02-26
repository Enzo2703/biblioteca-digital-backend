package com.biblioteca.biblioteca_digital.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca_digital.dto.PagoRequestDTO;
import com.biblioteca.biblioteca_digital.dto.PagoResponseDTO;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.PagoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('READER')")
public class PagoController {
	
	private final PagoService pagoService;
    private final UsuarioRepository usuarioRepository;
    
    @PostMapping
    public ResponseEntity<PagoResponseDTO> pagar(
            @RequestBody PagoRequestDTO request,
            Authentication authentication) {

        Usuario usuario = usuarioRepository
                .findByEmail(authentication.getName())
                .orElseThrow();

        return ResponseEntity.ok(
                pagoService.procesarPago(request, usuario)
        );
    }

    @GetMapping("/{id}/comprobante")
    public ResponseEntity<byte[]> descargar(@PathVariable Long id) {

        byte[] pdf = pagoService.generarComprobante(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=comprobante.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

	
}
