package com.ecommerce.server.service.forgotPass;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

// ForgotCodeService.java
@Service
public class ForgotCodeService {

    private final StringRedisTemplate redis;
    private static final Duration TTL = Duration.ofMinutes(10);

    public ForgotCodeService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    private String key(String email) {
        return "forgot_code:" + email.toLowerCase();
    }

    public void saveCode(String email, String code) {
        // TIP: en prod, guarda un hash (p. ej. SHA-256 + salt) en vez del código plano
        redis.opsForValue().set(key(email), code, TTL);
    }

    public boolean verifyAndInvalidate(String email, String input) {
        String k = key(email);
        String stored = redis.opsForValue().get(k);
        if (stored == null) return false;

        // Comparación en tiempo constante
        boolean match = stored.equals(input);
        ;
        if (match) {
            redis.delete(k); // one-time
        }
        return match;
    }

    public void clear(String email) {
        redis.delete(key(email));
    }
}
