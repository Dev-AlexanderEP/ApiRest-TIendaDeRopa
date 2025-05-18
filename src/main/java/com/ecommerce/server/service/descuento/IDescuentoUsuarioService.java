package com.ecommerce.server.service.descuento;

import com.ecommerce.server.model.dto.descuento.DescuentoUsuarioDto;
import com.ecommerce.server.model.dto.descuento.DescuentoUsuarioRequestDto;
import com.ecommerce.server.model.entity.descuento.DescuentoUsuario;

import java.util.List;

public interface IDescuentoUsuarioService {
    List<DescuentoUsuario> getDescuentoUsuarios();
    DescuentoUsuario getDescuentoUsuario(Long id);
    void deleteDescuentoUsuario(DescuentoUsuario descuentoUsuario);
    boolean existsById(Long id);

}
