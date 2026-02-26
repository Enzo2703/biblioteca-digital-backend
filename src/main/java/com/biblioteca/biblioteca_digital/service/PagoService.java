package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.dto.PagoRequestDTO;
import com.biblioteca.biblioteca_digital.dto.PagoResponseDTO;
import com.biblioteca.biblioteca_digital.model.Usuario;

public interface PagoService {
	
	PagoResponseDTO procesarPago(PagoRequestDTO request, Usuario usuario);

    byte[] generarComprobante(Long pagoId);
}
