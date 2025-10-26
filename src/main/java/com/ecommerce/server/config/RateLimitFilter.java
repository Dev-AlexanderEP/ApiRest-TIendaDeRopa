package com.ecommerce.server.config;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        String ip = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(ip, k ->
                Bucket4j.builder()
                        .addLimit(
                                Bandwidth.classic(
                                        20, // capacidad máxima
                                        Refill.greedy(20, Duration.ofMinutes(1)) // recarga
                                )
                        )
                        .build()
        );
        if (bucket.tryConsume(1)) {
            // tiene token -> dejo pasar
            chain.doFilter(request, response);
        } else {
            // no tiene token -> bloqueo
            HttpServletResponse httpResp = (HttpServletResponse) response;
            httpResp.setStatus(429);
            httpResp.getWriter().write("Too Many Requests");
        }
    }
}
