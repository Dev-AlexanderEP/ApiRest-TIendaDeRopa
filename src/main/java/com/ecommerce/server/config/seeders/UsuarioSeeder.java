package com.ecommerce.server.config.seeders;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioSeeder {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsuarios(UsuarioDao usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("admin")
                        .email("admin@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("ADMIN")
                        .build());

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user1")
                        .email("user1@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user2")
                        .email("user2@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());
            }
        };
    }
}
