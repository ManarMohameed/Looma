package com.looma.looma.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.looma.looma.entity.User;
import com.looma.looma.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/api/users")

public class UserController {
	    private final UserService userService;

	    public UserController(UserService userService) {
	        this.userService = userService;
	    }

	    public record RegisterRequest(
	            @NotBlank @Size(max = 50) String username,
	            @NotBlank @Email @Size(max = 100) String email,
	            @NotBlank @Size(min = 6, max = 72) String password) {
	    }

	    public record UserResponse(Integer userId, String username, String email, String role) {
	        static UserResponse from(User user) {
	            return new UserResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getRole());
	        }
	    }

	    @PostMapping("/register")
	    @ResponseStatus(HttpStatus.CREATED)
	    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
	        User user = userService.register(request.username(), request.email(), request.password());
	        return UserResponse.from(user);
	    }

	    @GetMapping("/{id}")
	    public UserResponse getById(@PathVariable Integer id) {
	        return UserResponse.from(userService.getById(id));
	    }
	}

