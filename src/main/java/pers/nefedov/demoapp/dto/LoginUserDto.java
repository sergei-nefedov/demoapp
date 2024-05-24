package pers.nefedov.demoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    @Schema(description = "Логин", example = "FirstUser")
    @NotEmpty
    @NotBlank
    @Size(min = 3, max = 15, message = "Логин должен состоять минимум из 3, максимум из 15 знаков")
    private String login;

    @Schema(description = "Пароль", example = "Password123")
    @NotEmpty
    @Size(min = 6, max = 15, message = "Пароль должен состоять минимум из 6, максимум из 15 знаков")
    private String password;
}
