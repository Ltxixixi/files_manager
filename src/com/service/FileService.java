package com.service;

import com.dao.FileDao;
import com.dao.IFileDao;
import com.entity.File;

import java.sql.SQLException;
import java.util.List;

public class FileService implements IFileService{
    @Override
    public int insert(File file) throws SQLException, ClassNotFoundException {
        IFileDao dao = new FileDao();
        return dao.insert(file);
    }

    @Override
    public List<File> getFiles(int page, int pageSize, int userId, boolean isPublicOnly) throws SQLException, ClassNotFoundException {
        IFileDao dao = new FileDao();
        return dao.getFiles(page, pageSize, userId, isPublicOnly);
    }

    @Override
    public int getFileCount(int userId, boolean isPublicOnly) throws SQLException, ClassNotFoundException {
        IFileDao dao = new FileDao();
        return dao.getFileCount(userId, isPublicOnly);
    }

    @Override
    public File getFileById(int fileId) throws SQLException, ClassNotFoundException {
        IFileDao dao = new FileDao();
        return dao.getFileById(fileId);
    }

    @Override
    public void incrementDownloadCount(int fileId) throws SQLException, ClassNotFoundException {
        IFileDao dao = new FileDao();
        dao.incrementDownloadCount(fileId);
    }

    @Override
    public boolean blockFile(int fileId) throws SQLException, ClassNotFoundException {
        IFileDao dao = new FileDao();
        return dao.blockFile(fileId);
    }

    @Override
    public List<File> getUserFiles(int userId, int page, int pageSize) throws SQLException, ClassNotFoundException {
        IFileDao dao = new FileDao();
        return dao.getUserFiles(userId, page, pageSize);
    }
    @Override
    public boolean deleteFile(int fileId) throws SQLException, ClassNotFoundException {
        IFileDao dao = new FileDao();
        return dao.deleteFile(fileId);
    }


}
