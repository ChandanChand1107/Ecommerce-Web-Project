package com.zosh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.appconfig.JwtProvider;
import com.zosh.model.Cart;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;
import com.zosh.request.LoginRequest;
import com.zosh.response.AuthResponse;
import com.zosh.service.CartService;
import com.zosh.service.CustomUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	

	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	private CustomUserServiceImplementation customUserService;
	private CartService cartService;
	
	private AuthController(UserRepository userRepository,PasswordEncoder passwordEncoder, 
			CustomUserServiceImplementation customUserService, JwtProvider jwtProvider,
			CartService cartService) 
	{
		this.userRepository = userRepository;
		this.customUserService = customUserService;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.cartService = cartService;
	}
	@GetMapping("/signin")
	public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){
		String username = loginRequest.getEmail();
		String passwrd = loginRequest.getPassword();
		
		Authentication authentication = authenticate(username, passwrd);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToke(authentication);
		 AuthResponse authResponse = new AuthResponse();
		 
	     authResponse.setJwt(token);
	     authResponse.setMessage("Signin Scuccess");
        
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}
	
	private Authentication authenticate(String username, String passwrd) {
		UserDetails userDetail = customUserService.loadUserByUsername(username);
		if(userDetail == null)
		{
			throw new BadCredentialsException("Invalid Username");
		}
		if(!passwordEncoder.matches(passwrd, userDetail.getPassword()))
		{
			throw new BadCredentialsException("Invalid Password");
		}
		return new UsernamePasswordAuthenticationToken( userDetail, null, userDetail.getAuthorities());
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user)throws Exception
	{
		String email = user.getEmail();
        String password = user.getPassword();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();
        
        User isEmailExist=userRepository.findByEmail(email);
        
        if(isEmailExist != null)
        {
        	throw new Exception("Email is already used with Another account");
        }
        
        User createdUser= new User();
        
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		
		User savedUser = userRepository.save(createdUser);
		Cart cart =cartService.createCart(savedUser);
		
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = jwtProvider.generateToke(authentication);
        AuthResponse authResponse = new AuthResponse();
        
        authResponse.setJwt(token);
        authResponse.setMessage("SignUp Scuccess");
        
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

}
