package com.ecommerce.server.controller;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.dto.UsuarioDto;
import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    private Mensajes msg = new Mensajes();

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @GetMapping("/usuarios")
    public ResponseEntity<?> showAllUsuarios() {
        List<Usuario> getList = usuarioService.getUsuarios();
        if (getList.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(getList);
    }

//    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> showAdminById(@PathVariable Long id){
        Usuario usuario = usuarioService.getUsuario(id);
        if (usuario == null){
            return msg.NoGet();
        }
        return msg.Get(UsuarioDto.builder()
                .id(usuario.getId())
                .nombreUsuario(usuario.getNombreUsuario())
                .email(usuario.getEmail())
                .contrasenia(usuario.getContrasenia())
                .rol(usuario.getRol())
                .build());
    }

//    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping("/usuario")
    public ResponseEntity<?> create(@RequestBody UsuarioDto usuarioDto) {
        Usuario  usuarioSave = null;
        try{
            usuarioSave = usuarioService.save(usuarioDto);
            return msg.Post(UsuarioDto.builder()
                    .id(usuarioSave.getId())
                    .nombreUsuario(usuarioSave.getNombreUsuario())
                    .email(usuarioSave.getEmail())
                    .contrasenia(passwordEncoder.encode(usuarioSave.getContrasenia())) // encriptar aquí
                    .rol(usuarioSave.getRol())
                    .build());
        }catch(DataAccessException e){
            return  msg.Error(e);
        }
    }

//    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PutMapping("/usuario/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UsuarioDto usuarioDto) {
        Usuario usuarioUpdate = null;
        try{
            if ( usuarioService.existsById(id)) {
                usuarioDto.setId(id);
                usuarioUpdate = usuarioService.save(usuarioDto);
                return msg.Put(UsuarioDto.builder()
                        .id(usuarioUpdate.getId())
                        .nombreUsuario(usuarioUpdate.getNombreUsuario())
                        .email(usuarioUpdate.getEmail())
                        .contrasenia(usuarioUpdate.getContrasenia())
                        .rol(usuarioUpdate.getRol())
                        .build());
            }else{
                return msg.NoPut();
            }
        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }

//    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        try{
            Usuario usuarioDelete = usuarioService.getUsuario(id);
            usuarioService.deleteUsuario(usuarioDelete);
            return msg.Delete(usuarioDelete);
        }catch (DataAccessException e){
            return  msg.Error(e);
        }
    }
}
