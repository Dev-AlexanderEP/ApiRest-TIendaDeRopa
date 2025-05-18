package com.ecommerce.server.service.impl.carrito;

import com.ecommerce.server.model.dao.carrito.CarritoDao;
import com.ecommerce.server.model.dto.carrito.CarritoDto;
import com.ecommerce.server.model.dto.carrito.CarritoItemDto;
import com.ecommerce.server.model.dto.carrito.CarritoRequestDto;
import com.ecommerce.server.model.entity.carrito.Carrito;
import com.ecommerce.server.service.carrito.ICarritoService;
import com.ecommerce.server.service.prenda.IPrendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarritoImplService implements ICarritoService {
    @Autowired
    private CarritoDao carritoDao;
    @Autowired
    private IPrendaService prendaService;


    @Override
    public List<Carrito> getCarritos() {
        return (List) carritoDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Carrito getCarrito(Long id) {
        return carritoDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public CarritoDto save(CarritoRequestDto carritoRequestDto) {
        String estadoDefault = "ACTIVO"; // Corrección: "ACTVO" parece un error tipográfico
        if (carritoRequestDto.getEstado() != null) {
            estadoDefault = carritoRequestDto.getEstado();
        }

        // Obtener el carrito existente si se está actualizando (PUT)
        Carrito existingCarrito = null;
        if (carritoRequestDto.getId() != null) {
            existingCarrito = getCarrito(carritoRequestDto.getId());
        }

        // Mapear CarritoRequestDto a la entidad Carrito
        Carrito carrito = Carrito.builder()
                .id(carritoRequestDto.getId()) // Si es null, se generará automáticamente
                .usuarioId(carritoRequestDto.getUsuarioId())
                .fechaCreacion(existingCarrito != null ? existingCarrito.getFechaCreacion() : null) // Mantener la fecha original si es un PUT
                .estado(estadoDefault)
                .carritoItems(existingCarrito != null ? existingCarrito.getCarritoItems() : new ArrayList<>()) // Mantener los ítems existentes
                .build();

        // Guardar el carrito en la base de datos
        Carrito savedCarrito = carritoDao.save(carrito);

        // Mapear el Carrito guardado a CarritoDto
        List<CarritoItemDto> carritoItemDtos = savedCarrito.getCarritoItems().stream()
                .map(item -> CarritoItemDto.builder()
                        .id(item.getId())
                        .prenda(item.getPrenda())
                        .cantidad(item.getCantidad())
                        .precioUnitario(item.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());

        CarritoDto carritoDto = CarritoDto.builder()
                .id(savedCarrito.getId())
                .usuarioId(savedCarrito.getUsuarioId())
                .fechaCreacion(savedCarrito.getFechaCreacion())
                .estado(savedCarrito.getEstado())
                .carritoItems(carritoItemDtos)
                .build();

        return carritoDto;
    }

    @Transactional
    @Override
    public void deleteCarrito(Carrito carrito) {
        carritoDao.delete(carrito);
    }

    @Override
    public boolean existsById(Long id) {
        return carritoDao.existsById(id);
    }
}
