package com.ecommerce.server.service;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoCompra(
            String destinatario,
            String resumenCompra,
            String codigoTracking
    ) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("palex2143@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("¡Gracias por tu compra! - Tu código de seguimiento");
        mensaje.setText(
                "Detalles de tu compra:\n" + resumenCompra +
                        "\n\nCódigo de seguimiento: " + codigoTracking +
                        "\nPuedes rastrear tu pedido aquí: https://tutienda.com/track/" + codigoTracking
        );
        mailSender.send(mensaje);
    }

    public void enviarCorreoCompraHtml(
            String destinatario,
            String resumenCompra, // Este resumen ya contiene las imágenes de todos los productos
            String codigoTracking
    ) throws Exception {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
        helper.setFrom("dev.alexander.estrada@gmail.com");
        helper.setTo(destinatario);
        helper.setSubject("¡Gracias por tu compra! - Tu código de seguimiento");
        helper.setText(
                "<h2>Detalles de tu compra</h2>"
                        + resumenCompra
                        + "<b>Código de seguimiento:</b> " + codigoTracking + "<br>"
                        + "<a href=\"http://localhost:5173/tracking/" + codigoTracking + "\">Rastrear tu pedido</a>",
                true // Para indicar que es HTML
        );
        mailSender.send(mensaje);
    }

}