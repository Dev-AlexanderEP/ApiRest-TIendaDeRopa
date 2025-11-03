package com.ecommerce.server.model.dao.envio;

import com.ecommerce.server.model.entity.envio.Envio;
import org.hibernate.query.criteria.CriteriaDefinition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.util.Optional;

public interface EnvioDao extends CrudRepository<Envio,Long> {
    Optional<Envio> findByTrackingNumber(String trackingNumber);

    @Query("SELECT e FROM Envio e JOIN e.venta v WHERE v.usuario.id = :userId AND e.estado <> :estado")
    List<Envio> findByUsuarioIdAndEstadoNot(@Param("userId") Long userId, @Param("estado") String estado);


}
