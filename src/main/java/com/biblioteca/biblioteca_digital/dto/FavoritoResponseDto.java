package com.biblioteca.biblioteca_digital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoResponseDto {
	
	private Integer id;
    private Integer libroId;
    private String titulo;
    private String autor;

}
