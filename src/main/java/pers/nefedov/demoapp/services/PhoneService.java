package pers.nefedov.demoapp.services;

import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.models.Phone;
import pers.nefedov.demoapp.models.User;

import java.util.List;

public interface PhoneService {
    Phone addPhone(UserCreationDto userCreationDto);

    List<String> addPhoneNumber(User user, String phoneNumber);

    List<String> deletePhoneNumber(User user, String phoneNumber);

    User findUserByPhone(String phoneNumber);
}


