package com.ecommerce.server.service;

import com.ecommerce.server.model.dto.UsuarioDto;
import com.ecommerce.server.model.entity.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> getUsuarios();
    Usuario getUsuario(Long id);
    Usuario save(UsuarioDto usuarioDto);
    void deleteUsuario(Usuario usuario);
    boolean existsById(Long id);
}
