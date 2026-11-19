package com.looma.looma.service;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.looma.looma.entity.User;
import com.looma.looma.repository.UserRepository;

@Service

public class UserService {

	    private final UserRepository userRepository;
	    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    public UserService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }

	    public User register(String username, String email, String rawPassword) {
	        if (userRepository.existsByEmail(email)) {
	            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
	        }

	        User user = new User();
	        user.setUsername(username);
	        user.setEmail(email);
	        user.setPasswordHash(passwordEncoder.encode(rawPassword));

	        return userRepository.save(user);
	    }

	    public User getById(Integer id) {
	        return userRepository.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	    
	}
}
