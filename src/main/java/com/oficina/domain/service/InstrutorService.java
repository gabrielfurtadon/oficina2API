package com.oficina.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oficina.domain.repository.InstrutorRepository;

@Service
public class InstrutorService implements UserDetailsService {
	
	   @Autowired
	    private InstrutorRepository instrutorRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        return instrutorRepository.findByEmail(username)
	                .orElseThrow(() -> new UsernameNotFoundException("Instrutor não encontrado com o email: " + username));
	    }

}
