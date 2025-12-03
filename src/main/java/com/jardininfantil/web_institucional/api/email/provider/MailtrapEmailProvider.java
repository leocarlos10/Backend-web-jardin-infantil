package com.jardininfantil.web_institucional.api.email.provider;

import com.jardininfantil.web_institucional.api.email.EmailProvider;
import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;

import java.util.List;

public class MailtrapEmailProvider implements EmailProvider {

    private final MailtrapClient client;
    private final String fromEmail;
    private final String fromName;

    public MailtrapEmailProvider(String token,
                                 Long inboxId,
                                 String fromEmail,
                                 String fromName) {

        this.fromEmail = fromEmail;
        this.fromName = fromName;

        MailtrapConfig config = new MailtrapConfig.Builder()
                .sandbox(true)
                .inboxId(inboxId)
                .token(token)
                .build();

        this.client = MailtrapClientFactory.createMailtrapClient(config);
    }

    @Override
    public void sendEmail(String to, String subject, String text) throws Exception {

        MailtrapMail mail = MailtrapMail.builder()
                .from(new Address(fromEmail, fromName))
                .to(List.of(new Address(to)))
                .subject(subject)
                .text(text)
                .category("Transactional")
                .build();

        client.send(mail);
    }
}
