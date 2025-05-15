package com.dao;

import com.db.DBConnection;
import com.entity.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FileDao implements IFileDao {

    public int insert(File file) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        String sql = "INSERT INTO file (filename, uuid_name, file_path, file_size, file_type, is_public, uploader_id, download_count, is_blocked) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, file.getFilename());
        statement.setString(2, file.getUuid_name());
        statement.setString(3, file.getFile_path());
        statement.setInt(4, file.getFile_size());
        statement.setString(5, file.getFile_type());
        statement.setString(6, file.getIs_public());
        statement.setString(7, file.getUploader_id());
        statement.setInt(8, file.getDownload_count());
        statement.setString(9, file.getIs_blocked());

        int rowsInserted = statement.executeUpdate();
        statement.close();
        connection.close();
        return rowsInserted;
    }
    @Override
    public List<File> getFiles(int page, int pageSize, int userId, boolean isPublicOnly)
            throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        String sql = "SELECT * FROM file WHERE is_blocked = 'N'";

        if (isPublicOnly) {
            sql += " AND is_public = 'Y'";
        } else if (userId != 0) {
            sql += " AND (is_public = 'Y' OR uploader_id = ?)";
        }

        sql += " LIMIT ? OFFSET ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        int paramIndex = 1;
        if (!isPublicOnly && userId != 0) {
            statement.setInt(paramIndex++, userId);
        }
        statement.setInt(paramIndex++, pageSize);
        statement.setInt(paramIndex, (page - 1) * pageSize);

        ResultSet rs = statement.executeQuery();
        List<File> files = new ArrayList<>();
        while (rs.next()) {
            files.add(extractFileFromResultSet(rs));
        }

        rs.close();
        statement.close();
        connection.close();
        return files;
    }

    @Override
    public int getFileCount(int userId, boolean isPublicOnly)
            throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        String sql = "SELECT COUNT(*) FROM file WHERE is_blocked = 'N'";

        if (isPublicOnly) {
            sql += " AND is_public = 'Y'";
        } else if (userId != 0) {
            sql += " AND (is_public = 'Y' OR uploader_id = ?)";
        }

        PreparedStatement statement = connection.prepareStatement(sql);
        if (!isPublicOnly && userId != 0) {
            statement.setInt(1, userId);
        }

        ResultSet rs = statement.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        statement.close();
        connection.close();
        return count;
    }

    @Override
    public File getFileById(int fileId) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM file WHERE id = ?");
        statement.setInt(1, fileId);

        ResultSet rs = statement.executeQuery();
        File file = null;
        if (rs.next()) {
            file = extractFileFromResultSet(rs);
        }

        rs.close();
        statement.close();
        connection.close();
        return file;
    }

    @Override
    public void incrementDownloadCount(int fileId) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE file SET download_count = download_count + 1 WHERE id = ?");
        statement.setInt(1, fileId);
        statement.executeUpdate();

        statement.close();
        connection.close();
    }


    @Override
    public boolean blockFile(int fileId) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE file SET is_blocked = 'Y' WHERE id = ?");
        statement.setInt(1, fileId);
        int rowsAffected = statement.executeUpdate();

        statement.close();
        connection.close();
        return rowsAffected > 0;
    }

    @Override
    public List<File> getUserFiles(int userId, int page, int pageSize)
            throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM file WHERE uploader_id = ? AND is_blocked = 'N' LIMIT ? OFFSET ?");
        statement.setInt(1, userId);
        statement.setInt(2, pageSize);
        statement.setInt(3, (page - 1) * pageSize);

        ResultSet rs = statement.executeQuery();
        List<File> files = new ArrayList<>();
        while (rs.next()) {
            files.add(extractFileFromResultSet(rs));
        }

        rs.close();
        statement.close();
        connection.close();
        return files;
    }

    private File extractFileFromResultSet(ResultSet rs) throws SQLException {
        File file = new File();
        file.setId(rs.getInt("id"));
        file.setFilename(rs.getString("filename"));
        file.setUuid_name(rs.getString("uuid_name"));
        file.setFile_path(rs.getString("file_path"));
        file.setFile_size(rs.getInt("file_size"));
        file.setFile_type(rs.getString("file_type"));
        file.setIs_public(rs.getString("is_public"));
        file.setUploader_id(rs.getString("uploader_id"));
        file.setDownload_count(rs.getInt("download_count"));
        file.setIs_blocked(rs.getString("is_blocked"));
        return file;
    }
}