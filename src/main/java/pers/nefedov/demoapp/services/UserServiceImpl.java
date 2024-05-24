package pers.nefedov.demoapp.services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.nefedov.demoapp.dto.RegisteredUserDto;
import pers.nefedov.demoapp.dto.TransferDto;
import pers.nefedov.demoapp.dto.UserCreationDto;
import pers.nefedov.demoapp.exceptions.ForbiddenException;
import pers.nefedov.demoapp.mappers.DateMapper;
import pers.nefedov.demoapp.mappers.UserMapper;
import pers.nefedov.demoapp.models.User;
import pers.nefedov.demoapp.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PhoneService phoneService;
    private final EmailService emailService;
    private final DateMapper dateMapper;

    @Override
    @Transactional
    public UserCreationDto addUser(UserCreationDto userCreationDto) {
        if (userRepository.existsById(userCreationDto.getLogin())) throw new ForbiddenException();
        User user = userRepository.save(userMapper.mapToUser(userCreationDto));
        phoneService.addPhone(userCreationDto);
        emailService.addEmail(userCreationDto);
        return userMapper.mapToUserCreationDto(user);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<String> addPhoneNumber(String phoneNumber) {
        User currentUser = getCurrentUser();
        return phoneService.addPhoneNumber(currentUser, phoneNumber);
    }

    @Override
    public List<String> deletePhoneNumber(String phoneNumber) {
        User currentUser = getCurrentUser();
        return phoneService.deletePhoneNumber(currentUser, phoneNumber);
    }

    @Override
    public List<String> addEmail(String email) {
        User currentUser = getCurrentUser();
        return emailService.addEmail(currentUser, email);
    }

    @Override
    public List<String> deleteEmail(String email) {
        User currentUser = getCurrentUser();
        return emailService.deleteEmail(currentUser, email);
    }

    @Override
    public RegisteredUserDto searchByEmail(String email) {
        return userMapper.mapToRegisteredUserDto(emailService.findUserByEmail(email));
    }

    @Override
    public RegisteredUserDto searchByPhone(String phoneNumber) {
        return userMapper.mapToRegisteredUserDto(phoneService.findUserByPhone(phoneNumber));
    }

    @Override
    public List<RegisteredUserDto> searchByName(String name) {
        return userRepository.findByNameLikeIgnoreCase(name).stream().map(userMapper::mapToRegisteredUserDto).collect(Collectors.toList());
    }

    @Override
    public List<RegisteredUserDto> searchByBirthdate(String date) {
        Date requestDate = dateMapper.mapToDate(date);
        return userRepository.findByDateOfBirthGreaterThan(requestDate).stream().map(userMapper::mapToRegisteredUserDto).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public BigDecimal transfer(TransferDto transferDto) {
        User currentUser = getCurrentUser();
        User receivingUser = getUserByLogin(transferDto.getReceiverLogin());
        BigDecimal transferSum = transferDto.getTransferringSum();

        if (currentUser.getAccountBalance().compareTo(transferSum) < 0 ||
                transferSum.signum() <= 0 ||
                transferSum.compareTo(new BigDecimal(1000000000)) > 0 ||
                receivingUser == null ||
                receivingUser.getLogin().equals(currentUser.getLogin())) return BigDecimal.valueOf(-1);
        return performTransfer(currentUser, receivingUser, transferSum);
    }

    @Transactional
    protected BigDecimal performTransfer(User currentUser, User receivingUser, BigDecimal transferSum) {
        BigDecimal currentUserBalance = currentUser.getAccountBalance();
        BigDecimal currentUserBaseBalance = currentUser.getBaseAccountBalance();
        currentUserBalance = currentUserBalance.subtract(transferSum);
        if (transferSum.subtract(currentUserBaseBalance).signum() > 0) currentUserBaseBalance = BigDecimal.valueOf(0);
        else currentUserBaseBalance = currentUserBaseBalance.subtract(transferSum);
        BigDecimal receiverBalance = receivingUser.getAccountBalance();
        BigDecimal receiverBaseBalance = receivingUser.getBaseAccountBalance();
        receiverBalance = receiverBalance.add(transferSum);
        receiverBaseBalance = receiverBaseBalance.add(transferSum);

        currentUser.setAccountBalance(currentUserBalance);
        currentUser.setBaseAccountBalance(currentUserBaseBalance);
        receivingUser.setAccountBalance(receiverBalance);
        receivingUser.setBaseAccountBalance(receiverBaseBalance);
        userRepository.save(currentUser);
        userRepository.save(receivingUser);

        return currentUser.getAccountBalance();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Scheduled(fixedRate = 60000)
    void increaseBalances() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            BigDecimal balance = user.getAccountBalance();
            BigDecimal baseBalance = user.getBaseAccountBalance();
            BigDecimal maximumBalance = baseBalance.multiply(BigDecimal.valueOf(2.07));
            if (balance.compareTo(maximumBalance) >= 0) continue;
            BigDecimal newBalance = balance.add(balance.multiply(BigDecimal.valueOf(0.05)));
            if (newBalance.compareTo(maximumBalance) > 0) newBalance = maximumBalance;
            increaseBalance(user, newBalance);
        }
    }

    @Transactional
    protected void increaseBalance(User user, BigDecimal newBalance) {
        user.setAccountBalance(newBalance);
        userRepository.save(user);
    }
}
