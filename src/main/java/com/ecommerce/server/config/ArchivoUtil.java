package com.ecommerce.server.config;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

public class ArchivoUtil {

    public static void guardarArchivo(MultipartFile archivo, String subcarpeta, String nombreArchivo) throws IOException {
        String rutaBase = System.getProperty("user.dir") + File.separator + "uploads";
        Path rutaCarpeta = Paths.get(rutaBase, subcarpeta);
        Files.createDirectories(rutaCarpeta);

        Path rutaArchivo = rutaCarpeta.resolve(nombreArchivo);
        System.out.println("Guardando archivo en: " + rutaArchivo.toAbsolutePath());
        if (archivo.isEmpty()) {
            System.out.println("El archivo está vacío");
            return;
        }
        try {
            archivo.transferTo(rutaArchivo.toFile());
            System.out.println("Archivo guardado correctamente");
        } catch (Exception e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void eliminarCarpetaYContenido(String rutaRelativa) throws IOException {
        String rutaBase = System.getProperty("user.dir") + File.separator + "uploads";
        Path rutaCarpeta = Paths.get(rutaBase, rutaRelativa);
        if (Files.exists(rutaCarpeta)) {
            Files.walk(rutaCarpeta)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            System.out.println("Carpeta eliminada: " + rutaCarpeta.toAbsolutePath());
        }
    }

    public static void renombrarCarpetaYContenido(String rutaBase, String nombreAntiguo, String nombreNuevo) throws IOException {
        Path origen = Paths.get(rutaBase, nombreAntiguo);
        Path destino = Paths.get(rutaBase, nombreNuevo);

        if (Files.exists(origen)) {
            // Renombrar carpeta principal
            Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);

            // Recorrer y renombrar archivos y subcarpetas
            Files.walk(destino)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String nombreArchivo = path.getFileName().toString();
                        if (nombreArchivo.contains(nombreAntiguo)) {
                            Path nuevoPath = path.resolveSibling(nombreArchivo.replace(nombreAntiguo, nombreNuevo));
                            try {
                                Files.move(path, nuevoPath, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }
}