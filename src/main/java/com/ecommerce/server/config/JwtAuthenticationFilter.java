package com.ecommerce.server.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    // Rutas que NO requieren autenticación
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/token/",
            "/google-login",
            "/uploads/",
            "/api/v1/usuarios/create",
            "/api/v1/enviar-codigo-verificacion",
            "/api/v1/verificar-codigo"
    );

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Si es una ruta pública, no aplicar autenticación JWT
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Procesar autenticación JWT para rutas protegidas
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                Jwt jwt = jwtDecoder.decode(token);

                String username = jwt.getSubject();
                String scope = jwt.getClaimAsString("scope");
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + scope));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                // Token inválido o expirado
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token JWT inválido o expirado");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Verifica si la ruta es pública y no requiere autenticación
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}