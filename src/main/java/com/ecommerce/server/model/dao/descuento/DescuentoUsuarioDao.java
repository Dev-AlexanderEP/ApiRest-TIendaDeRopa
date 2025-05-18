package com.ecommerce.server.model.dao.descuento;

import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.model.entity.descuento.DescuentoCodigo;
import com.ecommerce.server.model.entity.descuento.DescuentoUsuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DescuentoUsuarioDao extends CrudRepository<DescuentoUsuario,Long> {
    @Query("SELECT CASE WHEN COUNT(du) > 0 THEN true ELSE false END " +
            "FROM DescuentoUsuario du WHERE du.descuentoCodigo = :descuentoCodigo AND du.usuario = :usuario")
    boolean existsByDescuentoCodigoAndUsuario(DescuentoCodigo descuentoCodigo, Usuario usuario);
}
