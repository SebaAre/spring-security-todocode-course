package com.sebastianarellano.blogsecurity.service;

import com.sebastianarellano.blogsecurity.dto.AuthLoginRequestDTO;
import com.sebastianarellano.blogsecurity.dto.AuthRegisterRequestDTO;
import com.sebastianarellano.blogsecurity.dto.AuthResponseDTO;
import com.sebastianarellano.blogsecurity.entity.Role;
import com.sebastianarellano.blogsecurity.entity.User;
import com.sebastianarellano.blogsecurity.repository.RoleRepository;
import com.sebastianarellano.blogsecurity.repository.UserRepository;
import com.sebastianarellano.blogsecurity.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(AuthRegisterRequestDTO request) {

        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(true);
        user.setEmail(request.email());


        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        String jwt = jwtUtil.createToken(user);

        return new AuthResponseDTO(
                user.getUsername(),
                "User registered successfully",
                jwt,
                true
        );
    }

    public AuthResponseDTO login(AuthLoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwt = jwtUtil.createToken(user);

        return new AuthResponseDTO(
                user.getUsername(),
                "Login successful",
                jwt,
                true
        );
    }

}
