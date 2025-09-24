package com.example.store.filters;

import com.example.store.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("jwtauthenticatinfilter");
        var authHeader = request.getHeader("Authorization");

        // Pass the request to next filter, at this point spring security will kick in
        // if the target resource or end point is protected spring will deny access and return 403 error.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.replace("Bearer ", "");

        if(!jwtService.validateToken(token)){
           filterChain.doFilter(request, response);
           return;
        }

        var authentication = new UsernamePasswordAuthenticationToken(
                jwtService.getUserIdFromToken(token),null,null);

        // additional metadata to the auth object
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // holds information regarding the user
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
