package com.ecommerce.server.service;

import com.ecommerce.server.model.dto.UsuarioUpdateDto;
import com.ecommerce.server.model.dto.usuario.CreateUsuarioRequest;
import com.ecommerce.server.model.dto.usuario.UpdateUsuarioRequest;
import com.ecommerce.server.model.dto.usuario.UsuarioResponse;
import com.ecommerce.server.model.entity.PageResult;
import com.ecommerce.server.model.entity.Usuario;

public interface IUsuarioService {
    PageResult<UsuarioResponse> getUsuarios(int pageNo);
    Usuario getUsuario(Long id);
    Usuario save(CreateUsuarioRequest createUsuarioRequest);
    Usuario update(UpdateUsuarioRequest updateUsuarioRequest);
    void deleteUsuario(Long id);
    boolean existsById(Long id);
}
