package com.ecommerce.server.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimitFilter extends OncePerRequestFilter {

    private final ConcurrentHashMap<String, Integer> requestCounts = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS_PER_MINUTE = 100;
    
    // Rutas públicas que NO tienen rate limiting estricto
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

    public RateLimitFilter() {
        // Limpiar contadores cada minuto
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
            requestCounts::clear, 
            1, 1, TimeUnit.MINUTES
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                     HttpServletResponse response, 
                                     FilterChain filterChain) throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // ✅ Rutas públicas: sin rate limit
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Rate limiting solo para rutas protegidas
        String clientIP = getClientIP(request);
        int count = requestCounts.merge(clientIP, 1, Integer::sum);
        
        if (count > MAX_REQUESTS_PER_MINUTE) {
            // ✅ Usar código 429 directamente
            response.setStatus(429); // Too Many Requests
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Rate limit exceeded. Try again later.\"}");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
    
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
    
    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}