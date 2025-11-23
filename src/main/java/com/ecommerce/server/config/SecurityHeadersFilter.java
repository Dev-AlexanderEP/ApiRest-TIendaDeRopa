package com.ecommerce.server.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SecurityHeadersFilter extends OncePerRequestFilter {

    // Rutas públicas que NO necesitan security headers estrictos
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/api/v1/prendas",
        "/api/v1/categorias",
        "/api/v1/marcas",
        "/api/v1/generos",
        "/api/v1/imagenes",
        "/api/v1/tallas",
        "/api/v1/proveedores",
        "/api/v1/prenda-tallas",
        "/api/v1/prenda-marcas",
        "/api/v1/prenda-precios",
        "/actuator/health",
        "/uploads",
        "/token"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String path = request.getRequestURI();
        String method = request.getMethod();
        
        // ✅ Ignorar OPTIONS (CORS preflight)
        if ("OPTIONS".equalsIgnoreCase(method)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // ✅ Rutas públicas: headers básicos
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Aplicar security headers estrictos solo a rutas protegidas
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        
        filterChain.doFilter(request, response);
    }
    
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}