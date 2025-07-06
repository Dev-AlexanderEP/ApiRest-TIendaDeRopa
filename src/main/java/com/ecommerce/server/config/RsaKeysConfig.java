package com.ecommerce.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

// Vinculamos las propiedades de configuracion con campos de la clase RsaKeysConfig
@ConfigurationProperties(prefix = "rsa")
@Data
public class RsaKeysConfig{
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

}
