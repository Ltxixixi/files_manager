package com.dao;

import com.entity.Person;

import java.sql.SQLException;

public interface IPersonDao {
    Person select(String name , String password) throws SQLException, ClassNotFoundException;
    int insert(Person person) throws SQLException, ClassNotFoundException;
}
