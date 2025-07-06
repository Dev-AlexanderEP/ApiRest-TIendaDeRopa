package com.ecommerce.server.model.dao.descuento;

import com.ecommerce.server.model.entity.descuento.DescuentoPrenda;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface DescuentoPrendaDao extends CrudRepository<DescuentoPrenda, Long> {
    @Modifying
    @Query("UPDATE DescuentoPrenda dp SET dp.activo = false " +
            "WHERE dp.fechaFin IS NOT NULL AND dp.fechaFin < :today AND dp.activo = true")
    void updateDescuentosInactivos(LocalDate today);
}
