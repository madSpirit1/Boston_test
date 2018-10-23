package com.spring.service;

import java.util.List;

import com.spring.dto.PersonDTO;
import com.spring.model.Person;

public interface PersonService {

    public void addPerson(PersonDTO p);

    public List<Person> listPersons();

    public List<Person> listPersonsWithEmail(String email);

    public Person getPersonById(int id);

    public void removePerson(int id);

    public boolean isPersonExists(String email);
}
