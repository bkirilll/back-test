package ru.dictation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.dictation.auth.JwtAuthenticationResponse;
import ru.dictation.auth.SignInRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AdminService adminService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;



    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var admin = adminService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(admin);
        return new JwtAuthenticationResponse(jwt);
    }
}