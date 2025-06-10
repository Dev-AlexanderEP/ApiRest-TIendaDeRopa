package com.ecommerce.server.model.dao.carrito;

import com.ecommerce.server.model.entity.carrito.CarritoItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CarritoItemDao extends CrudRepository<CarritoItem, Long> {
    @Query("SELECT ci FROM CarritoItem ci WHERE ci.carrito.id = :carritoId AND ci.prenda.id = :prendaId AND ci.talla.id = :tallaId")
    CarritoItem findByCarritoAndPrendaAndTalla(@Param("carritoId") Long carritoId, @Param("prendaId") Long prendaId, @Param("tallaId") Long tallaId);

    @Modifying
    @Transactional
    @Query("UPDATE CarritoItem ci SET ci.cantidad = ci.cantidad + 1 WHERE ci.id = :id")
    void incrementarCantidad(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("UPDATE CarritoItem ci SET ci.cantidad = :cantidad WHERE ci.id = :id")
    void actualizarCantidad(@Param("id") Long id, @Param("cantidad") int cantidad);
}
