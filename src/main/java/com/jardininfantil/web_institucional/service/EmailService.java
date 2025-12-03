package com.jardininfantil.web_institucional.service;

import com.jardininfantil.web_institucional.api.email.EmailServiceApi;
import com.jardininfantil.web_institucional.models.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailServiceApi emailService; 

    public void sendVerificationEmail(Object data) {
        Usuario registroData = (Usuario) data;
        String correo = registroData.getCorreo();


        String subject = "Cuenta creada Jardín Infantil";
        String body = """
                Hola,

                Gracias por registrarte en el Centro educativo Jardín Infantil Carrusel Aventuras.
                Ya puedes solicitar una reserva.
                """;

        try {
            emailService.send(correo, subject, body);
            log.info("✅ Email enviado a {}", correo);
        } catch (Exception e) {
            log.error("❌ Error enviando email a {}", correo, e);
        }
    }
}
