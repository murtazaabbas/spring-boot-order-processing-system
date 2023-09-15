package com.melitaltd.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melitaltd.config.SecurityProperties;
import com.melitaltd.exception.ServiceErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
public class ApiKeyAuthFilter extends OncePerRequestFilter {
    private final List<AntPathRequestMatcher> excludedMatchers = List.of(new AntPathRequestMatcher("/api/v1/orderservice/test"));
    private SecurityProperties securityProperties;

    public ApiKeyAuthFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the API key and secret from request headers
        String requestApiKey = request.getHeader("X-API-KEY");
        String requestApiSecret = request.getHeader("X-API-SECRET");
        // Validate the key and secret
        if (securityProperties.getKey().equals(requestApiKey) && securityProperties.getSecret().equals(requestApiSecret)) {
            // Continue processing the request
            filterChain.doFilter(request, response);
        } else {
            // Reject the request and send an unauthorized error
            handleInvalidToken(response, "Unauthorised accessed");
        }
    }

    private void handleInvalidToken(HttpServletResponse response, String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ServiceErrorResponse errorDetails = new ServiceErrorResponse(message, "UN_AUTHORIZED",
                "UN_AUTHORIZED");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludedMatchers.stream()
                .anyMatch(matcher -> matcher.matches(request));
    }
}
