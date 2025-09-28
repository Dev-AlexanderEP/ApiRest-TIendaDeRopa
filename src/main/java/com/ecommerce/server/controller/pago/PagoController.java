package com.ecommerce.server.controller.pago;

import com.ecommerce.server.model.dto.pago.MetodoPagoDto;
import com.ecommerce.server.model.dto.pago.PagoDto;
import com.ecommerce.server.model.dto.pago.PagoResquestDto;
import com.ecommerce.server.model.entity.pago.Pago;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.pago.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe"
})
@RestController
@RequestMapping("/api/v1")
public class PagoController {

    @Autowired
    private IPagoService pagoService;

    private Mensajes msg = new Mensajes();

    @GetMapping("/pagos")
    public ResponseEntity<?> showAll(){
        List<Pago> getList = pagoService.getPagos();
        if(getList.isEmpty()){
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/pago/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id){
        Pago pago = pagoService.getPago(id);
        if(pago == null){
            return msg.NoGetId();
        }
        return msg.Get(pago);
    }

    @PostMapping("/pago")
    public ResponseEntity<?> create(@RequestBody PagoResquestDto pagoResquestDto){
        Pago pagoSave = null;
        try {
            pagoSave = pagoService.save(pagoResquestDto);
            return msg.Post(pagoSave);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @PutMapping("/pago/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PagoResquestDto pagoResquestDto){
        Pago pagoUpdate = null;
        try {
            if (pagoService.existsById(id)){
                pagoResquestDto.setId(id);
                pagoUpdate = pagoService.save(pagoResquestDto);
                return msg.Put(pagoUpdate);
            }else {
                return msg.NoPut();
            }

        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }

    @DeleteMapping("/pago/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            Pago pagoDelete = pagoService.getPago(id);
            pagoService.delete(pagoDelete);
            return msg.Delete(pagoDelete);
        }catch (DataAccessException e){
            return msg.Error(e);
        }
    }
}
