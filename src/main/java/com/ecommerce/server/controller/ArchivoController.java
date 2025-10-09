package com.ecommerce.server.controller;

import com.ecommerce.server.config.ArchivoUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/archivos")
public class ArchivoController {

    @PostMapping("/upload")
    public ResponseEntity<?> uploadArchivo(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("subcarpeta") String subcarpeta,
            @RequestParam("nombreArchivo") String nombreArchivo) {
        System.out.println("archivo: " + (archivo != null ? archivo.getOriginalFilename() : "null"));
        System.out.println("subcarpeta: " + subcarpeta);
        System.out.println("nombreArchivo: " + nombreArchivo);
        try {
            ArchivoUtil.guardarArchivo(archivo, subcarpeta, nombreArchivo);
            return ResponseEntity.ok("Archivo guardado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el archivo: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar-carpeta")
    public ResponseEntity<?> eliminarCarpeta(@RequestParam("rutaRelativa") String rutaRelativa) {
        try {
            ArchivoUtil.eliminarCarpetaYContenido(rutaRelativa);
            return ResponseEntity.ok("Carpeta eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la carpeta: " + e.getMessage());
        }
    }


    @PutMapping("/renombrar-carpeta")
    public ResponseEntity<?> renombrarCarpeta(
            @RequestParam("rutaBase") String rutaBase,
            @RequestParam("nombreAntiguo") String nombreAntiguo,
            @RequestParam("nombreNuevo") String nombreNuevo) {
        try {
            ArchivoUtil.renombrarCarpetaYContenido(rutaBase, nombreAntiguo, nombreNuevo);
            return ResponseEntity.ok("Carpeta y archivos renombrados correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al renombrar: " + e.getMessage());
        }
    }
}