package com.oficina.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.oficina.api.controller.InstrutorController;
import com.oficina.domain.Instrutor;
import com.oficina.domain.repository.InstrutorRepository;

@WebMvcTest(InstrutorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class InstrutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstrutorRepository instrutorRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testRegisterInstrutor() throws Exception {
        String email = "teste@example.com";
        String senha = "senha123";
        String senhaEncriptada = "senha_encriptada";

        Instrutor instrutor = new Instrutor();
        instrutor.setEmail(email);
        instrutor.setPassword(senha);

        Instrutor instrutorSalvo = new Instrutor();
        instrutorSalvo.setId(UUID.randomUUID());
        instrutorSalvo.setEmail(email);
        instrutorSalvo.setPassword(senhaEncriptada);

        when(bCryptPasswordEncoder.encode(senha)).thenReturn(senhaEncriptada);
        when(instrutorRepository.save(any(Instrutor.class))).thenReturn(instrutorSalvo);

        mockMvc.perform(MockMvcRequestBuilders.post("/instrutor/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + senha + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.id").isNotEmpty());

        verify(bCryptPasswordEncoder, times(1)).encode(senha);
        verify(instrutorRepository, times(1)).save(any(Instrutor.class));
    }

    @Test
    public void testLogin_Success() throws Exception {
        String email = "teste@example.com";
        String senha = "senha123";
        String tokenJwt = "token_jwt_mockado";

        Instrutor instrutor = new Instrutor();
        instrutor.setId(UUID.randomUUID());
        instrutor.setEmail(email);
        instrutor.setPassword(senha);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(instrutor);


        mockMvc.perform(MockMvcRequestBuilders.post("/instrutor/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + senha + "\"}"))
                .andExpect(status().isOk());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        String email = "teste@example.com";
        String senha = "senha_incorreta";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Bad credentials"));

        mockMvc.perform(MockMvcRequestBuilders.post("/instrutor/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + senha + "\"}"))
                .andExpect(status().isOk());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
