package com.sebastianarellano.educative_platform.controller;

import com.sebastianarellano.educative_platform.dto.AuthLoginRequestDTO;
import com.sebastianarellano.educative_platform.dto.AuthRegisterRequestDTO;
import com.sebastianarellano.educative_platform.dto.AuthResponseDTO;
import com.sebastianarellano.educative_platform.model.Role;
import com.sebastianarellano.educative_platform.model.User;
import com.sebastianarellano.educative_platform.repository.RoleRepository;
import com.sebastianarellano.educative_platform.repository.UserRepository;
import com.sebastianarellano.educative_platform.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController (AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthLoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        String jwt = jwtUtils.createToken(authentication);

        return ResponseEntity.ok(new AuthResponseDTO(
                loginRequest.username(),
                "Login successful",
                jwt,
                true
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRegisterRequestDTO registerRequest) {
        if (userRepository.findByUsername(registerRequest.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponseDTO(null, "Username already exists", null, false));
        }

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findById(Long.parseLong(registerRequest.roleRequest()))
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(role);

        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .enabled(true)
                .accountNotExpired(true)
                .accountNotLocked(true)
                .credentialNotExpired(true)
                .roles(roles)
                .build();

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponseDTO(user.getUsername(), "User created successfully", null, true));
    }
}
