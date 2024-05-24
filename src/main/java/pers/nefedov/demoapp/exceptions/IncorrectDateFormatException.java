package pers.nefedov.demoapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Date format must be dd.MM.yyyy")
public class IncorrectDateFormatException extends RuntimeException {
}
