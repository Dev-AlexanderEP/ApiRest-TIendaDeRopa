package com.ecommerce.server.model.dao.carrito;

import com.ecommerce.server.model.dto.carrito.CarritoRequestDto;
import com.ecommerce.server.model.entity.carrito.Carrito;
import com.ecommerce.server.model.entity.carrito.CarritoItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CarritoDao extends CrudRepository<Carrito, Long> {
    List<Carrito> findByUsuarioId(Long usuarioId);
//    @Query("SELECT new com.ecommerce.server.model.dto.carrito.CarritoRequestDto(c.id, c.usuario.id, c.estado) FROM Carrito c WHERE c.usuario.id = :usuarioId AND c.estado = :estado")
//    Optional<CarritoRequestDto> findCarritoRequestDtoByUsuarioIdAndEstado(@Param("usuarioId") Long usuarioId, @Param("estado") String estado);
    @Query("SELECT new com.ecommerce.server.model.dto.carrito.CarritoRequestDto(c.id, c.usuarioId, c.estado) FROM Carrito c WHERE c.usuarioId = :usuarioId AND c.estado = 'ABIERTO'")
    List<CarritoRequestDto> findCarritosAbiertosByUsuarioId(@Param("usuarioId") Long usuarioId);
    @Query("SELECT COUNT(ci) FROM CarritoItem ci WHERE ci.carrito.id = :carritoId")
    int countDistinctItemsByCarritoId(@Param("carritoId") Long carritoId);
}
