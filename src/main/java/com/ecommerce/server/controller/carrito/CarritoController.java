package com.ecommerce.server.controller.carrito;


import com.ecommerce.server.model.dao.carrito.CarritoDao;
import com.ecommerce.server.model.dto.carrito.CarritoDto;
import com.ecommerce.server.model.dto.carrito.CarritoItemDto;
import com.ecommerce.server.model.dto.carrito.CarritoRequestDto;
import com.ecommerce.server.model.entity.carrito.Carrito;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.carrito.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe"
})
@RestController
@RequestMapping("/api/v1")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;
    @Autowired
    private CarritoDao carritoDao;

    private Mensajes msg = new Mensajes();

    // GET: Obtener todos los carritos
    @GetMapping("/carritos")
    public ResponseEntity<?> showAll() {
        List<Carrito> carritos = carritoService.getCarritos();
        List<CarritoDto> carritoDtos = carritos.stream().map(carrito -> {
            List<CarritoItemDto> carritoItemDtos = carrito.getCarritoItems().stream()
                    .map(item -> CarritoItemDto.builder()
                            .id(item.getId())
                            .prenda(item.getPrenda())
                            .cantidad(item.getCantidad())
                            .precioUnitario(item.getPrecioUnitario())
                            .build())
                    .collect(Collectors.toList());

            return CarritoDto.builder()
                    .id(carrito.getId())
                    .usuarioId(carrito.getUsuarioId())
                    .fechaCreacion(carrito.getFechaCreacion())
                    .estado(carrito.getEstado())
                    .carritoItems(carritoItemDtos)
                    .build();
        }).collect(Collectors.toList());
        if (carritoDtos.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(carritoDtos);
    }

//    // GET: Obtener un carrito por ID
    @GetMapping("/carrito/{id}")
    public ResponseEntity<?> ShowById(@PathVariable Long id) {
        Carrito carrito = carritoService.getCarrito(id);
        if (carrito == null) {
            return msg.NoGet();
        }

        List<CarritoItemDto> carritoItemDtos = carrito.getCarritoItems().stream()
                .map(item -> CarritoItemDto.builder()
                        .id(item.getId())
                        .prenda(item.getPrenda())
                        .talla(item.getTalla())
                        .cantidad(item.getCantidad())
                        .precioUnitario(item.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());

        CarritoDto carritoDto = CarritoDto.builder()
                .id(carrito.getId())
                .usuarioId(carrito.getUsuarioId())
                .fechaCreacion(carrito.getFechaCreacion())
                .estado(carrito.getEstado())
                .carritoItems(carritoItemDtos)
                .build();

        return msg.Get(carritoDto);
    }


    @GetMapping("/carrito/abierto/usuario/{usuarioId}")
    public ResponseEntity<?> getCarritosAbiertosByUsuarioId(@PathVariable Long usuarioId) {
        List<CarritoRequestDto> carritos = carritoDao.findCarritosAbiertosByUsuarioId(usuarioId);
        if (carritos.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(carritos);
    }
    // GET: Obtener la cantidad de ítems distintos en un carrito por su id
    @GetMapping("/carrito/{id}/cantidad-items")
    public ResponseEntity<?> getCantidadItems(@PathVariable Long id) {
        int cantidad = carritoDao.countDistinctItemsByCarritoId(id);
        return msg.Get(cantidad);
    }
    // POST: Crear un nuevo carrito
    @PostMapping("/carrito")
    public ResponseEntity<?> createCarrito(@RequestBody CarritoRequestDto carritoRequestDto) {
        try {
            CarritoDto carritoDto = carritoService.save(carritoRequestDto);
            return msg.Post(carritoDto);
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }


    // PUT: Actualizar un carrito existente
    @PutMapping("/carrito/{id}")
    public ResponseEntity<?> updateCarrito(@PathVariable Long id, @RequestBody CarritoRequestDto carritoRequestDto) {
        try {
            if (carritoService.existsById(id)) {
                carritoRequestDto.setId(id);
                CarritoDto carritoDto = carritoService.save(carritoRequestDto);
                return msg.Put(carritoDto);
            } else {
                return msg.NoPut();
            }
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }
//
    // DELETE: Eliminar un carrito
    @DeleteMapping("/carrito/{id}")
    public ResponseEntity<?> deleteCarrito(@PathVariable Long id) {
        try{
            Carrito carritoDelete = carritoService.getCarrito(id);
            carritoService.deleteCarrito(carritoDelete);
            return msg.Delete(carritoDelete);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }
}
