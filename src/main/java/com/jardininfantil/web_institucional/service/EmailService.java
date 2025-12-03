package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.models.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(Object data) {
        Usuario registroData = (Usuario) data;

        String recipientEmail = registroData.getCorreo();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Cuenta creada Jardín Infantil");
        message.setText(
            String.format(
                "Hola,\n\nGracias por registrarte en el Centro educativo Jardín Infantil Carrusel Aventuras. Ya puedes solicitar una reserva."
            )
        );

        mailSender.send(message);
        log.info("✅ Correo de verificación ENVIADO a: {}", recipientEmail);
    }
}
