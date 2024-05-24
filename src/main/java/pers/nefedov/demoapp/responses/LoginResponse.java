package pers.nefedov.demoapp.responses;

import lombok.*;

@Getter
@Setter

public class LoginResponse {
    private String token;
    private long expiresIn;
}
