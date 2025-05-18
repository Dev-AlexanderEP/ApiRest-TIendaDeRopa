package com.ecommerce.server.controller.pago;

import com.ecommerce.server.model.dto.pago.MetodoPagoDto;
import com.ecommerce.server.model.entity.pago.MetodoPago;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.pago.IMetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class MetodoPagoController {
    @Autowired
    private IMetodoPagoService metodoPagoService;
    private Mensajes msg = new Mensajes();

    @GetMapping("/metodo-pagos")
    public ResponseEntity<?> showAll(){
        List<MetodoPago> getList = metodoPagoService.getMetodoPagos();
        if(getList.isEmpty()){
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/metodo-pago/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id){
        MetodoPago metodoPago = metodoPagoService.getMetodoPago(id);
        if (metodoPago == null){
            return msg.NoGetId();
        }
        return msg.Get(metodoPago);
    }

    @PostMapping("/metodo-pago")
    public ResponseEntity<?> create(@RequestBody MetodoPagoDto metodoPagoDto){
        MetodoPago metodoPagoSave = null;
        try{
            metodoPagoSave = metodoPagoService.save(metodoPagoDto);
            return msg.Post(metodoPagoSave);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @PutMapping("/metodo-pago/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MetodoPagoDto metodoPagoDto){
        MetodoPago metodoPagoUpdate = null;
        try{
            if (metodoPagoService.existsById(id)){
                metodoPagoDto.setId(id);
                metodoPagoUpdate = metodoPagoService.save(metodoPagoDto);
                return msg.Put(metodoPagoUpdate);
            }else {
                return msg.NoPut();
            }

        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @DeleteMapping("/metodo-pago/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            MetodoPago metodoPagoDelete = metodoPagoService.getMetodoPago(id);
            metodoPagoService.delete(metodoPagoDelete);
            return msg.Delete(metodoPagoDelete);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

}
