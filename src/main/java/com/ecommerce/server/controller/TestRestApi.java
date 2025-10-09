package com.ecommerce.server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:4200",
        "http://localhost:5174",
        "https://sv-02udg1brnilz4phvect8.cloud.elastika.pe",
        "*"
})
public class TestRestApi {
    @GetMapping("/dataTest")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public Map<String, Object> dataTest(Authentication authentication) {
        return  Map.of(
                "message", "Data Test",
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }
    @PostMapping("/saveTest")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public Map<String, Object> saveTest( String data) {
        return  Map.of( "dataSave", data );
    }

}
