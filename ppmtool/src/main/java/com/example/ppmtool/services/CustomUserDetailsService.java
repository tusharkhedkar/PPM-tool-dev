package com.example.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ppmtool.entities.User;
import com.example.ppmtool.repositories.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		if(user == null)
				throw new UsernameNotFoundException("Username not found!");
		return user;
	}
	
	@Transactional
	public User loadUserById(Long id) {
		
		User user = userRepository.findById(id).get();
		if(user == null)
			throw new UsernameNotFoundException("Username not found!");
	return user;
	}

}
