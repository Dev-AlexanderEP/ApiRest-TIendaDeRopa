package com.ecommerce.server.service.descuento;

import com.ecommerce.server.model.dto.descuento.DescuentoCodigoDto;
import com.ecommerce.server.model.entity.descuento.DescuentoCodigo;

import java.util.List;

public interface IDescuentoCodigoService {
    List<DescuentoCodigo> getDescuentoCodigos();
    DescuentoCodigo getDescuentoCodigo(Long id);
    DescuentoCodigo save(DescuentoCodigoDto descuentoCodigoDto);
    void deleteDescuentoCodigo(DescuentoCodigo descuentoCodigo);
    boolean existsById(Long id);

    DescuentoCodigo aplicarCodigoDescuento(String codigo, Long usuarioId);
}
