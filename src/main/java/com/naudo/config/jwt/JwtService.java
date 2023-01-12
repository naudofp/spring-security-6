package com.naudo.config.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/** 
 * @author Fellipe Naudo  
 * @github naudofp
 * 11 de jan. de 2023
 */

@Service
public class JwtService {

	private static final String SECRET_KEY = "214125442A472D4B6150645367566B59703373367639792F423F4528482B4D62";
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    	final Claims claims = extractAllClaims(token);
    	return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
    	return Jwts
    			.parserBuilder()
    			.setSigningKey(getSignInKey())
    			.build()
    			.parseClaimsJws(token)
    			.getBody();
    }
    
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    public String generateToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails);
    }
    
    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
    	return Jwts
    			.builder()
    			.setClaims(claims)
    			.setSubject(userDetails.getUsername())
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 30))
    			.signWith(getSignInKey(), SignatureAlgorithm.HS256)
    			.compact();
    }
    
    public boolean hasClaim(String token, String claimName) {
    	final Claims claims = extractAllClaims(token);
    	return claims.get(claimName) != null;
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    private Key getSignInKey() {
    	byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    	return Keys.hmacShaKeyFor(keyBytes);
    }
}
