package com.naudo.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.naudo.config.jwt.AuthenticationResponse;
import com.naudo.config.jwt.JwtService;
import com.naudo.dto.UserLoginDTO;
import com.naudo.dto.UserRegisterDTO;
import com.naudo.models.User;
import com.naudo.repository.UserRepository;

/** 
 * @author Fellipe Naudo  
 * @github naudofp
 * 11 de jan. de 2023
 */

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public UserServiceImpl(UserRepository repository, PasswordEncoder encoder, JwtService jwtService, AuthenticationManager authenticationManager) {
		this.repository = repository;
		this.encoder = encoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public AuthenticationResponse register(UserRegisterDTO dto) {
		User user = new User(null, dto.firstname(), dto.email(), encoder.encode(dto.password()), dto.role());
		
		repository.save(user);
		String token = jwtService.generateToken(user);
		return new AuthenticationResponse(token);
	}

	@Override
	public AuthenticationResponse login(UserLoginDTO dto) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
		User user = repository.findByEmail(dto.email())
				.orElseThrow(() -> new UsernameNotFoundException("User " + dto.email() + " was not found"));
		String token = jwtService.generateToken(user);
		return new AuthenticationResponse(token);
	}

}
