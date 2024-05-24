package pers.nefedov.demoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import pers.nefedov.demoapp.models.User;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(@NonNull String login);

    List<User> findByNameLikeIgnoreCase(String name);

    List<User> findByDateOfBirthGreaterThan(Date dateOfBirth);
}
