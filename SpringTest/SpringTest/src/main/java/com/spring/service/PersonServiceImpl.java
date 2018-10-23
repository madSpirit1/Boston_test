package com.spring.service;

import java.security.MessageDigest;
import java.util.List;

import com.spring.dao.PersonDAOImpl;
import com.spring.dto.PersonDTO;
import com.spring.dao.PersonDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonDAO personDAO;

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    @Transactional
    public void addPerson(PersonDTO p) {
        this.personDAO.addPerson(convert(p));
    }

    private Person convert(PersonDTO p) {
        Person result = new Person();
        result.setEmail(p.getEmail());
        result.setDate(p.getDate());
        result.setSurname(p.getSurname());
        result.setName(p.getName());
        result.setPass(makeHash(p.getPass()));
        return result;
    }

    @Override
    public boolean isPersonExists(String email) {
        return personDAO.isPersonExists(email);
    }

    @Override
    @Transactional
    public List<Person> listPersons() {
        return this.personDAO.listPersons();
    }

    @Override
    @Transactional
    public List<Person> listPersonsWithEmail(String email) {
        return this.personDAO.listPersonsWithEmail(email);
    }

    @Override
    @Transactional
    public Person getPersonById(int id) {
        return this.personDAO.getPersonById(id);
    }

    @Override
    @Transactional
    public void removePerson(int id) {
        this.personDAO.removePerson(id);
    }

    /**
     * шифрование md5
     *
     * @param source
     * @return
     */
    private static byte[] makeHash(String source) {
        byte[] bytes = null;
        try {
            MessageDigest digeast = MessageDigest.getInstance("MD5");
            bytes = digeast.digest(source.getBytes());
        } catch (Exception ex) {
            Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);
            logger.error("error with hash");
        }
        return bytes;
    }


}
