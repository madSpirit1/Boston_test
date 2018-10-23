package com.spring.dto.validation;

import com.spring.dto.PersonDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordsTheSameValidator implements ConstraintValidator<PasswordsTheSame, PersonDTO> {
    @Override
    public void initialize(PasswordsTheSame passwordsTheSame) {
    }

    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext constraintValidatorContext) {
        return personDTO.getPass().equals(personDTO.getPass1());
    }
}
