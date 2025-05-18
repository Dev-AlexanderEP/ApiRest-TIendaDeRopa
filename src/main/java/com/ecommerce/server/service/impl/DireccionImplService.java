package com.ecommerce.server.service.impl;

import com.ecommerce.server.model.dao.DireccionDao;
import com.ecommerce.server.model.dto.DireccionDto;
import com.ecommerce.server.model.entity.Direccion;
import com.ecommerce.server.service.IDireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionImplService implements IDireccionService {


    @Autowired
    private DireccionDao direccionDao;


    @Override
    public List<Direccion> getDirecciones() {
        return (List) direccionDao.findAll();
    }

    @Override
    public Direccion getDireccion(Long id) {
        return direccionDao.findById(id).orElse(null);
    }

    @Override
    public Direccion save(DireccionDto direccionDto) {
        Direccion direccion = Direccion.builder()
                .id(direccionDto.getId())
                .nombres(direccionDto.getNombres())
                .apellidos(direccionDto.getApellidos())
                .usuarioId(direccionDto.getUsuarioId())
                .dni(direccionDto.getDni())
                .departamento(direccionDto.getDepartamento())
                .provincia(direccionDto.getProvincia())
                .distrito(direccionDto.getDistrito())
                .calle(direccionDto.getCalle())
                .detalle(direccionDto.getDetalle())
                .telefono(direccionDto.getTelefono())
                .build();
        return direccionDao.save(direccion);
    }

    @Override
    public void delete(Direccion direccion) {
        direccionDao.delete(direccion);
    }

    @Override
    public boolean existsById(Long id) {
        return direccionDao.existsById(id);
    }
}
