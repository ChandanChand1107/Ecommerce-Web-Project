package com.zosh.appconfig;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtProvider {
	
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	public String generateToke(Authentication auth) 
	{
		String jwt=Jwts.builder()
				
				.setIssuedAt(new Date())
				
				.setExpiration(new Date(new Date().getTime()+86400000))
				
				.claim("email",auth.getName())
				.signWith(key)
				.compact();
		
		return jwt;	
	}
	public String getEmailFromToken(String jwt)
	{
		jwt = jwt.substring(7);
		Claims claim = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email = String.valueOf(claim.get("email"));
		return email;
	}

}
