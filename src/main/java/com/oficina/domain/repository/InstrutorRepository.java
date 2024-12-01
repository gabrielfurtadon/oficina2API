package com.oficina.domain.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oficina.domain.Instrutor;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
	 Optional<Instrutor> findByEmail(String email);
}
