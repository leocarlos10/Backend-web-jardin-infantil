package com.jardininfantil.web_institucional.api.email;

public interface EmailProvider {
    void sendEmail(String to, String subject, String text) throws Exception;
}
