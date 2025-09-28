package com.ecommerce.server.config.seeders.envio;

import com.ecommerce.server.model.dao.envio.EnvioDao;
import com.ecommerce.server.model.dao.envio.DatosPersonalesDao;
import com.ecommerce.server.model.dao.venta.VentaDao;
import com.ecommerce.server.model.entity.envio.Envio;
import com.ecommerce.server.model.entity.envio.DatosPersonales;
import com.ecommerce.server.model.entity.venta.Venta;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.security.SecureRandom;
import java.time.LocalDate;

@Component
@Order(17)
@RequiredArgsConstructor
public class XVII_EnvioSeeder implements CommandLineRunner {

    private final EnvioDao envioDao;
    private final VentaDao ventaDao;
    private final DatosPersonalesDao datosDao;

    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RNG = new SecureRandom();

    private static String generateTracking(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) sb.append(ALPHANUM.charAt(RNG.nextInt(ALPHANUM.length())));
        return sb.toString();
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (envioDao.count() != 0) {
            System.out.println("⚠️ Seeder Envío omitido: ya existen registros.");
            return;
        }

        // ⚠️ Usa getReferenceById para evitar entidades detached si ya existen
        // (si prefieres, puedes usar findById(...) pero dentro de @Transactional ya queda managed)
        Venta venta = ventaDao.findById(1L).orElseThrow();
        DatosPersonales datos = datosDao.findById(1L).orElseThrow();

        LocalDate fechaEnvio = LocalDate.now();
        LocalDate fechaEntrega = fechaEnvio.plusDays(10);
        String tracking = generateTracking(10);

        Envio envio = Envio.builder()
                .venta(venta)
                .datosPersonales(datos)
                .costoEnvio(15.90)
                .fechaEnvio(fechaEnvio)
                .fechaEntrega(fechaEntrega)
                .estado("ENVIADO")
                .metodoEnvio("EXPRESS")
                .trackingNumber(tracking)
                .build();

        envioDao.save(envio);
        System.out.println("✅ Seeder Envío: creado con tracking " + tracking);
    }
}
