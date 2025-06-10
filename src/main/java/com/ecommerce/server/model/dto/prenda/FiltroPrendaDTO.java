package com.ecommerce.server.model.dto.prenda;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
public class FiltroPrendaDTO {
    private List<String> tallas;
    private List<String> marcas;
    private String categoria;
    private List<Rango> rangosPrecio;
    private List<Rango> rangosDescuento;

    // Getters y setters

    @Data
    public static class Rango {
        private Double min;
        private Double max;

        // Constructor, getters y setters
    }
}
