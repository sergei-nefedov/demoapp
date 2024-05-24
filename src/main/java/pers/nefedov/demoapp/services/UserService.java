package pers.nefedov.demoapp.services;

import pers.nefedov.demoapp.dto.RegisteredUserDto;
import pers.nefedov.demoapp.dto.TransferDto;
import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.models.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    UserCreationDto addUser(UserCreationDto userCreationDto);
    User getUserByLogin(String login);

    List<String> addPhoneNumber(String phoneNumber);

    List<String> deletePhoneNumber(String phoneNumber);

    List<String> addEmail(String email);

    List<String> deleteEmail(String email);

    RegisteredUserDto searchByEmail(String email);

    RegisteredUserDto searchByPhone(String phoneNumber);

    List<RegisteredUserDto> searchByName(String name);

    List<RegisteredUserDto> searchByBirthdate(String date);

    BigDecimal transfer(TransferDto transferDto);
}







