package com.ecommerce.server.model.dao.descuento;

import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.model.entity.descuento.DescuentoCodigo;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DescuentoCodigoDao extends CrudRepository<DescuentoCodigo, Long> {
    @Modifying
    @Query("UPDATE DescuentoCodigo dc SET dc.activo = false " +
            "WHERE dc.fechaFin IS NOT NULL AND dc.fechaFin < :today AND dc.activo = true")
    void updateCodigosInactivos(LocalDate today);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT dc FROM DescuentoCodigo dc WHERE dc.codigo = :codigo AND dc.activo = true")
    Optional<DescuentoCodigo> findByCodigoWithLock(String codigo);

}
