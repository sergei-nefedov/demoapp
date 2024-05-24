package pers.nefedov.demoapp.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pers.nefedov.demoapp.dto.LoginUserDto;
import pers.nefedov.demoapp.dto.TransferDto;
import pers.nefedov.demoapp.responses.LoginResponse;

import java.math.BigDecimal;

import static pers.nefedov.demoapp.controller.Utils.createTestUserWithSettingsInDB;
import static pers.nefedov.demoapp.controller.Utils.deleteTestUserFromDB;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {
        TransferTest.Initializer.class
})
public class TransferTest {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=jdbc:postgresql://localhost:5432/testapp_db",
                    "spring.datasource.username=test_app",
                    "spring.datasource.password=password"
            ).applyTo(applicationContext);
        }
    }

    @BeforeEach
    public void begin () {
        createTestUserWithSettingsInDB("User1", passwordEncoder.encode("Password1"),namedParameterJdbcTemplate);
        createTestUserWithSettingsInDB("User2", passwordEncoder.encode("Password1"),namedParameterJdbcTemplate);
    }
    @AfterEach
    public void end () {
        deleteTestUserFromDB("User1", namedParameterJdbcTemplate);
        deleteTestUserFromDB("User2", namedParameterJdbcTemplate);
    }

    @Test
    public void whenLoginThenOkAndGetJwtAndDoTransfer() {
        String jwt = RestAssured.given().
                contentType("application/json").
                body(new LoginUserDto("User2", "Password1")).
                when().post("http://localhost/auth/login").then().
                statusCode(200).
                extract().response().as(LoginResponse.class).getToken();
        RestAssured.given().
                contentType("application/json").
                headers("Authorization", "Bearer " + jwt).
                body(new TransferDto("User1", BigDecimal.valueOf(10))).
                when().patch("http://localhost/user/transfer").then().
                statusCode(200);
    }

    @Test
    public void whenLoginThenOkAndGetJwtAndDoTransferWithoutJwt() {
        String jwt = RestAssured.given().
                contentType("application/json").
                body(new LoginUserDto("User2", "Password1")).
                when().post("http://localhost/auth/login").then().
                statusCode(200).
                extract().response().as(LoginResponse.class).getToken();
        RestAssured.given().
                contentType("application/json").
                body(new TransferDto("User1", BigDecimal.valueOf(10))).
                when().patch("http://localhost/user/transfer").then().
                statusCode(403);
    }

    @Test
    public void whenLoginThenOkAndGetJwtAndDoTransferMoreThanBillion() {
        String jwt = RestAssured.given().
                contentType("application/json").
                body(new LoginUserDto("User2", "Password1")).
                when().post("http://localhost/auth/login").then().
                statusCode(200).
                extract().response().as(LoginResponse.class).getToken();
        RestAssured.given().
                contentType("application/json").
                headers("Authorization", "Bearer " + jwt).
                body(new TransferDto("User1", BigDecimal.valueOf(1000000001))).
                when().patch("http://localhost/user/transfer").then().
                statusCode(400);
    }
    @Test
    public void whenLoginThenOkAndGetJwtAndDoTransferWithZeroAmount() {
        String jwt = RestAssured.given().
                contentType("application/json").
                body(new LoginUserDto("User2", "Password1")).
                when().post("http://localhost/auth/login").then().
                statusCode(200).
                extract().response().as(LoginResponse.class).getToken();
        RestAssured.given().
                contentType("application/json").
                headers("Authorization", "Bearer " + jwt).
                body(new TransferDto("User1", BigDecimal.valueOf(0))).
                when().patch("http://localhost/user/transfer").then().
                statusCode(400);
    }
    @Test
    public void whenLoginThenOkAndGetJwtAndDoTransferWithNegativeAmount() {
        String jwt = RestAssured.given().
                contentType("application/json").
                body(new LoginUserDto("User2", "Password1")).
                when().post("http://localhost/auth/login").then().
                statusCode(200).
                extract().response().as(LoginResponse.class).getToken();
        RestAssured.given().
                contentType("application/json").
                headers("Authorization", "Bearer " + jwt).
                body(new TransferDto("User1", BigDecimal.valueOf(-1))).
                when().patch("http://localhost/user/transfer").then().
                statusCode(400);
    }

    @Test
    public void whenLoginThenOkAndGetJwtAndDoTransferToOneself() {
        String jwt = RestAssured.given().
                contentType("application/json").
                body(new LoginUserDto("User2", "Password1")).
                when().post("http://localhost/auth/login").then().
                statusCode(200).
                extract().response().as(LoginResponse.class).getToken();
        RestAssured.given().
                contentType("application/json").
                headers("Authorization", "Bearer " + jwt).
                body(new TransferDto("User2", BigDecimal.valueOf(1))).
                when().patch("http://localhost/user/transfer").then().
                statusCode(400);
    }

    @Test
    public void whenLoginThenOkAndGetJwtAndDoTransferMoreThanBalance() {
        String jwt = RestAssured.given().
                contentType("application/json").
                body(new LoginUserDto("User1", "Password1")).
                when().post("http://localhost/auth/login").then().
                statusCode(200).
                extract().response().as(LoginResponse.class).getToken();
        RestAssured.given().
                contentType("application/json").
                headers("Authorization", "Bearer " + jwt).
                body(new TransferDto("User2", BigDecimal.valueOf(101))).
                when().patch("http://localhost/user/transfer").then().
                statusCode(400);
    }
}
