package com.oficina.config;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oficina.domain.service.InstrutorService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String SECRET_KEY = "uB3dS4VjW/Ua2D9Upj9J3pN0YdT6uL5Kz4PzL2Nt8rFZBmHcKjZRvK9Tk6y2ZAjKxN7kMbDkQFZFcQJL8W5y+A==";


    @Autowired
    private InstrutorService instrutorDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
            	Claims claims = Jwts.parserBuilder()
            	        .setSigningKey(SECRET_KEY)
            	        .build()
            	        .parseClaimsJws(token)
            	        .getBody();

                String email = claims.getSubject();
                UserDetails userDetails = instrutorDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}