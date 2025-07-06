package com.ecommerce.server.model.dao;

import com.ecommerce.server.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByEmail(String email);

    Page<Usuario> findAll(Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE " +
            "(:id IS NULL OR u.id = :id) AND " +
            "(:nombreUsuario IS NULL OR u.nombreUsuario = :nombreUsuario) AND " +
            "(:email IS NULL OR u.email = :email)")
    List<Usuario> buscarPorOpciones(
            @Param("id") Long id,
            @Param("nombreUsuario") String nombreUsuario,
            @Param("email") String email
    );
}
