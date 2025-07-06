package com.ecommerce.server.controller;

import com.ecommerce.server.model.entity.envio.Envio;
import com.ecommerce.server.service.MailService;
import com.ecommerce.server.service.envio.IEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe"
})
@RestController
@RequestMapping("/api/v1")
public class MailController {

    @Autowired
    private IEnvioService envioService;

    @Autowired
    private MailService mailService;

    @GetMapping("/registrar")
    public ResponseEntity<?> registrarOrden(@RequestParam Long id) {
        Envio envio = envioService.getEnvio(id);

        String emailUsuario = envio.getDatosPersonales().getEmail();

        // Construir resumen HTML
        StringBuilder resumenHtml = new StringBuilder();
        resumenHtml.append("<h2 style='color:#7c3aed;'>Detalles de tu compra</h2>");
        resumenHtml.append("<b>Cliente:</b> ")
                .append(envio.getDatosPersonales().getNombres()).append(" ")
                .append(envio.getDatosPersonales().getApellidos()).append("<br>");
        resumenHtml.append("<b>Dirección:</b> ")
                .append(envio.getDatosPersonales().getCalle()).append(", ")
                .append(envio.getDatosPersonales().getDetalle()).append("<br>");
        resumenHtml.append("<b>Teléfono:</b> ")
                .append(envio.getDatosPersonales().getTelefono()).append("<br><br>");

        // Tabla de productos
        resumenHtml.append("<b>Productos comprados:</b>");
        resumenHtml.append("<table style='width:100%; border-collapse:collapse; margin: 16px 0;'>")
                .append("<thead style='background:#f3f3f3;'>")
                .append("<tr style='color:#7c3aed;'>")
                .append("<th style='border:1px solid #ccc; padding:8px;'>Imagen</th>")
                .append("<th style='border:1px solid #ccc; padding:8px;'>Producto</th>")
                .append("<th style='border:1px solid #ccc; padding:8px;'>Talla</th>")
                .append("<th style='border:1px solid #ccc; padding:8px;'>Cantidad</th>")
                .append("<th style='border:1px solid #ccc; padding:8px;'>Precio unitario</th>")
                .append("</tr></thead><tbody>");

        envio.getVenta().getDetalles().forEach(detalle -> {
            String urlImagen = detalle.getPrenda().getImagen().getPrincipal();
            if (!urlImagen.startsWith("http")) {
                urlImagen = "http://localhost:8080/" + urlImagen;
            }
            resumenHtml.append("<tr>")
                    .append("<td style='border:1px solid #ccc; padding:8px; text-align:center;'>")
                    .append("<img src='").append(urlImagen).append("' alt='Producto' width='60'></td>")
                    .append("<td style='border:1px solid #ccc; padding:8px;'>")
                    .append(detalle.getPrenda().getNombre()).append("</td>")
                    .append("<td style='border:1px solid #ccc; padding:8px;'>")
                    .append(detalle.getTalla().getNomTalla()).append("</td>")
                    .append("<td style='border:1px solid #ccc; padding:8px;'>")
                    .append(detalle.getCantidad()).append("</td>")
                    .append("<td style='border:1px solid #ccc; padding:8px;'>S/. ")
                    .append(detalle.getPrecioUnitario()).append("</td>")
                    .append("</tr>");
        });
        resumenHtml.append("</tbody></table>");

        double total = envio.getVenta().getDetalles().stream()
                .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                .sum();

        resumenHtml.append("<br><b>Total:</b> S/. ").append(total).append("<br>");
        resumenHtml.append("<b>Costo de envío:</b> S/. ").append(envio.getCostoEnvio()).append("<br>");
        resumenHtml.append("<b>Método de envío:</b> ").append(envio.getMetodoEnvio()).append("<br>");
        resumenHtml.append("<b>Estado:</b> ").append(envio.getEstado()).append("<br>");
        resumenHtml.append("<b>Tracking:</b> ").append(envio.getTrackingNumber()).append("<br>");
        resumenHtml.append("<br><b>Código de seguimiento:</b> ").append(envio.getTrackingNumber()).append("<br>");
        resumenHtml.append("<a href='https://tutienda.com/track/")
                .append(envio.getTrackingNumber())
                .append("' style='color:#7c3aed; text-decoration:underline;'>Rastrear tu pedido aquí</a>");

        try {
            mailService.enviarCorreoCompraHtml(
                    emailUsuario,
                    resumenHtml.toString(),
                    envio.getTrackingNumber()
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error enviando correo: " + e.getMessage());
        }

        return ResponseEntity.ok("Correo enviado correctamente");
    }
}