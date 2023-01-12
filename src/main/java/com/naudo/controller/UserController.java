package com.naudo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naudo.config.jwt.AuthenticationResponse;
import com.naudo.dto.UserLoginDTO;
import com.naudo.dto.UserRegisterDTO;
import com.naudo.services.UserService;

/** 
 * @author Fellipe Naudo  
 * @github naudofp
 * 11 de jan. de 2023
 */


@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> demo(){
		return ResponseEntity.ok("");
	}

	@PostMapping("/auth/register")	
	public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRegisterDTO dto){
		return ResponseEntity.ok(service.register(dto));
	}
	
	@PostMapping("/auth/login")	
	public ResponseEntity<AuthenticationResponse> register(@RequestBody UserLoginDTO dto){
		return ResponseEntity.ok(service.login(dto));
	}

}
