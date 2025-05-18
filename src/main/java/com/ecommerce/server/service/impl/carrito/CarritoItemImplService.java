package com.ecommerce.server.service.impl.carrito;


import com.ecommerce.server.model.dao.carrito.CarritoItemDao;
import com.ecommerce.server.model.dto.carrito.CarritoItemRequestDto;
import com.ecommerce.server.model.entity.carrito.CarritoItem;
import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.service.carrito.ICarritoItemService;
import com.ecommerce.server.service.prenda.IPrendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoItemImplService implements ICarritoItemService {

    @Autowired
    private CarritoItemDao carritoItemDao;

    @Autowired
    private IPrendaService prendaService;

    @Override
    public List<CarritoItem> getCarritoItems() {
        return (List) carritoItemDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public CarritoItem getCarritoItem(Long id) {
        return carritoItemDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public CarritoItem save(CarritoItemRequestDto carritoItemRequestDto) {
        Prenda prenda = prendaService.getPrenda(carritoItemRequestDto.getPrendaId());
        // Mapear CarritoItemRequestDto a la entidad CarritoItem
        CarritoItem carritoItem = CarritoItem.builder()
                .id(carritoItemRequestDto.getId()) // Si es null, se generará automáticamente
                .carritoId(carritoItemRequestDto.getCarritoId()) // Obtener el carritoId del DTO
                .prenda(prenda)
                .cantidad(carritoItemRequestDto.getCantidad())
                .precioUnitario(carritoItemRequestDto.getPrecioUnitario())
                .build();

        // Guardar el carritoItem en la base de datos
        return carritoItemDao.save(carritoItem);
    }


    @Transactional
    @Override
    public void deleteCarritoItem(CarritoItem carritoItem) {
        carritoItemDao.delete(carritoItem);
    }

    @Override
    public boolean existsById(Long id) {
        return carritoItemDao.existsById(id);
    }
}
