package com.ppm.kanbantool.KanbanTool2.web;

import javax.validation.Valid;

import com.ppm.kanbantool.KanbanTool2.domain.User;
import com.ppm.kanbantool.KanbanTool2.payload.JWTLoginSuccessResponse;
import com.ppm.kanbantool.KanbanTool2.payload.LoginRequest;
import com.ppm.kanbantool.KanbanTool2.security.JwtTokenProvider;
import com.ppm.kanbantool.KanbanTool2.security.SecurityConstants;
import com.ppm.kanbantool.KanbanTool2.services.MapValidationErrorService;
import com.ppm.kanbantool.KanbanTool2.services.UserService;
import com.ppm.kanbantool.KanbanTool2.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap!= null) return errorMap;
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()
						)
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
		
	}
	@GetMapping("/test")
	public ResponseEntity<?> test(){
		System.out.println("in test");
		return ResponseEntity.ok("test");
	}
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
		System.out.println("in register function of the controller");
		//Validate passwords match
		userValidator.validate(user, result);
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
		User newUser = userService.saveUser(user);
		
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
		
	}
}
