package com.ecommerce.server.service.prenda;

import com.ecommerce.server.model.dto.descuento.PrendaConDescuentoResponseDto;
import com.ecommerce.server.model.dto.prenda.PrendaDto;
import com.ecommerce.server.model.entity.prenda.Prenda;

import java.util.List;

public interface IPrendaService {
    List<Prenda> getPrendas();
    Prenda getPrenda(Long id);
    Prenda save(PrendaDto prendaDto);
    void deletePrenda(Prenda prenda);
    boolean existsById(Long id);
    public List<PrendaConDescuentoResponseDto> obtenerPrendasConDescuentos();
}
