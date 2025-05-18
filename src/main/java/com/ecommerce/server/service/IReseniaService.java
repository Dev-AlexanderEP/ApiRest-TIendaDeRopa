package com.ecommerce.server.service;

import com.ecommerce.server.model.dto.ReseniaDataClientDto;
import com.ecommerce.server.model.dto.ReseniaRequestDto;
import com.ecommerce.server.model.entity.Resenia;

import java.util.List;

public interface IReseniaService {
    List<Resenia> getResenias();
    Resenia getResenia(Long id);
    Resenia save(ReseniaRequestDto reseniaRequestDto);
    void delete(Resenia resenia);
    boolean existById(Long id);
    List<ReseniaDataClientDto> findReseniaDtoByPrendaId(Long prendaId);


}
