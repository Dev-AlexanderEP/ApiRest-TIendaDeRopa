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

    // Opción 1: método derivado (recomendado)
    List<Envio> findByVentaUsuarioIdAndEstadoNot(Long userId, String estado);


}
