package pers.nefedov.demoapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason="Name is not unique")
public class NamesConflictException extends RuntimeException {
}
