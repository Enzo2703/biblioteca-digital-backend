package com.biblioteca.biblioteca_digital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca_digital.serviceImplement.EmailSenderServiceImplement;

@RestController
@RequestMapping("/api/correo")
public class CorreoController {
	@Autowired
    private EmailSenderServiceImplement emailSender;

    @GetMapping("/enviar")
    public String enviarCorreoDePrueba() {
        emailSender.enviarCorreo(
            "luzdanicabanillas18@gmail.com", 
            "Prueba de correo desde Spring Boot",
            "¡Hola! Este es un correo de prueba enviado desde tu app con Spring Boot y Gmail."
        );
        return "Correo enviado correctamente";
    }

}
