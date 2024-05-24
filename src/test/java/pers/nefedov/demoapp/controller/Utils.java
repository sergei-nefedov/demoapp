package pers.nefedov.demoapp.controller;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Utils {

    public static void deleteTestUserFromDB(String login, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        namedParameterJdbcTemplate.update(
                "DELETE FROM users WHERE login = :login; ",
                Map.of("login", login)
        );
    }

    public static void createTestUserWithSettingsInDB(String login, String encodedPassword, NamedParameterJdbcTemplate
            namedParameterJdbcTemplate) {

            namedParameterJdbcTemplate.update (
                    "INSERT INTO users " +
                            "(login, " +
                            "password, " +
                            "name, " +
                            "date_of_birth, " +
                            "account_balance, " +
                            "base_account_balance) " +
                            "VALUES " +
                            "(:login, " +
                            ":password," +
                            ":name," +
                            ":dateOfBirth," +
                            ":accountBalance, " +
                            ":baseAccountBalance);",
                    Map.of(
                            "login", login,
                            "password", encodedPassword,
                            "name", "Иванов И.И.",
                            "dateOfBirth", LocalDate.parse("31.12.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                            "accountBalance", 100,
                            "baseAccountBalance", 100)
                    );
    }
}