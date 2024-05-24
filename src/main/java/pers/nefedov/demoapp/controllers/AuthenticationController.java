package pers.nefedov.demoapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.nefedov.demoapp.dto.LoginUserDto;
import pers.nefedov.demoapp.dto.RegisteredUserDto;
import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.mappers.UserMapper;
import pers.nefedov.demoapp.models.User;
import pers.nefedov.demoapp.responses.LoginResponse;
import pers.nefedov.demoapp.services.AuthenticationService;
import pers.nefedov.demoapp.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserMapper userMapper) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisteredUserDto> register(@RequestBody UserCreationDto userCreationDto) {
        RegisteredUserDto registeredUserDto = userMapper.mapToRegisteredUserDto(authenticationService.signup(userCreationDto));

        return new ResponseEntity<>(registeredUserDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
