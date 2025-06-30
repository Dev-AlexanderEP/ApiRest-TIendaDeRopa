package com.ecommerce.server.service.impl.envio;

import com.ecommerce.server.model.dao.envio.DatosPersonalesDao;
import com.ecommerce.server.model.dto.carrito.CarritoDto;
import com.ecommerce.server.model.dto.carrito.CarritoRequestDto;
import com.ecommerce.server.model.dto.envio.DatosPersonalesDto;
import com.ecommerce.server.model.entity.carrito.Carrito;
import com.ecommerce.server.model.entity.envio.DatosPersonales;
import com.ecommerce.server.service.carrito.ICarritoService;
import com.ecommerce.server.service.envio.IDatosPersonalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DatosPersonalesImplService implements IDatosPersonalesService {

    @Autowired
    private DatosPersonalesDao datosPersonalesDao;

    @Override
    public List<DatosPersonales> getDatosPersonales() {
        return (List) datosPersonalesDao.findAll();
    }

    @Override
    public DatosPersonales getDatoPersonal(Long id) {
        return datosPersonalesDao.findById(id).orElse(null);
    }

   @Override
   public DatosPersonales save(DatosPersonalesDto datosPersonalesDto) {
       DatosPersonales datosPersonales = DatosPersonales.builder()
               .id(datosPersonalesDto.getId()) // Si es null, se generará automáticamente
               .nombres(datosPersonalesDto.getNombres())
               .apellidos(datosPersonalesDto.getApellidos())
               .usuarioId(datosPersonalesDto.getUsuarioId())
               .dni(datosPersonalesDto.getDni())
               .departamento(datosPersonalesDto.getDepartamento())
               .provincia(datosPersonalesDto.getProvincia())
               .distrito(datosPersonalesDto.getDistrito())
               .calle(datosPersonalesDto.getCalle())
               .detalle(datosPersonalesDto.getDetalle())
               .telefono(datosPersonalesDto.getTelefono())
               .email(datosPersonalesDto.getEmail())
               .build();

       return datosPersonalesDao.save(datosPersonales);
   }

    @Override
    public void delete(DatosPersonales datosPersonales) {
        datosPersonalesDao.delete(datosPersonales);
    }

    @Override
    public boolean existsById(Long id) {
        return datosPersonalesDao.existsById(id);
    }

}
