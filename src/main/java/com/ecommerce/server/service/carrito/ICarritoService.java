package com.ecommerce.server.service.carrito;

import com.ecommerce.server.model.dto.carrito.CarritoDto;
import com.ecommerce.server.model.dto.carrito.CarritoRequestDto;
import com.ecommerce.server.model.entity.carrito.Carrito;

import java.util.List;

public interface ICarritoService {
    List<Carrito> getCarritos();
    Carrito getCarrito(Long id);
    CarritoDto save(CarritoRequestDto carritoRequestDto); // Cambiar a CarritoDto
    void deleteCarrito(Carrito carrito);
    boolean existsById(Long id);
}
