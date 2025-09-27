package com.ecommerce.server.service.impl;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.dto.UsuarioUpdateDto;
import com.ecommerce.server.model.dto.usuario.CreateUsuarioRequest;
import com.ecommerce.server.model.dto.usuario.UpdateUsuarioRequest;
import com.ecommerce.server.model.dto.usuario.UsuarioResponse;
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
    private final PasswordEncoder passwordEncoder;

    public UsuarioImplService(UsuarioDao usuarioDao,PasswordEncoder passwordEncoder) {
        this.usuarioDao = usuarioDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public PageResult<UsuarioResponse> getUsuarios(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by("id").ascending());
        Page<Usuario> usuariosPage = usuarioDao.findAll(pageable);

        // Mapear entidades a DTOs
        List<UsuarioResponse> usuariosResponse = usuariosPage.getContent()
                .stream()
                .map(usuario -> new UsuarioResponse(
                        usuario.getId(),
                        usuario.getNombreUsuario(),
                        usuario.getEmail(),
                        usuario.getRol(),
                        usuario.getActivo(),
                        usuario.getCreatedAt(),
                        usuario.getUpdatedAt()
                ))
                .toList();

        int currentPage = usuariosPage.getNumber() + 1; // Spring arranca desde 0
        int totalPages = usuariosPage.getTotalPages();

        Integer prev = (currentPage > 1) ? currentPage - 1 : null;
        Integer next = (currentPage < totalPages) ? currentPage + 1 : null;

        return new PageResult<>(
                usuariosResponse,
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
    public Usuario save(CreateUsuarioRequest createUsuarioRequest) {
        String ValidarRol = createUsuarioRequest.rol() != null ? createUsuarioRequest.rol() : "USER";
        Usuario usuario = Usuario.builder()
                .id(null)
                .nombreUsuario(createUsuarioRequest.nombreUsuario())
                .email(createUsuarioRequest.email())
                .contrasenia(passwordEncoder.encode(createUsuarioRequest.contrasenia()))
                .rol(ValidarRol)
                .build();
        return usuarioDao.save(usuario);
    }

    @Override
    public Usuario update(UpdateUsuarioRequest updateUsuarioRequest) {
        Usuario usuario = usuarioDao.findById(updateUsuarioRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + updateUsuarioRequest.id()));

        // Validar email único (excluyendo el usuario actual)
        if (updateUsuarioRequest.email() != null) {
            var existente = usuarioDao.findByEmail(updateUsuarioRequest.email());
            if (existente.isPresent() && !existente.get().getId().equals(usuario.getId())) {
                throw new IllegalArgumentException("Ya existe un usuario con el email: " + updateUsuarioRequest.email());
            }
            usuario.setEmail(updateUsuarioRequest.email());
        }

        if (updateUsuarioRequest.nombreUsuario() != null) {
            usuario.setNombreUsuario(updateUsuarioRequest.nombreUsuario());
        }
        if (updateUsuarioRequest.rol() != null) {
            usuario.setRol(updateUsuarioRequest.rol());
        }
        if (updateUsuarioRequest.contrasenia() != null) {
            usuario.setContrasenia(passwordEncoder.encode(updateUsuarioRequest.contrasenia()));
        }
        if (updateUsuarioRequest.activo() != null) {
            usuario.setActivo(updateUsuarioRequest.activo());
        }

        return usuarioDao.save(usuario);
    }

    @Transactional
    @Override
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));
        usuarioDao.delete(usuario);
    }


    @Override
    public boolean existsById(Long id) {
        return usuarioDao.existsById(id);
    }
}
