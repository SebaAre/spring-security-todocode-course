package com.sebastianarellano.blogsecurity.security;

import com.sebastianarellano.blogsecurity.entity.Role;
import com.sebastianarellano.blogsecurity.entity.User;
import com.sebastianarellano.blogsecurity.repository.RoleRepository;
import com.sebastianarellano.blogsecurity.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    public OAuth2AuthenticationSuccessHandler(UserRepository userRepository,
                                              RoleRepository roleRepository,
                                              JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername(email);
                    newUser.setEmail(email);
                    newUser.setEnabled(true);

                    Role userRole = roleRepository.findByName("USER")
                            .orElseThrow(() -> new RuntimeException("Role USER not found"));

                    Set<Role> roles = new HashSet<>();
                    roles.add(userRole);
                    newUser.setRoles(roles);

                    return userRepository.save(newUser);
                });

        String jwt = jwtUtil.createToken(user);

        String redirectUrl = "http://localhost:8080/oauth2/success?token=" + jwt;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}