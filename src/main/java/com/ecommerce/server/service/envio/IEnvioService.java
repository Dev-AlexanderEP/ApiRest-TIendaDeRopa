package com.ecommerce.server.service.envio;

import com.ecommerce.server.model.dto.envio.DatosPersonalesDto;
import com.ecommerce.server.model.dto.envio.EnvioDto;
import com.ecommerce.server.model.entity.envio.DatosPersonales;
import com.ecommerce.server.model.entity.envio.Envio;

import java.util.List;

public interface IEnvioService {
    List<Envio> getEnvios();
    Envio getEnvio(Long id);
    Envio save(EnvioDto envioDto);
    void delete(Envio envio);
    boolean existsById(Long id);
    List<Envio> obtenerEnviosNoEntregadosPorUsuario(Long userId);
    List<Envio> obtenerEnviosEntregadosPorUsuario(Long userId);

    }
