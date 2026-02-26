package com.biblioteca.biblioteca_digital.model;

import java.time.LocalDateTime;

import com.biblioteca.biblioteca_digital.enums.EstadoPago;
import com.biblioteca.biblioteca_digital.enums.MetodoPago;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagos")
public class Pago {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoOperacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private Double monto;

    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    private EstadoPago estado;

}
