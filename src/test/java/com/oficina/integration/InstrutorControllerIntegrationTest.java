package com.oficina.integration;

import com.oficina.domain.Instrutor;
import com.oficina.domain.repository.InstrutorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // Para o perform
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // Para o andExpect

@SpringBootTest
@AutoConfigureMockMvc
public class InstrutorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstrutorRepository instrutorRepository;

    @BeforeEach
    public void setUp() {
        instrutorRepository.deleteAll();
    }

    @Test
    public void testRegisterInstrutor() throws Exception {
        String email = "teste@example.com";
        String senha = "senha123";

        mockMvc.perform(post("/instrutor/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + senha + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.id").isNotEmpty());

        Instrutor instrutor = instrutorRepository.findByEmail(email).orElse(null);
        assertNotNull(instrutor);
        assertEquals(email, instrutor.getEmail());
    }

    @Test
    public void testLogin_Success() throws Exception {
        String email = "teste@example.com";
        String senha = "senha123";
        String senhaEncriptada = new BCryptPasswordEncoder().encode(senha);

        Instrutor instrutor = new Instrutor();
        instrutor.setId(UUID.randomUUID());
        instrutor.setEmail(email);
        instrutor.setPassword(senhaEncriptada);
        instrutorRepository.save(instrutor);

        mockMvc.perform(post("/instrutor/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + senha + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(isEmptyOrNullString())))
                .andExpect(content().string(containsString("."))); 
    }

    @Test
    public void testLogin_Failure() throws Exception {
        String email = "teste@example.com";
        String senha = "senha_errada";

        String senhaEncriptada = new BCryptPasswordEncoder().encode("senha_correta");

        Instrutor instrutor = new Instrutor();
        instrutor.setId(UUID.randomUUID());
        instrutor.setEmail(email);
        instrutor.setPassword(senhaEncriptada);
        instrutorRepository.save(instrutor);

        mockMvc.perform(post("/instrutor/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + senha + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Falha no login")));
    }
}