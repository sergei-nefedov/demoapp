package pers.nefedov.demoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDto {
    @Schema(description = "Логин", example = "FirstUser")
    @NotEmpty(message = "Логин обязателен")
    @NotBlank(message = "Логин не должен быть пустым")
    @Size(min = 3, max = 15, message = "Логин должен состоять минимум из 3, максимум из 15 знаков")
    private String login;

    @Schema(description = "Пароль", example = "Password123")
    @NotEmpty
    @Size(min = 6, max = 15, message = "Пароль должен состоять минимум из 6, максимум из 15 знаков")
    private String password;

    @Schema(description = "Фамилия, имя и отчество пользователя", example = "Иванов Иван Иванович")
    @NotEmpty
    @Size(min = 5, max = 64, message = "Длина ФИО не должна быть меньше 5 и больше 64 знаков.")
    private String name;

    @Schema(description = "Дата рождения пользователя в формате дд.мм.гггг", example = "01.01.2000")
    @NotEmpty
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Size(min = 10, max = 10, message = "Дата рождения должна состоять из 10 знаков")
    private String dateOfBirth;

    @Schema(description = "Баланс счета пользователя", example = "100")
    @NotNull
    @DecimalMin(message = "Баланс не может быть отрицательным", value = "0")
    private double accountBalance;

    @Schema(description = "Номер телефона", example = "+12345678901")
    @NotEmpty
    @Size(min = 12, max = 12, message = "Номер телефона должен состоять из 12 знаков")
    private String phoneNumber;

    @Schema(description = "Адрес электронной почты", example = "user@post.com")
    @NotEmpty
    @Email(message = "Неверный формат адреса электронной почты.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    @Size(min = 10, max = 24)
    private String email;
}
