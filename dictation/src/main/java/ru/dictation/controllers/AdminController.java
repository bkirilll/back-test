package ru.dictation.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dictation.domain.dto.JwtAuthenticationResponse;
import ru.dictation.domain.dto.SignInRequest;
import ru.dictation.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AdminController {


    private final AuthenticationService authenticationService;

    private final Logger logger = LogManager.getLogger(AdminController.class);


    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {

        logger.info("Admin with username " + request.getUsername() + " enter in system");

        return authenticationService.signIn(request);
    }
}