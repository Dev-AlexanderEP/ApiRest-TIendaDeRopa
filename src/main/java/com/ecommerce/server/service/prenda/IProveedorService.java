package com.ecommerce.server.service.prenda;

import com.ecommerce.server.model.dto.prenda.ProveedorDto;
import com.ecommerce.server.model.entity.prenda.Proveedor;

import java.util.List;

public interface IProveedorService {
    List<Proveedor> getProveedores();
    Proveedor getProveedor(Long id);
    Proveedor save(ProveedorDto proveedorDto);
    void deleteProveedor(Proveedor proveedor);
    boolean existsById(Long id);
}
