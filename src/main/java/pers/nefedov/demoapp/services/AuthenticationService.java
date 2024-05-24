package pers.nefedov.demoapp.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.nefedov.demoapp.dto.LoginUserDto;
import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.mappers.UserMapper;
import pers.nefedov.demoapp.models.User;
import pers.nefedov.demoapp.repositories.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PhoneService phoneService;
    private final EmailService emailService;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            UserMapper userMapper,
            PhoneService phoneService,
            EmailService emailService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.phoneService = phoneService;
        this.emailService = emailService;
    }

    @Transactional
    public User signup(UserCreationDto input) {
        User user = userMapper.mapToUser(input);
        user = userRepository.save(user);
        phoneService.addPhone(input);
        emailService.addEmail(input);
        return user;
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getLogin(),
                        input.getPassword()
                )
        );

        return userRepository.findByLogin(input.getLogin());
        //.orElseThrow();
    }
}

