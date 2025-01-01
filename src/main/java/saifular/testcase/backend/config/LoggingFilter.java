package saifular.testcase.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        String url = request.getRequestURI();
        LocalDateTime timestamp = LocalDateTime.now();

        System.out.printf("Request received : Method=%s, URL=%s, Timestamp=%s%n", method, url, timestamp);

        filterChain.doFilter(request, response);
    }
}
