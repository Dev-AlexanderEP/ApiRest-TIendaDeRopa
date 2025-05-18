package com.ecommerce.server.service.envio;


import com.ecommerce.server.model.dto.envio.DatosPersonalesDto;
import com.ecommerce.server.model.entity.envio.DatosPersonales;

import java.util.List;

public interface IDatosPersonalesService {
    List<DatosPersonales> getDatosPersonales();
    DatosPersonales getDatoPersonal(Long id);
    DatosPersonales save(DatosPersonalesDto datosPersonalesDto);
    void delete(DatosPersonales datosPersonales);
    boolean existsById(Long id);
}
