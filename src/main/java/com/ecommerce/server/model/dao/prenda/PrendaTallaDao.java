// src/main/java/com/ecommerce/server/model/dao/prenda/PrendaTallaDao.java
package com.ecommerce.server.model.dao.prenda;

import com.ecommerce.server.model.entity.prenda.PrendaTalla;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PrendaTallaDao extends CrudRepository<PrendaTalla, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE PrendaTalla pt SET pt.stock = pt.stock - 1 WHERE pt.prenda.id = :prendaId AND pt.talla.id = :tallaId AND pt.stock >= 1")
    int restarUnoStock(@Param("prendaId") Long prendaId, @Param("tallaId") Long tallaId);

    @Modifying
    @Transactional
    @Query("UPDATE PrendaTalla pt SET pt.stock = pt.stock + 1 WHERE pt.prenda.id = :prendaId AND pt.talla.id = :tallaId")
    int sumarUnoStock(@Param("prendaId") Long prendaId, @Param("tallaId") Long tallaId);

    @Modifying
    @Transactional
    @Query("UPDATE PrendaTalla pt SET pt.stock = pt.stock + :cantidad WHERE pt.prenda.id = :prendaId AND pt.talla.id = :tallaId")
    int sumarStock(@Param("prendaId") Long prendaId, @Param("tallaId") Long tallaId, @Param("cantidad") Integer cantidad);

    PrendaTalla findByPrenda_IdAndTalla_Id(Long prendaId, Long tallaId);
}