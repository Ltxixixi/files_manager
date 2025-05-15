package com.dao;

import com.db.DBConnection;
import com.entity.File;
import com.entity.Reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportsDao implements IReportsDao{

    public int insert(Reports report) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        String sql = "INSERT INTO reports (file_id, reporter_id, reason, status) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, report.getFile_id());
        statement.setString(2, report.getReporter_id());
        statement.setString(3, report.getReason());
        statement.setString(4, report.getStatus());

        int rowsInserted = statement.executeUpdate();
        statement.close();
        connection.close();
        return rowsInserted;
    }

    public List<Reports> getAllReports() throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        String sql = "SELECT r.*, f.filename FROM reports r JOIN file f ON r.file_id = f.id ORDER BY r.id DESC";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();
        List<Reports> reports = new ArrayList<>();

        while (rs.next()) {
            Reports report = new Reports();
            report.setId(rs.getInt("id"));
            report.setFile_id(rs.getString("file_id"));
            report.setReporter_id(rs.getString("reporter_id"));
            report.setReason(rs.getString("reason"));
            report.setStatus(rs.getString("status"));

            // 设置文件名
            File file = new File();
            file.setFilename(rs.getString("filename"));
            report.setFile(file);
            reports.add(report);
        }
        rs.close();
        statement.close();
        connection.close();
        return reports;
    }

    @Override
    public boolean updateReportStatus(int reportId, String status) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        String sql = "UPDATE reports SET status = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, status);
        statement.setInt(2, reportId);

        int rowsAffected = statement.executeUpdate();

        statement.close();
        connection.close();
        return rowsAffected > 0;
    }

    @Override
    public boolean addReport(Reports report) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        String sql = "INSERT INTO reports (file_id, reporter_id, reason, status) VALUES (?, ?, ?, '待处理')";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, report.getFile_id());
        statement.setString(2, report.getReporter_id());
        statement.setString(3, report.getReason());

        int rowsInserted = statement.executeUpdate();

        statement.close();
        connection.close();
        return rowsInserted > 0;
    }
}
