package pers.nefedov.demoapp.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.models.Email;
@AllArgsConstructor
@Component
public class EmailMapperImpl implements EmailMapper {
    private final UserMapper userMapper;
    @Override
    public Email mapToEmail(UserCreationDto userCreationDto) {
        Email email = new Email();
        email.setEmail(userCreationDto.getEmail());
        email.setUser(userMapper.mapToUser(userCreationDto));
        return email;
    }
}
