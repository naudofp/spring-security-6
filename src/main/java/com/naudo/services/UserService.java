package com.naudo.services;

import com.naudo.config.jwt.AuthenticationResponse;
import com.naudo.dto.UserLoginDTO;
import com.naudo.dto.UserRegisterDTO;

/** 
 * @author Fellipe Naudo  
 * @github naudofp
 * 11 de jan. de 2023
 */

public interface UserService {
	
	AuthenticationResponse register(UserRegisterDTO dto);

	AuthenticationResponse login(UserLoginDTO dto);
}
