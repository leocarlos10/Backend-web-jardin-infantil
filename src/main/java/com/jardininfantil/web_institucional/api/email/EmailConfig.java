package com.jardininfantil.web_institucional.api.email;

import com.jardininfantil.web_institucional.api.email.provider.MailtrapEmailProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${email.provider}")
    private String providerName;

    @Value("${email.mailtrap.token:}")
    private String mailtrapToken;

    @Value("${email.mailtrap.inbox-id:0}")
    private Long mailtrapInboxId;

    @Value("${email.mailtrap.from-email:}")
    private String mailtrapFromEmail;

    @Value("${email.mailtrap.from-name:}")
    private String mailtrapFromName;

    @Bean
    public EmailProvider emailProvider() {

        return switch (providerName) {
            case "mailtrap" -> new MailtrapEmailProvider(
                    mailtrapToken,
                    mailtrapInboxId,
                    mailtrapFromEmail,
                    mailtrapFromName
            );
            default ->
                throw new RuntimeException("Proveedor no soportado: " + providerName);
        };
    }
}
