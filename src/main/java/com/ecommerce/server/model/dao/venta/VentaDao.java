package com.ecommerce.server.model.dao.venta;

import com.ecommerce.server.model.entity.venta.Venta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VentaDao extends CrudRepository<Venta, Long> {
    @Query(value = "SELECT v.id FROM ventas v WHERE v.usuario_id = :usuarioId AND v.estado = 'PENDIENTE' ORDER BY v.fecha_creacion ASC LIMIT 1", nativeQuery = true)
    Optional<Long> findSecondPendingVentaIdByUsuarioId(@Param("usuarioId") Long usuarioId);
}
