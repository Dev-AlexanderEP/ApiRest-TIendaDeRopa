package com.ecommerce.server.model.dao.prenda;

import com.ecommerce.server.model.entity.prenda.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ProveedorDao extends CrudRepository<Proveedor, Long> {
    Page<Proveedor> findAll(Pageable pageable);

}
