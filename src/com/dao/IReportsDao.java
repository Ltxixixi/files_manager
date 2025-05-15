package com.dao;

import com.entity.Reports;

import java.sql.SQLException;
import java.util.List;

public interface IReportsDao {
    int insert(Reports report) throws SQLException, ClassNotFoundException;
    List<Reports> getAllReports() throws SQLException, ClassNotFoundException;
    boolean updateReportStatus(int reportId, String status) throws SQLException, ClassNotFoundException;
    boolean addReport(Reports report) throws SQLException, ClassNotFoundException;
}
