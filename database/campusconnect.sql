-- CampusConnect database setup
-- Run this script in MySQL Workbench. It does not contain account credentials.
CREATE DATABASE IF NOT EXISTS CampusConnectDB;
USE CampusConnectDB;

CREATE TABLE IF NOT EXISTS Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    password VARCHAR(255),
    role VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS LostItems (
    item_id INT PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    location VARCHAR(100),
    date VARCHAR(30),
    reporter_name VARCHAR(100),
    contact_number VARCHAR(20),
    status VARCHAR(30),
    photo_path VARCHAR(500) NULL
);

CREATE TABLE IF NOT EXISTS FoundItems (
    item_id INT PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    location VARCHAR(100),
    date VARCHAR(30),
    reporter_name VARCHAR(100),
    contact_number VARCHAR(20),
    status VARCHAR(30),
    photo_path VARCHAR(500) NULL
);

CREATE TABLE IF NOT EXISTS Claims (
    claim_id INT PRIMARY KEY,
    item_id INT NOT NULL,
    student_name VARCHAR(100),
    status VARCHAR(30)
);
