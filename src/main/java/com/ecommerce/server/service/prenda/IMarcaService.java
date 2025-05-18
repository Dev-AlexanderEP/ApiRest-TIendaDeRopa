package com.ecommerce.server.service.prenda;

import com.ecommerce.server.model.dto.prenda.MarcaDto;
import com.ecommerce.server.model.entity.prenda.Marca;

import java.util.List;

public interface IMarcaService {
    List<Marca> getMarcas();
    Marca getMarca(Long id);
    Marca save(MarcaDto marcaDto);
    void deleteMarca(Marca marca);
    boolean existsById(Long id);

}
