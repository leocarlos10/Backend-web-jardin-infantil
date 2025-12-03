package com.jardininfantil.web_institucional.api.email;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceApi {

    private final EmailProvider provider;
    
    public void send(String to, String subject, String body) throws Exception {
        provider.sendEmail(to, subject, body);
    }
}
