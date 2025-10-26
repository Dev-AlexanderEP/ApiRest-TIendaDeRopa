package com.ecommerce.server.controller;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.dto.UsuarioDto;
import com.ecommerce.server.model.dto.UsuarioUpdateDto;
import com.ecommerce.server.model.dto.usuario.CreateUsuarioRequest;
import com.ecommerce.server.model.dto.usuario.UpdateUsuarioRequest;
import com.ecommerce.server.model.dto.usuario.UpdatedUsuarioWebRequest;
import com.ecommerce.server.model.dto.usuario.UsuarioResponse;
import com.ecommerce.server.model.entity.PageResult;
import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.model.payload.Mensajes;
import com.ecommerce.server.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:4200",
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe",
        "*"
})
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private Mensajes msg = new Mensajes();

    @Autowired
    private UsuarioDao usuarioDao;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarUsuarios(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombreUsuario,
            @RequestParam(required = false) String email
    ) {
        List<Usuario> usuarios = usuarioDao.buscarPorOpciones(id, nombreUsuario, email);
        if (usuarios.isEmpty()) {
            return msg.NoGet();
        }
        return msg.Get(usuarios);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> showAllUsuarios(@RequestParam(defaultValue = "1") int pageNo) {
        PageResult<UsuarioResponse> getList = usuarioService.getUsuarios(pageNo);
        return msg.Get(getList);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> showAdminById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuario(id);
        if (usuario == null) {
            return msg.NoGet();
        }
        return msg.Get(UsuarioResponse.fromEntity(usuario));

    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Validated CreateUsuarioRequest createUsuarioRequest) {
        Usuario usuarioSave = null;
        try {
            usuarioSave = usuarioService.save(createUsuarioRequest);
            return msg.Post(UsuarioResponse.fromEntity(usuarioSave));
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Validated UpdatedUsuarioWebRequest updatedUsuarioWebRequest) {
        try {
            UpdateUsuarioRequest req = UpdateUsuarioRequest.from(updatedUsuarioWebRequest, id);
            Usuario usuarioUpdate = usuarioService.update(req);
            return msg.Put(UsuarioResponse.fromEntity(usuarioUpdate));
        } catch (IllegalArgumentException e) {
            return msg.NoPut();
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return msg.Deletev2();
        } catch (DataAccessException e) {
            return msg.Error(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/resetear-contrasenia")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPass req) {
        System.out.println("=== DEBUG /resetar-contrasenia ===");
        System.out.println("Email recibido: " + req.email());
        System.out.println("Nueva contraseña: " + req.newPassword());
        System.out.println("Código recibido: " + req.code());
//
        Usuario usuario = usuarioDao.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + req.email()));

        UpdateUsuarioRequest userRequest = new UpdateUsuarioRequest(
                usuario.getId(),
                usuario.getNombreUsuario(),
                req.email(),
                usuario.getActivo(),
                req.newPassword(),
                usuario.getRol()
        );
        System.out.println("Usuario encontrado en BD: " + usuario.getEmail());

//         Aquí deberías validar el código de verificación con ForgotCodeService
//         De momento asumido como válido
        System.out.println("Validando código (simulado OK)");

//         Actualizar la contraseña
        usuarioService.update(userRequest);
        System.out.println("Contraseña actualizada y guardada correctamente");

        return ResponseEntity.ok(new ResetPass("req.email()", "Password updated successfully", "req.code()"));
    }


    // Este es un DTO simple para la solicitud de restablecimiento de contraseña No deberia ir aqui
    public record ResetPass(
            String email,
            String newPassword,
            String code
    ){}
}
