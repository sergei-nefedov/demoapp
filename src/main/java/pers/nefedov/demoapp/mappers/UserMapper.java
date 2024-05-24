package pers.nefedov.demoapp.mappers;

import pers.nefedov.demoapp.dto.RegisteredUserDto;
import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.models.User;

public interface UserMapper {
    User mapToUser(UserCreationDto userCreationDto);
    UserCreationDto mapToUserCreationDto(User user);
    RegisteredUserDto mapToRegisteredUserDto(User user);
}
