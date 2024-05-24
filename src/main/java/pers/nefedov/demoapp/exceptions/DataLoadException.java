package pers.nefedov.demoapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE, reason="Loading data from file is unsuccessful")
public class DataLoadException extends RuntimeException {
}
