package com.spring.dao;

import java.util.List;

import com.spring.model.Person;

public interface PersonDAO {
    public void addPerson(Person p);

    public List<Person> listPersons();

    public List<Person> listPersonsWithEmail(String email);

    public Person getPersonById(int id);

    public void removePerson(int id);

    public boolean isPersonExists(String email);
}
