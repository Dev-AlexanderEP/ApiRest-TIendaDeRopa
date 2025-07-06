package com.ecommerce.server.model.dao.descuento;

import com.ecommerce.server.model.entity.descuento.DescuentoCategoria;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface DescuentoCategoriaDao extends CrudRepository<DescuentoCategoria, Long> {
    @Modifying
    @Query("UPDATE DescuentoCategoria dc SET dc.activo = false " +
            "WHERE dc.fechaFin IS NOT NULL AND dc.fechaFin < :today AND dc.activo = true")
    void updateDescuentosInactivos(LocalDate today);
}
