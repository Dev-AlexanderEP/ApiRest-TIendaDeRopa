package com.ecommerce.server.model.dao.prenda;

import com.ecommerce.server.model.entity.prenda.Talla;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TallaDao  extends CrudRepository<Talla, Long> {
    @Query("SELECT t FROM Talla t WHERE LOWER(t.nomTalla) = LOWER(:nomTalla)")
    Talla findByNomTalla(String nomTalla);
    Page<Talla> findAll(Pageable pageable);

}
