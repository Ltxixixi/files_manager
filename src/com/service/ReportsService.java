package com.service;

import com.dao.IReportsDao;
import com.dao.ReportsDao;
import com.entity.Reports;

import java.sql.SQLException;
import java.util.List;

public class ReportsService implements IReportsService{

    public int insert(Reports report) throws SQLException, ClassNotFoundException {
        IReportsDao reportsDao = new ReportsDao();
        return reportsDao.insert(report);
    }

    @Override
    public List<Reports> getAllReports() throws SQLException, ClassNotFoundException {
        IReportsDao reportsDao = new ReportsDao();
        return reportsDao.getAllReports();

    }

    @Override
    public boolean updateReportStatus(int reportId, String status) throws SQLException, ClassNotFoundException {
        IReportsDao reportsDao = new ReportsDao();
        return reportsDao.updateReportStatus(reportId, status);
    }
}
