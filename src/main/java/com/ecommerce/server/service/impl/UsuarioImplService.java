package com.ecommerce.server.service.impl;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.dto.UsuarioDto;
import com.ecommerce.server.model.dto.UsuarioUpdateDto;
import com.ecommerce.server.model.entity.PageResult;
import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioImplService implements IUsuarioService {

    private final UsuarioDao usuarioDao;

    public UsuarioImplService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PageResult<Usuario> getUsuarios(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by("id").ascending());
        Page<Usuario> usuariosPage = usuarioDao.findAll(pageable);

        int currentPage = usuariosPage.getNumber() + 1; // Spring arranca desde 0
        int totalPages = usuariosPage.getTotalPages();

        Integer prev = (currentPage > 1) ? currentPage - 1 : null;
        Integer next = (currentPage < totalPages) ? currentPage + 1 : null;

        return new PageResult<>(
                usuariosPage.getContent(),
                usuariosPage.getTotalElements(),
                totalPages,
                currentPage,
                prev,
                next,
                usuariosPage.isEmpty()
        );
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
