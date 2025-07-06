package com.ecommerce.server.model.dao;

import com.ecommerce.server.model.entity.Direccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DireccionDao extends CrudRepository<Direccion, Long> {
    List<Direccion> findByUsuarioId(Long usuarioId);
    Page<Direccion> findAll(Pageable pageable);

}
