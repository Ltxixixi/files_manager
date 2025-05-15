package com.service;

import com.dao.IPersonDao;
import com.dao.PersonDao;
import com.entity.Person;

import java.sql.SQLException;

public class PersonService implements IPersonService{
    @Override
    public Person selet(String name, String password) throws SQLException, ClassNotFoundException {
        IPersonDao person = new PersonDao();
        return person.select(name,password);
    }

    @Override
    public int insert(Person person) throws SQLException, ClassNotFoundException {
        IPersonDao personDao = new PersonDao();
        return personDao.insert(person);
    }
}
