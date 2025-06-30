package com.ecommerce.server.model.dao.envio;

import com.ecommerce.server.model.entity.envio.Envio;
import org.hibernate.query.criteria.CriteriaDefinition;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EnvioDao extends CrudRepository<Envio,Long> {
    Optional<Envio> findByTrackingNumber(String trackingNumber);
}
