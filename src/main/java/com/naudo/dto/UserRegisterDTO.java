package com.naudo.dto;

import com.naudo.enums.Role;

/** 
 * @author Fellipe Naudo  
 * @github naudofp
 * 11 de jan. de 2023
 */

public record UserRegisterDTO(String firstname, String email, String password, Role role) {

}
