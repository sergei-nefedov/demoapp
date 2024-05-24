package pers.nefedov.demoapp.services;

import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.models.Email;
import pers.nefedov.demoapp.models.User;

import java.util.List;

public interface EmailService {
    Email addEmail(UserCreationDto userCreationDto);

    List<String> deleteEmail(User user, String email);

    List<String> addEmail(User user, String email);

    User findUserByEmail(String email);
}

