package pers.nefedov.demoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    @Schema(description = "Логин получателя средств", example = "AnotherUser")
    @NotEmpty(message = "Логин обязателен")
    @NotBlank(message = "Логин не должен быть пустым")
    @Size(min = 3, max = 15, message = "Логин должен состоять минимум из 3, максимум из 15 знаков")
    private String receiverLogin;

    @Schema(description = "Сумма перевода", example = "10")
    @NotNull
    @DecimalMin(value = "0.01", message = "Сумма должна быть больше нуля")
    @DecimalMax(value = "1000000000", message = "Сумма должна быть меньше миллиарда")
    private BigDecimal transferringSum;
}
