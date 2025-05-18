package com.ecommerce.server.controller.carrito;

import com.ecommerce.server.model.dto.carrito.CarritoItemDto;
import com.ecommerce.server.model.dto.carrito.CarritoItemRequestDto;
import com.ecommerce.server.model.entity.carrito.CarritoItem;
import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.carrito.ICarritoItemService;
import com.ecommerce.server.service.carrito.ICarritoService;
import com.ecommerce.server.service.prenda.IPrendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class CarritoItemController {

    @Autowired
    private ICarritoItemService carritoItemService;
    @Autowired
    private IPrendaService prendaService;

    private Mensajes msg = new Mensajes();

    // GET: Obtener todos los ítems
    @GetMapping("/carrito-items")
    public ResponseEntity<?> showAll() {
        List<CarritoItem> getList = carritoItemService.getCarritoItems();
        if (getList.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    // GET: Obtener un ítem por ID
    @GetMapping("/carrito-item/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        CarritoItem carritoItem = carritoItemService.getCarritoItem(id);
        if (carritoItem == null) {
            return msg.NoGet();
        }
        return msg.Get(carritoItem);
    }

    // POST: Crear un nuevo ítem
    @PostMapping("/carrito-item")
    public ResponseEntity<?> create(@RequestBody CarritoItemRequestDto carritoItemRequestDto) {
        CarritoItem carritoItemSave = null;
        try{
            carritoItemSave = carritoItemService.save(carritoItemRequestDto);
            Prenda prenda = prendaService.getPrenda(carritoItemRequestDto.getPrendaId());
            return msg.Post(CarritoItem.builder()
                    .id(carritoItemSave.getId())
                    .carritoId(carritoItemSave.getCarritoId())
                    .prenda(prenda)
                    .cantidad(carritoItemSave.getCantidad())
                    .build());
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }
//
//     PUT: Actualizar un ítem existente
    @PutMapping("/carrito-item/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CarritoItemRequestDto carritoItemRequestDto) {
        CarritoItem carritoItemUpdate = null;
        try{
            if (carritoItemService.existsById(id)) {
                carritoItemRequestDto.setId(id); // Asegurarse de que el ID del DTO coincida con el de la URL
                carritoItemUpdate = carritoItemService.save(carritoItemRequestDto);
                Prenda prenda = prendaService.getPrenda(carritoItemRequestDto.getPrendaId());
                return msg.Put(CarritoItem.builder()
                        .id(carritoItemUpdate.getId())
                        .carritoId(carritoItemUpdate.getCarritoId())
                        .prenda(prenda)
                        .cantidad(carritoItemUpdate.getCantidad())
                        .build());
            }else{
                return msg.NoPut();
            }

        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }




    // DELETE: Eliminar un ítem
    @DeleteMapping("/carrito-item/{id}")
    public ResponseEntity<?> deleteCarritoItem(@PathVariable Long id) {
        try{
            CarritoItem carritoItemDelete = carritoItemService.getCarritoItem(id);
            carritoItemService.deleteCarritoItem(carritoItemDelete);
            return msg.Delete(carritoItemDelete);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }
}
