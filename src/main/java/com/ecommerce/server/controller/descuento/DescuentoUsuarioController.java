package com.ecommerce.server.controller.descuento;


import com.ecommerce.server.model.entity.descuento.DescuentoUsuario;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.descuento.IDescuentoUsuarioService;
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
public class DescuentoUsuarioController {

    @Autowired
    private IDescuentoUsuarioService descuentoUsuarioService;

    private Mensajes msg = new Mensajes();


    @GetMapping("/descuento-usuarios")
    public ResponseEntity<?> showAll(){
        List<DescuentoUsuario> getList = descuentoUsuarioService.getDescuentoUsuarios();
        if (getList.isEmpty()){
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

    @GetMapping("/descuento-usuario/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id){
        DescuentoUsuario descuentoUsuario = descuentoUsuarioService.getDescuentoUsuario(id);
        if (descuentoUsuario == null){
            return msg.NoGetId();
        }
        return msg.Get(descuentoUsuario);
    }

    @DeleteMapping("/descuento-usuario/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            DescuentoUsuario descuentoUsuario = descuentoUsuarioService.getDescuentoUsuario(id);
            descuentoUsuarioService.deleteDescuentoUsuario(descuentoUsuario);
            return msg.Delete(descuentoUsuario);
        }catch (DataAccessException e){
            return null;
        }
    }
}
