package com.ecommerce.server.controller;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        String[] roles = usuario.getRol().split(" "); // Asumimos que los roles están separados por un espacio
        boolean enabled = usuario.getActivo();
        return User.builder()
                .username(usuario.getNombreUsuario())
                .password(usuario.getContrasenia())
                .authorities(roles) // puedes mapear roles más avanzadamente si es necesario
                .disabled(!enabled)
                .build();
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + email));
        String[] roles = usuario.getRol().split(" ");
        boolean enabled = usuario.getActivo();
        return User.builder()
                .username(usuario.getNombreUsuario())
                .password(usuario.getContrasenia())
                .authorities(roles)
                .disabled(enabled)
                .build();
    }
}