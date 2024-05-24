package pers.nefedov.demoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.nefedov.demoapp.models.Email;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, String> {
    List<Email> findByUser_Login(String login);

    long countByUser_Login(String login);

    Email findByEmail(String email);
}