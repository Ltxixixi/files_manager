package com.dao;

import com.entity.File;

import java.sql.SQLException;
import java.util.List;

public interface IFileDao {

    public List<File> getFiles(int page, int pageSize, int userId, boolean isPublicOnly) throws SQLException, ClassNotFoundException;
    public int getFileCount(int userId, boolean isPublicOnly)throws SQLException, ClassNotFoundException;
    public File getFileById(int fileId) throws SQLException, ClassNotFoundException;
    public void incrementDownloadCount(int fileId)throws SQLException, ClassNotFoundException;
    public boolean blockFile(int fileId)throws SQLException, ClassNotFoundException;
    public List<File> getUserFiles(int userId, int page, int pageSize)throws SQLException, ClassNotFoundException;
    public int insert(File file) throws SQLException, ClassNotFoundException;

}
