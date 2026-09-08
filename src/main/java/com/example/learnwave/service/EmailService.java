package com.example.learnwave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    /**
     * Envia o email de recuperação de senha com o link contendo o token.
     */
    public void enviarEmailRecuperacaoSenha(String destinatario, String nome, String token) {
        String link = frontendUrl + "/redefinir-senha?token=" + token;

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(destinatario);
        mensagem.setSubject("LearnWave - Recuperação de senha");
        mensagem.setText(
            "Olá, " + (nome != null ? nome : "") + "!\n\n" +
            "Recebemos uma solicitação para redefinir a senha da sua conta no LearnWave.\n\n" +
            "Clique no link abaixo para criar uma nova senha:\n" +
            link + "\n\n" +
            "Este link é válido por 1 hora.\n\n" +
            "Se você não solicitou a redefinição de senha, ignore este email. " +
            "Sua senha atual continuará funcionando normalmente.\n\n" +
            "Atenciosamente,\n" +
            "Equipe LearnWave"
        );

        mailSender.send(mensagem);
    }
}
