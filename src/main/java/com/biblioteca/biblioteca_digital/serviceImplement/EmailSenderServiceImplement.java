package com.biblioteca.biblioteca_digital.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImplement {
	
	 @Autowired
	    private JavaMailSender mailSender;

	    public void enviarCorreo(String to, String asunto, String cuerpo) {
	        SimpleMailMessage mensaje = new SimpleMailMessage();
	        mensaje.setFrom("luzdanicabanillas18@gmail.com");
	        mensaje.setTo(to);
	        mensaje.setSubject(asunto);
	        mensaje.setText(cuerpo);
	        mailSender.send(mensaje);
	    }

}
