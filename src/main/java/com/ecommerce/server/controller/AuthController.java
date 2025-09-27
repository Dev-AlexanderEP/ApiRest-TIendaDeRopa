package com.ecommerce.server.controller;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.entity.Usuario;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*")
@RestController
public class AuthController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired()
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/token")
    public ResponseEntity<Map<String, String>>  generarToken(String grantType, String username, String password, boolean withRefreshToken, String refreshToken) {
        String subject = null;
        String scope = null;

        String nombre = username;
        if (grantType.equals("password")) {
            // Si es un email, busca el username correspondiente
            if (nombre.contains("@")) {
                Usuario usuario = usuarioDao.findByEmail(nombre)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " ));
                nombre = usuario.getNombreUsuario();
            }

            // Autenticamos al usuario con sus datos
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(nombre, password)
            );

            //Obtenemos los datos del usuario autenticado
            subject = authentication.getName().toString();
            scope = authentication.getAuthorities()
                    .stream().map(auth->auth.getAuthority())
                    .collect(Collectors.joining(" "));
        } else if (grantType.equals("refreshToken")) {
            if (refreshToken == null) {
                return new ResponseEntity<>(Map.of("error", "refresh token is required"), HttpStatus.UNAUTHORIZED);
            }
            Jwt decodedJwt = null;
            try{
                // extraemos info del refreseh token
                decodedJwt = jwtDecoder.decode(refreshToken);
            }catch (JwtException e){
                return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
            }

            subject = decodedJwt.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            scope = authorities.stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
        }
        Map<String, String> idToken = new HashMap<>();
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(withRefreshToken?1:5, ChronoUnit.HOURS))
                .issuer("security-service")
                .claim("scope", scope)
                .build();

        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        idToken.put("accessToken", jwtAccessToken);

        if (withRefreshToken) {
            JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(5, ChronoUnit.HOURS))
                    .issuer("security-service")
                    .claim("scope", scope)
                    .build();
            String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
            idToken.put("refreshToken", jwtRefreshToken);
        }

        return new ResponseEntity<>(idToken, HttpStatus.OK);
    }


// Dentro de AuthController

        @PostMapping("/google-login")
        public ResponseEntity<Map<String, String>> handleGoogleLogin(@RequestBody Map<String, String> request) {
            String credential = request.get("credential");
            String clientId = request.get("clientId");

            try {
                // Configurar el verificador de tokens de Google
                GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                        .setAudience(Collections.singletonList(clientId))
                        .build();

                // Verificar el token
                GoogleIdToken idToken = verifier.verify(credential);
                if (idToken != null) {
                    GoogleIdToken.Payload payload = idToken.getPayload();

                    // Extraer datos del usuario
                    String email = payload.getEmail();
                    String name = (String) payload.get("name");

                    // Buscar el usuario en la base de datos por email
                    UserDetails userDetails;
                    try {
                        userDetails = customUserDetailsService.loadUserByEmail(email);
                    } catch (UsernameNotFoundException e) {
                        // Generar un nombre de usuario a partir del email (parte antes de @)
                        String username = name;
                        // Crear un nuevo Admin
                        Usuario usuario = new Usuario();
                        usuario.setNombreUsuario(username);
                        usuario.setContrasenia("google-user-" + Instant.now().toString()); // Placeholder password
                        usuario.setEmail(email);
                        usuario.setNombreUsuario(name);
                        usuario.setRol("USER"); // Asignar rol por defecto
                        usuario.setActivo(true);

                        // Guardar el nuevo Admin en la base de datos
                        usuarioDao.save(usuario);

                        // Volver a cargar los detalles del usuario recién creado
                        userDetails = customUserDetailsService.loadUserByEmail(email);
                    }

                    // Generar token JWT personalizado
                    String scope = userDetails.getAuthorities()
                            .stream().map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(" "));

                    Instant instant = Instant.now();
                    JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                            .subject(userDetails.getUsername())
                            .issuedAt(instant)
                            .expiresAt(instant.plus(5, ChronoUnit.HOURS))
                            .issuer("security-service")
                            .claim("scope", scope)
                            .build();

                    String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

                    // Preparar respuesta
                    Map<String, String> response = new HashMap<>();
                    response.put("accessToken", jwtAccessToken);
                    response.put("email", email);
                    response.put("name", name);
                    response.put("username", userDetails.getUsername());
                    response.put("roles", scope);

                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(Map.of("error", "Invalid Google token"), HttpStatus.UNAUTHORIZED);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(Map.of("error", "Error validating Google token: " + e.getMessage()), HttpStatus.UNAUTHORIZED);
            }
        }



    @GetMapping("/usuario-id")
    public Long getUsuarioId() {
        // Obtener el Authentication del SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("Usuario no autenticado");
        }

        // Obtener el Principal
        Object principal = authentication.getPrincipal();

        Long userId;
        if (principal instanceof OAuth2AuthenticatedPrincipal) {
            // Manejo para OAuth2AuthenticatedPrincipal
            OAuth2AuthenticatedPrincipal oauthPrincipal = (OAuth2AuthenticatedPrincipal) principal;
            String username = oauthPrincipal.getAttribute("sub");
            if (username == null) {
                throw new RuntimeException("No se encontró el nombre de usuario en los claims del token");
            }
            Usuario usuario = usuarioDao.findByNombreUsuario(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
            userId = usuario.getId();
        } else if (principal instanceof Jwt) {
            // Manejo para Jwt
            Jwt jwt = (Jwt) principal;
            String username = jwt.getClaimAsString("sub");
            if (username == null) {
                throw new RuntimeException("No se encontró el nombre de usuario en los claims del token");
            }
            Usuario usuario = usuarioDao.findByNombreUsuario(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
            userId = usuario.getId();
        } else if (principal instanceof String) {
            // Manejo como respaldo si el principal es un String
            String username = (String) principal;
            if ("anonymousUser".equals(username)) {
                throw new RuntimeException("Usuario no autenticado");
            }
            Usuario usuario = usuarioDao.findByNombreUsuario(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
            userId = usuario.getId();
        } else {
            throw new RuntimeException("Tipo de principal no soportado: " + principal.getClass().getName());
        }

        return userId;
    }

    @GetMapping("/usuario")
    public Usuario getUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("Usuario no autenticado");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal oauthPrincipal = (OAuth2AuthenticatedPrincipal) principal;
            String username = oauthPrincipal.getAttribute("sub");
            if (username == null) {
                throw new RuntimeException("No se encontró el nombre de usuario en los claims del token");
            }
            return usuarioDao.findByNombreUsuario(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        } else if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            String username = jwt.getClaimAsString("sub");
            if (username == null) {
                throw new RuntimeException("No se encontró el nombre de usuario en los claims del token");
            }
            Usuario user =usuarioDao.findByNombreUsuario(username).orElse(null);
            user.setContrasenia("");
            return user;
//            return usuarioDao.findByNombreUsuario(username)
//                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        } else if (principal instanceof String) {
            String username = (String) principal;
            if ("anonymousUser".equals(username)) {
                throw new RuntimeException("Usuario no autenticado");
            }
            return usuarioDao.findByNombreUsuario(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        } else {
            throw new RuntimeException("Tipo de principal no soportado: " + principal.getClass().getName());
        }
    }


}
