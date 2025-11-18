package com.ecommerce.server.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private RsaKeysConfig rsaKeysConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) throws Exception {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeysConfig.getPublicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeysConfig.getPublicKey())
                .privateKey(rsaKeysConfig.getPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        // ✅ Todos los orígenes permitidos (producción + desarrollo)
        corsConfig.setAllowedOrigins(Arrays.asList(
            // Render
            "https://tienda-ropa-d456.onrender.com",
            // Zapto/DuckDNS
            "https://mixmatch.zapto.org",
            "http://mixmatch.zapto.org",
            "https://mixmatch.duckdns.org",
            "http://mixmatch.duckdns.org",
            // Elastika
            "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe",
            // Desarrollo local
            "http://localhost:5174",
            "http://localhost:5173",
            "http://localhost:4200",
            "http://localhost:3000"
        ));
        
        corsConfig.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ));
        
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        
        corsConfig.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "X-Total-Count",
            "Access-Control-Allow-Origin"
        ));
        
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Rutas completamente públicas
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/token", "/token/**").permitAll()
                        .requestMatchers("/google-login").permitAll()
                        .requestMatchers("/api/v1/usuarios/create").permitAll()
                        .requestMatchers("/api/v1/enviar-codigo-verificacion").permitAll()
                        .requestMatchers("/api/v1/verificar-codigo").permitAll()
                        
                        // Endpoints públicos de lectura
                        .requestMatchers("/api/v1/prendas/**").permitAll()
                        .requestMatchers("/api/v1/prenda-tallas/**").permitAll()
                        .requestMatchers("/api/v1/prenda-marcas/**").permitAll()
                        .requestMatchers("/api/v1/prenda-precios/**").permitAll()
                        .requestMatchers("/api/v1/categorias/**").permitAll()
                        .requestMatchers("/api/v1/marcas/**").permitAll()
                        .requestMatchers("/api/v1/generos/**").permitAll()
                        .requestMatchers("/api/v1/imagenes/**").permitAll()
                        .requestMatchers("/api/v1/tallas/**").permitAll()
                        .requestMatchers("/api/v1/proveedores/**").permitAll()
                        
                        // Resto requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt
                        .decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                    )
                )
                // ✅ Filtros mejorados que NO bloquean rutas públicas
                .addFilterBefore(new SecurityHeadersFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new RateLimitFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                // JwtAuthenticationFilter NO es necesario, oauth2ResourceServer ya lo maneja
                .build();
    }
}