package pers.nefedov.demoapp.mappers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pers.nefedov.demoapp.dto.RegisteredUserDto;
import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.models.User;

import java.math.BigDecimal;

import static java.lang.Math.round;

@Component
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final DateMapper dateMapper;
    @Override
    public User mapToUser(UserCreationDto userCreationDto) {
        User user = new User();
        user.setLogin(userCreationDto.getLogin());
        user.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
        user.setName(userCreationDto.getName());
        user.setDateOfBirth(dateMapper.mapToDate(userCreationDto.getDateOfBirth()));
        user.setAccountBalance(BigDecimal.valueOf(userCreationDto.getAccountBalance()));
        user.setBaseAccountBalance(BigDecimal.valueOf(userCreationDto.getAccountBalance()));
        return user;
    }

    @Override
    public UserCreationDto mapToUserCreationDto(User user) {// TODO rename?
        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setLogin(user.getLogin());
        userCreationDto.setPassword(user.getPassword());
        userCreationDto.setName(user.getName());
        userCreationDto.setDateOfBirth(dateMapper.mapDateToString(user.getDateOfBirth()));
        userCreationDto.setAccountBalance(user.getAccountBalance().doubleValue());
        return userCreationDto;
    }

    @Override
    public RegisteredUserDto mapToRegisteredUserDto(User user) { //TODO rename
        if (user == null) return null;
        RegisteredUserDto registeredUserDto = new RegisteredUserDto();
        registeredUserDto.setLogin(user.getLogin());
        registeredUserDto.setName(user.getName());
        registeredUserDto.setDateOfBirth(dateMapper.mapDateToString(user.getDateOfBirth()));
        registeredUserDto.setAccountBalance(user.getAccountBalance().doubleValue());
        return registeredUserDto;
    }
}
