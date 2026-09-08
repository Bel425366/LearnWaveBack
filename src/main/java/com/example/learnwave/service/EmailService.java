package com.example.learnwave.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String resendApiKey;

    @Value("${resend.from.email}")
    private String remetente;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private static final String RESEND_URL = "https://api.resend.com/emails";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Envia o email de recuperação de senha com o link contendo o token,
     * usando a API HTTP do Resend (porta 443, não bloqueada pelo Render).
     */
    public void enviarEmailRecuperacaoSenha(String destinatario, String nome, String token) {
        String link = frontendUrl + "/redefinir-senha?token=" + token;

        String corpoHtml =
            "<div style=\"font-family: Arial, sans-serif; max-width: 500px; margin: 0 auto;\">" +
            "<h2 style=\"color: #6366F1;\">LearnWave - Recuperação de senha</h2>" +
            "<p>Olá, " + (nome != null ? nome : "") + "!</p>" +
            "<p>Recebemos uma solicitação para redefinir a senha da sua conta no LearnWave.</p>" +
            "<p>Clique no botão abaixo para criar uma nova senha:</p>" +
            "<p style=\"text-align: center; margin: 30px 0;\">" +
            "<a href=\"" + link + "\" style=\"background-color: #6366F1; color: #fff; padding: 12px 24px; " +
            "text-decoration: none; border-radius: 6px; display: inline-block;\">Redefinir minha senha</a>" +
            "</p>" +
            "<p style=\"color: #666; font-size: 13px;\">Este link é válido por 1 hora.</p>" +
            "<p style=\"color: #666; font-size: 13px;\">Se você não solicitou a redefinição de senha, " +
            "ignore este email. Sua senha atual continuará funcionando normalmente.</p>" +
            "<hr style=\"border: none; border-top: 1px solid #eee; margin: 20px 0;\">" +
            "<p style=\"color: #999; font-size: 12px;\">Equipe LearnWave</p>" +
            "</div>";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(resendApiKey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("from", "LearnWave <" + remetente + ">");
        payload.put("to", new String[]{destinatario});
        payload.put("subject", "LearnWave - Recuperação de senha");
        payload.put("html", corpoHtml);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> resposta = restTemplate.postForEntity(RESEND_URL, request, String.class);
        System.out.println("Resposta do Resend: " + resposta.getStatusCode() + " - " + resposta.getBody());
    }
}
