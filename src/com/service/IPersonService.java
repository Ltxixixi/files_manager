package com.service;

import com.entity.Person;

import java.sql.SQLException;

public interface IPersonService {
    Person selet(String name , String password) throws SQLException, ClassNotFoundException;
    int insert(Person person) throws SQLException, ClassNotFoundException;
}
