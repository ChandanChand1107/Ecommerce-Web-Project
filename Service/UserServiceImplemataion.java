package com.zosh.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zosh.appconfig.JwtProvider;
import com.zosh.exception.UserException;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;
@Service
public class UserServiceImplemataion implements UserService {
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	public UserServiceImplemataion(UserRepository userRepository,JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent())
		{
			return user.get();
		}
		throw new UserException("user not found with id-:" + userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userRepository.findByEmail(email);
		if(user==null)
		{
			throw new UserException("user not foun with email" + email);
		}
		return user;
 	}

}
