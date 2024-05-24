package pers.nefedov.demoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import pers.nefedov.demoapp.models.Phone;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, String> {
    List<Phone> findByUser_Login(@NonNull String login);

    long countByUser_Login(String login);

    Phone findByNumber(String number);
}