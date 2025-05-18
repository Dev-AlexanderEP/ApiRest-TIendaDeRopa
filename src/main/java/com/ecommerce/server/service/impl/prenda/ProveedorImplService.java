package com.ecommerce.server.service.impl.prenda;

import com.ecommerce.server.model.dao.prenda.ProveedorDao;
import com.ecommerce.server.model.dto.prenda.ProveedorDto;
import com.ecommerce.server.model.entity.prenda.Proveedor;
import com.ecommerce.server.service.prenda.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedorImplService implements IProveedorService {
    @Autowired
    private ProveedorDao proveedorDao;

    @Override
    public List<Proveedor> getProveedores() {
        return (List) proveedorDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Proveedor getProveedor(Long id) {
        return proveedorDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Proveedor save(ProveedorDto proveedorDto) {
        Proveedor proveedor = Proveedor.builder()
                .id(proveedorDto.getId())
                .nomProveedor(proveedorDto.getNomProveedor())
                .build();
        return proveedorDao.save(proveedor);
    }

    @Transactional
    @Override
    public void deleteProveedor(Proveedor proveedor) {
        proveedorDao.delete(proveedor);
    }

    @Override
    public boolean existsById(Long id) {
        return proveedorDao.existsById(id);
    }
}
