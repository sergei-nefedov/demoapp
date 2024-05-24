package pers.nefedov.demoapp.mappers;

import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.models.Email;

public interface EmailMapper {
    Email mapToEmail(UserCreationDto userCreationDto);
}

