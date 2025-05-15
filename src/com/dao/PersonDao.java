package com.dao;

import com.db.DBConnection;
import com.entity.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDao implements IPersonDao{
    public Person select(String name, String pwd) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
        statement.setString(1, name);
        statement.setString(2, pwd);

        ResultSet rs = statement.executeQuery();
        Person person = null;
        if (rs.next()) {
            person = extractPersonFromResultSet(rs);
        }
        rs.close();
        statement.close();
        connection.close();
        return person;
    }

    @Override
    public int insert(Person person) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users(username, password,role) VALUES (?, ?,?)");
        statement.setString(1, person.getUsername());
        statement.setString(2, person.getPassword());
        statement.setString(3, person.getRole());
        int rowsInserted = statement.executeUpdate();
        statement.close();
        connection.close();
        return rowsInserted;

    }


    private Person extractPersonFromResultSet(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setUsername(rs.getString("username"));
        person.setPassword(rs.getString("password"));
        person.setRole(rs.getString("role"));
        return person;
    }
}
