package pers.nefedov.demoapp.mappers;

import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.models.Phone;

public interface PhoneMapper {
    Phone mapToPhone(UserCreationDto userCreationDto);
}
