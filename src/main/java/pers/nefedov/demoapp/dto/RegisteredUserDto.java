package pers.nefedov.demoapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredUserDto {

    private String login;

    private String name;

    private String dateOfBirth;

    private double accountBalance;

//    private List <String> phoneNumbers; todo clear
//
//    private List <String> emails;
}
