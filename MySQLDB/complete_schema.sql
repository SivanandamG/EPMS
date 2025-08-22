CREATE DATABASE IF NOT EXISTS epms;
USE epms;

-- Department table
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    budget DECIMAL(15,2)
);

-- Employee table
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    department_id BIGINT,
    date_of_joining DATE,
    salary DECIMAL(12,2),
    manager_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (manager_id) REFERENCES employees(id)
);

-- Project table
CREATE TABLE IF NOT EXISTS projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    start_date DATE,
    end_date DATE,
    department_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- Performance Review table
CREATE TABLE IF NOT EXISTS performance_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    review_date DATE,
    score DECIMAL(3,2),
    comments TEXT,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Employee Project junction table (Many-to-Many)
CREATE TABLE IF NOT EXISTS employee_projects (
    employee_id BIGINT,
    project_id BIGINT,
    assigned_date DATE,
    role VARCHAR(100),
    PRIMARY KEY (employee_id, project_id),
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

-- Client credentials table for API security
CREATE TABLE IF NOT EXISTS api_clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id VARCHAR(100) UNIQUE NOT NULL,
    client_secret VARCHAR(255) NOT NULL,
    client_name VARCHAR(200) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);