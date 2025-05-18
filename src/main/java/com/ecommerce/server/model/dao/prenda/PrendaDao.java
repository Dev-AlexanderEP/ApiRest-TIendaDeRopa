package com.ecommerce.server.model.dao.prenda;

import com.ecommerce.server.model.entity.prenda.Prenda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrendaDao extends CrudRepository<Prenda, Long> {
    @Query("SELECT p, dp.porcentaje, dp.activo " +
            "FROM Prenda p LEFT JOIN DescuentoPrenda dp ON p.id = dp.prenda.id " +
            "WHERE p.activo = true AND (dp.activo = true OR dp IS NULL) " +
            "AND (dp.fechaInicio <= CURRENT_DATE AND (dp.fechaFin IS NULL OR dp.fechaFin >= CURRENT_DATE) OR dp IS NULL)")
    List<Object[]> findPrendasConDescuentosActivos();
}
