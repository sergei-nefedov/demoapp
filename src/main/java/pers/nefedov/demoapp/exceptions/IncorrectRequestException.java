package pers.nefedov.demoapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="The request is incorrect")
public class IncorrectRequestException extends RuntimeException {
}
