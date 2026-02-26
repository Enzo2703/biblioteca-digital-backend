package com.biblioteca.biblioteca_digital.dto;

import java.time.LocalDateTime;

import com.biblioteca.biblioteca_digital.enums.EstadoPago;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagoResponseDTO {
	
	private boolean exito;
    private String mensaje;
    private String codigoOperacion;
    private LocalDateTime fechaVencimiento;
    private Double monto;
    private EstadoPago estado;

}
