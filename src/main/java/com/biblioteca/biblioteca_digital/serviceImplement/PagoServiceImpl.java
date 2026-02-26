package com.biblioteca.biblioteca_digital.serviceImplement;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.dto.PagoRequestDTO;
import com.biblioteca.biblioteca_digital.dto.PagoResponseDTO;
import com.biblioteca.biblioteca_digital.enums.EstadoPago;
import com.biblioteca.biblioteca_digital.enums.EstadoSuscripcion;
import com.biblioteca.biblioteca_digital.enums.MetodoPago;
import com.biblioteca.biblioteca_digital.model.Pago;
import com.biblioteca.biblioteca_digital.model.Suscripcion;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.PagoRepository;
import com.biblioteca.biblioteca_digital.repository.SuscripcionRepository;
import com.biblioteca.biblioteca_digital.service.PagoService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService{
	
	private final PagoRepository pagoRepository;
    private final SuscripcionRepository suscripcionRepository;

    private static final Double MONTO = 15.0;

	@Override
	public PagoResponseDTO procesarPago(PagoRequestDTO request, Usuario usuario) {
		
		Pago pago = new Pago();
        pago.setUsuario(usuario);
        pago.setMetodoPago(request.getMetodoPago());
        pago.setMonto(MONTO);
        pago.setFechaPago(LocalDateTime.now());
        pago.setCodigoOperacion("OP-" + (100000 + new Random().nextInt(900000)));

        boolean aprobado = false;

        // 🔵 BCP
        if (request.getMetodoPago() == MetodoPago.BCP) {
            aprobado = validarTarjeta(request);
        }

        // 🟣 YAPE
        if (request.getMetodoPago() == MetodoPago.YAPE) {
            aprobado = validarYape(request);
        }

        pago.setEstado(aprobado ? EstadoPago.APROBADO : EstadoPago.RECHAZADO);
        pagoRepository.save(pago);

        if (aprobado) {
            Suscripcion suscripcion = activarOReactivar(usuario);

            return PagoResponseDTO.builder()
                    .exito(true)
                    .mensaje("Pago exitoso")
                    .codigoOperacion(pago.getCodigoOperacion())
                    .fechaVencimiento(suscripcion.getFechaFin())
                    .monto(MONTO)
                    .estado(EstadoPago.APROBADO)
                    .build();
        }

        return PagoResponseDTO.builder()
                .exito(false)
                .mensaje("Pago rechazado")
                .estado(EstadoPago.RECHAZADO)
                .build();
    }

    private boolean validarTarjeta(PagoRequestDTO request) {
        if (request.getNumeroTarjeta() == null || request.getNumeroTarjeta().length() != 16)
            return false;

        if (request.getCvv() == null || request.getCvv().length() != 3)
            return false;

        // 90% probabilidad aprobación
        return Math.random() <= 0.9;
    }

    private boolean validarYape(PagoRequestDTO request) {
        return request.getCodigo() != null && request.getCodigo().equals("123456");
    }

    private Suscripcion activarOReactivar(Usuario usuario) {

        Suscripcion suscripcion = suscripcionRepository
                .findByUsuarioId(usuario.getId())
                .orElse(null);

        LocalDateTime ahora = LocalDateTime.now();

        if (suscripcion == null) {
            suscripcion = new Suscripcion();
            suscripcion.setUsuario(usuario);
            suscripcion.setFechaInicio(ahora);
            suscripcion.setFechaFin(ahora.plusMonths(1));
        } else {
            suscripcion.setFechaFin(ahora.plusMonths(1));
        }

        suscripcion.setEstado(EstadoSuscripcion.ACTIVA);
        return suscripcionRepository.save(suscripcion);
	}

	@Override
	public byte[] generarComprobante(Long pagoId) {
		
		Pago pago = pagoRepository.findById(pagoId).orElseThrow();

	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);

	    document.add(new Paragraph("📚 BIBLIOTECA DIGITAL"));
	    document.add(new Paragraph(" "));
	    document.add(new Paragraph("Usuario: " + pago.getUsuario().getNombre()));
	    document.add(new Paragraph("Email: " + pago.getUsuario().getEmail()));
	    document.add(new Paragraph("Método: " + pago.getMetodoPago()));
	    document.add(new Paragraph("Monto: S/ 15.00"));
	    document.add(new Paragraph("Fecha Pago: " + pago.getFechaPago()));
	    document.add(new Paragraph("Código Operación: " + pago.getCodigoOperacion()));
	    document.add(new Paragraph("Estado: " + pago.getEstado()));

	    document.close();
	    return baos.toByteArray();
	}



}
