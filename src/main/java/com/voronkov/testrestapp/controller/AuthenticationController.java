package com.voronkov.testrestapp.controller;

import com.voronkov.testrestapp.dto.AuthenticationRequestDto;
import com.voronkov.testrestapp.exception.UserNotFoundException;
import com.voronkov.testrestapp.model.User;
import com.voronkov.testrestapp.repository.UserRepository;
import com.voronkov.testrestapp.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**Controller rest authorization and login endpoint
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            UserController.isValidEmail(email);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            User user = userRepository.findByEmailIgnoreCase(email);

            if (user == null) {
                throw new UserNotFoundException();
            }

            String token = jwtTokenProvider.createToken(email);

            Map<Object, Object> response = new HashMap<>();
            response.put("email", email);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid name or password");
        }
    }
}
