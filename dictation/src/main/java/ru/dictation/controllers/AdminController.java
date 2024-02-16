package ru.dictation.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dictation.auth.JwtAuthenticationResponse;
import ru.dictation.auth.SignInRequest;
import ru.dictation.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AdminController {


    private final AuthenticationService authenticationService;



    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {

        log.info("Admin + " + request.getUsername() + "sign in.");

        return authenticationService.signIn(request);
    }
}