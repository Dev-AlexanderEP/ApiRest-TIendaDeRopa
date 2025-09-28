package com.ecommerce.server.config.seeders;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class I_UsuarioSeeder {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Order(1) // se ejecuta primero
    CommandLineRunner initUsuarios(UsuarioDao usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {

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

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user3")
                        .email("user3@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user4")
                        .email("user4@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user5")
                        .email("user5@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user6")
                        .email("user6@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user7")
                        .email("user7@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user8")
                        .email("user8@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user9")
                        .email("user9@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());

                usuarioRepository.save(Usuario.builder()
                        .nombreUsuario("user10")
                        .email("user10@example.com")
                        .contrasenia(passwordEncoder.encode("123456"))
                        .rol("USER")
                        .build());

                System.out.println("✅ Seeder ejecutado: géneros insertados.");
            } else {
                System.out.println("⚠️ Seeder omitido: ya existen géneros en la base de datos.");
            }
        };
    }
}
