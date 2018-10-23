package com.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.spring.model.Person;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonDAOImpl implements PersonDAO {

    private static final Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    /**
     * добавить пользователя
     *
     * @param p
     */
    @Override
    public void addPerson(Person p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p);
        logger.info("Person saved successfully, Person Details=" + p);
    }

    /**
     * поиск пользователя по емайл
     *
     * @param email
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Person> listPersonsWithEmail(String email) {
        List<Person> personsList = null;
        Session session = this.sessionFactory.getCurrentSession();
        if (email.equals("")) {
            personsList = session.createQuery("from Person").list();
        } else {
            personsList = session.createQuery("from Person where email = :email").setParameter("email", email).list();
        }
        return personsList;
    }


    /**
     * список всех пользователей
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Person> listPersons() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Person> personsList = session.createQuery("from Person").list();
        return personsList;
    }

    /**
     * поиск пользователя
     *
     * @param id
     * @return
     */
    @Override
    public Person getPersonById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Person p = (Person) session.load(Person.class, new Integer(id));
        //logger.info("Person loaded successfully, Person details="+p);
        return p;
    }

    /**
     * даление пользователя
     *
     * @param id
     */
    @Override
    public void removePerson(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Person p = (Person) session.load(Person.class, new Integer(id));
        if (null != p) {
            session.delete(p);
        }
        logger.info("Person deleted successfully, person details=" + p);
    }

    /**
     * проверка, существует ли пользователь с таким емайл
     *
     * @param email
     * @return
     */
    @Transactional
    @Override
    public boolean isPersonExists(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        Long count = (Long) session.createQuery("select count(id) from Person where email=:email").setParameter("email", email).uniqueResult();
        logger.info("person exist=" + (count > 0));
        return count > 0;
    }


}
