package com.naudo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naudo.models.User;

/** 
 * @author Fellipe Naudo  
 * @github naudofp
 * 11 de jan. de 2023
 */

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
