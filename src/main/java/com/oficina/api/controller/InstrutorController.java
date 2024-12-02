package com.oficina.api.controller;


import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oficina.domain.Instrutor;
import com.oficina.domain.repository.InstrutorRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/instrutor")
public class InstrutorController {

	private static final String SECRET_KEY = "uB3dS4VjW/Ua2D9Upj9J3pN0YdT6uL5Kz4PzL2Nt8rFZBmHcKjZRvK9Tk6y2ZAjKxN7kMbDkQFZFcQJL8W5y+A==";
	private static final SecretKey SECRET_KEY_SPEC = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private InstrutorRepository instrutorRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @PostMapping("/register")
    public Instrutor registerInstrutor(@RequestBody Instrutor instrutor) {
        instrutor.setId(UUID.randomUUID());
        instrutor.setPassword(bCryptPasswordEncoder.encode(instrutor.getPassword()));
        return instrutorRepository.save(instrutor);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Instrutor instrutor) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(instrutor.getEmail(), instrutor.getPassword())
            );

            Instrutor user = (Instrutor) authentication.getPrincipal();

            String token = Jwts.builder()
                    .setSubject(user.getEmail())
                    .claim("id", user.getId().toString())
                    .claim("name", user.getName())
                    .claim("roles", user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(SECRET_KEY_SPEC, SignatureAlgorithm.HS512)
                    .compact();


            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
        	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        	            .body(Collections.singletonMap("error", "Falha no login: " + e.getMessage()));
        }
    }

	
}
