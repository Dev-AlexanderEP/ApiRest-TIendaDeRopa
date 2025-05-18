package com.ecommerce.server.service.carrito;

import com.ecommerce.server.model.dto.carrito.CarritoItemDto;
import com.ecommerce.server.model.dto.carrito.CarritoItemRequestDto;
import com.ecommerce.server.model.entity.carrito.CarritoItem;

import java.util.List;

public interface ICarritoItemService {
    List<CarritoItem> getCarritoItems();
    CarritoItem getCarritoItem(Long id);
    CarritoItem save(CarritoItemRequestDto carritoItemRequestDto);
    void deleteCarritoItem(CarritoItem carritoItem);
    boolean existsById(Long id);
}
