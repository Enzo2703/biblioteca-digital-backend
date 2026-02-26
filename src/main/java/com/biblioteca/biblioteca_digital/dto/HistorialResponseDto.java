package com.biblioteca.biblioteca_digital.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistorialResponseDto {
	
	private Integer id;
    private Integer libroId;
    private String titulo;
    private String autor;
    private LocalDateTime fechaAcceso;
}
