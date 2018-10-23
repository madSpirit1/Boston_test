package com.spring.dto;

import com.spring.dto.validation.PasswordsTheSame;

import javax.validation.constraints.*;
import java.util.Date;


@PasswordsTheSame
public class PersonDTO {
    @Size(min = 2, max = 30, message = "от 2 до 30 символов")
    private String name;
    @Size(min = 2, max = 30, message = "от 2 до 30 символов")
    private String surname;
    @NotNull(message = "Введите дату")
    @Past(message = "Введите корректную дату рождения")
    private Date date;
    @NotEmpty(message = "Введите пароль")
    private String pass;
    @Size(min = 2, max = 30, message = "от 2 до 30 символов")
    @Email(message = "Введите корректный емайл")
    private String email;
    private String pass1;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }
}
