package com.biblioteca.biblioteca_digital.model;

import java.time.LocalDateTime;
import java.util.List;

import com.biblioteca.biblioteca_digital.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String password;

    private String estado = "A"; // activo por defecto

    
    @Column(name = "fecha_registro", updatable = false)
    private java.time.LocalDateTime fechaRegistro = java.time.LocalDateTime.now();
    /*@Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }*/
    
   //Esto maneja las suscripciones por cada usuario
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Suscripcion> suscripciones;

    //agregado recientemente:
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
