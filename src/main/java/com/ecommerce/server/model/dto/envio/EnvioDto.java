package com.ecommerce.server.model.dto.envio;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class EnvioDto {

    private Long id;
    private Long ventaId;
    private Long datosPersonalesId;
    private Double costoEnvio;
    private LocalDate fechaEnvio;
    private LocalDate fechaEntrega;
    private String estado;
    private String metodoEnvio;
    private String trackingNumber;
}