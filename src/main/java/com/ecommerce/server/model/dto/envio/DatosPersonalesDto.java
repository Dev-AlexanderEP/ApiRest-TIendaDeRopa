package com.ecommerce.server.model.dto.envio;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class DatosPersonalesDto {

    private Long id;
    private String nombres;
    private String apellidos;
    private Long usuarioId;
    private String dni;
    private String departamento;
    private String provincia;
    private String distrito;
    private String calle;
    private String detalle;
    private String telefono;
    private String email;
}