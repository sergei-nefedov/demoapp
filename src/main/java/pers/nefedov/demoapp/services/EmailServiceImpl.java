package pers.nefedov.demoapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.exceptions.ForbiddenException;
import pers.nefedov.demoapp.mappers.EmailMapper;
import pers.nefedov.demoapp.models.Email;
import pers.nefedov.demoapp.models.User;
import pers.nefedov.demoapp.repositories.EmailRepository;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;

    @Override
    public Email addEmail(UserCreationDto userCreationDto) {
        if (emailRepository.existsById(userCreationDto.getEmail())) throw new ForbiddenException();
        return emailRepository.save(emailMapper.mapToEmail(userCreationDto));
    }

    @Override
    public List<String> addEmail(User user, String email) {
        if (emailRepository.existsById(email)) throw new ForbiddenException();
        emailRepository.save(new Email(email, user));
        List<Email> phoneList = emailRepository.findByUser_Login(user.getLogin());
        return phoneList.stream().map(Email::getEmail).collect(Collectors.toList());
    }

    @Override
    public User findUserByEmail(String email) {
        Email foundEmail = emailRepository.findByEmail(email);
        if (foundEmail == null) return null;
        return foundEmail.getUser();
    }

    @Transactional
    @Override
    public List<String> deleteEmail(User user, String email) {
        if (!emailRepository.existsById(email)) throw new ForbiddenException();
        if (emailRepository.countByUser_Login(user.getLogin()) < 2) throw new ForbiddenException();
        Email newEmail = new Email(email, user);
        emailRepository.delete(newEmail);
        return emailRepository.findByUser_Login(user.getLogin()).stream().map(Email::getEmail).collect(Collectors.toList());
    }
}
