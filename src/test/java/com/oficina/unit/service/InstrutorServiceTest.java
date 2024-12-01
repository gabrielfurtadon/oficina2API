package com.oficina.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.oficina.domain.Instrutor;
import com.oficina.domain.repository.InstrutorRepository;
import com.oficina.domain.service.InstrutorService;

@ExtendWith(MockitoExtension.class)
public class InstrutorServiceTest {

    @Mock
    private InstrutorRepository instrutorRepository;

    @InjectMocks
    private InstrutorService instrutorService;

    @Test
    public void testLoadUserByUsername_UserExists() {
    	
        String email = "teste@example.com";
        Instrutor instrutor = new Instrutor();
        instrutor.setId(UUID.randomUUID());
        instrutor.setEmail(email);
        instrutor.setPassword("senha_encriptada");

        when(instrutorRepository.findByEmail(email)).thenReturn(Optional.of(instrutor));

        UserDetails result = instrutorService.loadUserByUsername(email);

        assertNotNull(result);
        assertEquals(email, result.getUsername());
        verify(instrutorRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsername_UserDoesNotExist() {
        String email = "nao_existe@example.com";
        when(instrutorRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            instrutorService.loadUserByUsername(email);
        });
        verify(instrutorRepository, times(1)).findByEmail(email);
    }
}
