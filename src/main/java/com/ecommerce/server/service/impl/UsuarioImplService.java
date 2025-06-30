package com.ecommerce.server.service.impl;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.dto.UsuarioDto;
import com.ecommerce.server.model.dto.UsuarioUpdateDto;
import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioImplService implements IUsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> getUsuarios() {
        return (List) usuarioDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario getUsuario(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Usuario save(UsuarioDto usuarioDto) {

        Usuario usuario = Usuario.builder()
                .id(usuarioDto.getId())
                .nombreUsuario(usuarioDto.getNombreUsuario())
                .email(usuarioDto.getEmail())
                .contrasenia(passwordEncoder.encode(usuarioDto.getContrasenia()))
                .rol("USER")
                .build();
        return usuarioDao.save(usuario);
    }
    @Override
    public Usuario update(UsuarioUpdateDto usuarioUpdateDto) {
        Usuario usuario = usuarioDao.findById(usuarioUpdateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + usuarioUpdateDto.getId()));

        // Validar email único (excluyendo el usuario actual)
        if (usuarioUpdateDto.getEmail() != null) {
            var existente = usuarioDao.findByEmail(usuarioUpdateDto.getEmail());
            if (existente.isPresent() && !existente.get().getId().equals(usuario.getId())) {
                throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuarioUpdateDto.getEmail());
            }
            usuario.setEmail(usuarioUpdateDto.getEmail());
        }

        usuario.setNombreUsuario(usuarioUpdateDto.getNombreUsuario());
        usuario.setRol(usuarioUpdateDto.getRol());
        usuario.setActivo(usuarioUpdateDto.getActivo());

        return usuarioDao.save(usuario);
    }

    @Transactional
    @Override
    public void deleteUsuario(Usuario usuario) {
        usuarioDao.delete(usuario);
    }

    @Override
    public boolean existsById(Long id) {
        return usuarioDao.existsById(id);
    }
}
