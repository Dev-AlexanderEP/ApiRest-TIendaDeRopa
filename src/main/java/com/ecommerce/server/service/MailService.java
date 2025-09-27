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

    public void enviarCodigoVerificacionHtml(
            String destinatario,
            String codigoVerificacion
    ) throws Exception {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setFrom("dev.alexander.estrada@gmail.com");
        helper.setTo(destinatario);
        helper.setSubject("Tu código de verificación");

        // Construir el HTML del correo
        String html = """
            <div style="font-family:Arial,sans-serif;line-height:1.5;color:#333">
                <h2 style="color:#7c3aed;margin:0 0 12px">Verificación de seguridad</h2>
                <p>Usa este código para completar tu proceso de verificación:</p>
                <div style="font-size:28px;font-weight:700;
                            letter-spacing:6px;padding:12px 16px;
                            border:1px solid #eee;display:inline-block;
                            margin:16px 0;color:#000">
                    %s
                </div>
                <p style="color:#666">Este código caduca en 10 minutos.<br>
                   Si no lo solicitaste, puedes ignorar este mensaje.</p>
            </div>
            """.formatted(codigoVerificacion);

        helper.setText(html, true); // true → HTML
        mailSender.send(mensaje);
    }


}