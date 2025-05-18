package com.ecommerce.server.model.payload;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Mensajes {
    public String msgGet = "";
    public String msgNoGet = "No hay Registros";
    public String msgNoGetId = "El Registro que intenta buscar, NO EXISTE";

    public String msgPost = "Se guardo correctamente";
    public String msgPut = "Se actualizo correctamente";
    public String msgNoPut = "El Registro no se encuentra para editar";


    public ResponseEntity<?> mensaje(String mensaje, Object object, HttpStatus status) {
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje(mensaje)
                        .object(object)
                        .build(), status);
    }

    public ResponseEntity<?> NoGet() {
        return mensaje(msgNoGet, null, HttpStatus.OK);
    }
    public ResponseEntity<?> NoGetId() {
        return mensaje(msgNoGetId, null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> Get(Object object) {
        return  mensaje("", object , HttpStatus.OK);
    }

    public ResponseEntity<?> Post(Object object) {
        return  mensaje(msgPost, object,HttpStatus.CREATED);
    }

    public ResponseEntity<?> Error( DataAccessException e) {
        return mensaje(e.getMessage(), null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    public ResponseEntity<?> Put(Object object) {
        return mensaje(msgPut, object, HttpStatus.CREATED);
    }

    public ResponseEntity<?> NoPut() {
        return mensaje(msgNoPut, null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> Delete(Object object) {
        return  new ResponseEntity<>(object, HttpStatus.NO_CONTENT);
    }
    public ResponseEntity<?> Personalizado(String Personalizado) {return mensaje(Personalizado, null, HttpStatus.OK);}
}
