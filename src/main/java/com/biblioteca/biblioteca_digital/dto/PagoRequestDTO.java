package com.biblioteca.biblioteca_digital.dto;

import com.biblioteca.biblioteca_digital.enums.MetodoPago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoRequestDTO {

	private MetodoPago metodoPago;

    // BCP
    private String numeroTarjeta;
    private String titular;
    private String fechaExp;
    private String cvv;

    // Yape
    private String celular;
    private String codigo;
}
