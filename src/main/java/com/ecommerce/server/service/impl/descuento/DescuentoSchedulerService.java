package com.ecommerce.server.service.impl.descuento;

import com.ecommerce.server.model.dao.descuento.DescuentoCategoriaDao;
import com.ecommerce.server.model.dao.descuento.DescuentoCodigoDao;
import com.ecommerce.server.model.dao.descuento.DescuentoPrendaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class DescuentoSchedulerService {

    @Autowired
    private DescuentoPrendaDao prendaRepository;

    @Autowired
    private DescuentoCategoriaDao categoriaRepository;

    @Autowired
    private DescuentoCodigoDao codigoRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Se ejecuta todos los días a las 00:00 (medianoche)
    @Transactional
    public void actualizarTodosLosDescuentosInactivos() {
        LocalDate today = LocalDate.now();
        prendaRepository.updateDescuentosInactivos(today);
        categoriaRepository.updateDescuentosInactivos(today);
        codigoRepository.updateCodigosInactivos(today);
    }
}