-- 创建数据库 file
CREATE DATABASE IF NOT EXISTS file;
USE file;

-- 创建 Person 表
CREATE TABLE IF NOT EXISTS Person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(50)
);

-- 创建 File 表
CREATE TABLE IF NOT EXISTS File (
    id INT AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(255),
    uuid_name VARCHAR(255),
    file_path VARCHAR(255),
    file_size INT,
    file_type VARCHAR(50),
    is_public VARCHAR(10),
    uploader_id VARCHAR(255),
    download_count INT,
    is_blocked VARCHAR(10)
);

-- 创建 Reports 表
CREATE TABLE IF NOT EXISTS Reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    file_id VARCHAR(255),
    reporter_id VARCHAR(255),
    reason TEXT,
    status VARCHAR(50)
);