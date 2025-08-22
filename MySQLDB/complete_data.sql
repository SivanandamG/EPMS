USE epms;

-- Sample data for departments
INSERT INTO departments (name, budget) VALUES 
('Engineering', 1000000.00),
('Marketing', 500000.00),
('HR', 300000.00),
('Finance', 400000.00),
('Operations', 600000.00),
('Sales', 700000.00);

-- Sample data for employees
INSERT INTO employees (name, email, department_id, date_of_joining, salary, manager_id) VALUES 
('John Doe', 'john.doe@company.com', 1, '2020-01-15', 75000.00, NULL),
('Jane Smith', 'jane.smith@company.com', 1, '2021-03-10', 65000.00, 1),
('Mike Johnson', 'mike.johnson@company.com', 2, '2019-06-20', 55000.00, NULL),
('Sarah Wilson', 'sarah.wilson@company.com', 1, '2022-02-01', 70000.00, 1),
('Tom Brown', 'tom.brown@company.com', 3, '2021-08-15', 60000.00, NULL),
('Alice Cooper', 'alice.cooper@company.com', 4, '2020-05-10', 68000.00, NULL),
('Bob Davis', 'bob.davis@company.com', 5, '2019-11-20', 62000.00, NULL),
('Carol White', 'carol.white@company.com', 6, '2021-01-05', 58000.00, NULL),
('David Lee', 'david.lee@company.com', 1, '2022-07-12', 72000.00, 1),
('Emma Garcia', 'emma.garcia@company.com', 2, '2020-09-18', 59000.00, 3),
('Frank Miller', 'frank.miller@company.com', 3, '2021-12-03', 61000.00, 5),
('Grace Taylor', 'grace.taylor@company.com', 4, '2019-04-25', 66000.00, 6),
('Henry Clark', 'henry.clark@company.com', 5, '2022-03-14', 64000.00, 7),
('Ivy Rodriguez', 'ivy.rodriguez@company.com', 6, '2020-10-08', 57000.00, 8),
('Jack Martinez', 'jack.martinez@company.com', 1, '2021-06-22', 69000.00, 1);

-- Sample data for projects
INSERT INTO projects (name, start_date, end_date, department_id) VALUES 
('Project Alpha', '2023-01-01', '2023-12-31', 1),
('Project Beta', '2023-06-01', '2024-06-01', 1),
('Marketing Campaign', '2023-03-01', '2023-09-01', 2),
('HR System Upgrade', '2023-04-01', '2023-10-01', 3),
('Financial Analytics', '2023-02-15', '2024-02-15', 4),
('Operations Optimization', '2023-05-01', '2023-11-30', 5),
('Sales Dashboard', '2023-07-01', '2024-01-31', 6),
('Mobile App Development', '2023-08-01', '2024-08-01', 1),
('Customer Portal', '2023-09-01', '2024-03-01', 2),
('Payroll System', '2023-10-01', '2024-04-01', 3);

-- Sample data for performance reviews
INSERT INTO performance_reviews (employee_id, review_date, score, comments) VALUES 
(1, '2023-12-01', 4.5, 'Excellent performance and leadership'),
(1, '2023-06-01', 4.2, 'Good leadership skills'),
(1, '2022-12-01', 4.0, 'Consistent performer'),
(2, '2023-12-01', 4.0, 'Strong technical skills'),
(2, '2023-06-01', 3.8, 'Good progress on projects'),
(3, '2023-12-01', 3.5, 'Meeting expectations'),
(4, '2023-12-01', 4.3, 'Outstanding technical contributions'),
(5, '2023-12-01', 4.1, 'Great HR initiatives'),
(6, '2023-12-01', 4.4, 'Exceptional financial analysis'),
(6, '2023-06-01', 4.2, 'Strong budget management'),
(6, '2022-12-01', 4.0, 'Reliable financial reporting'),
(7, '2023-12-01', 3.8, 'Good operational efficiency'),
(7, '2023-06-01', 3.7, 'Process improvements'),
(7, '2022-12-01', 3.6, 'Meeting targets'),
(8, '2023-12-01', 4.2, 'Excellent sales performance'),
(8, '2023-06-01', 4.0, 'Strong client relationships'),
(8, '2022-12-01', 3.9, 'Consistent sales results'),
(9, '2023-12-01', 4.6, 'Outstanding coding skills'),
(9, '2023-06-01', 4.4, 'Innovative solutions'),
(10, '2023-12-01', 3.9, 'Creative marketing campaigns'),
(10, '2023-06-01', 3.8, 'Good brand management'),
(11, '2023-12-01', 4.0, 'Effective recruitment'),
(11, '2023-06-01', 3.8, 'Good training programs'),
(12, '2023-12-01', 4.3, 'Excellent cost analysis'),
(12, '2023-06-01', 4.1, 'Strong financial planning'),
(13, '2023-12-01', 3.7, 'Good supply chain management'),
(13, '2023-06-01', 3.6, 'Process optimization'),
(14, '2023-12-01', 4.1, 'Strong sales targets achievement'),
(14, '2023-06-01', 3.9, 'Good customer service'),
(15, '2023-12-01', 4.5, 'Exceptional development skills'),
(15, '2023-06-01', 4.3, 'Great architecture design');

-- Sample data for employee projects
INSERT INTO employee_projects (employee_id, project_id, assigned_date, role) VALUES 
(1, 1, '2023-01-01', 'Project Manager'),
(2, 1, '2023-01-15', 'Senior Developer'),
(2, 2, '2023-06-01', 'Lead Developer'),
(4, 1, '2023-02-01', 'Developer'),
(4, 2, '2023-06-15', 'Developer'),
(3, 3, '2023-03-01', 'Marketing Lead'),
(5, 4, '2023-04-01', 'HR Lead'),
(6, 5, '2023-02-15', 'Finance Lead'),
(12, 5, '2023-02-20', 'Financial Analyst'),
(7, 6, '2023-05-01', 'Operations Lead'),
(13, 6, '2023-05-05', 'Operations Analyst'),
(8, 7, '2023-07-01', 'Sales Lead'),
(14, 7, '2023-07-05', 'Sales Analyst'),
(1, 8, '2023-08-01', 'Project Manager'),
(9, 8, '2023-08-05', 'Mobile Developer'),
(15, 8, '2023-08-10', 'UI/UX Developer'),
(3, 9, '2023-09-01', 'Product Manager'),
(10, 9, '2023-09-05', 'Marketing Coordinator'),
(5, 10, '2023-10-01', 'HR Manager'),
(11, 10, '2023-10-05', 'System Analyst'),
(9, 2, '2023-06-10', 'Senior Developer'),
(15, 1, '2023-01-20', 'Backend Developer'),
(15, 2, '2023-06-20', 'Frontend Developer');

-- Insert sample client credentials
INSERT INTO api_clients (client_id, client_secret, client_name) VALUES 
('epms-client-001', 'secret123', 'EPMS Web Application'),
('epms-client-002', 'secret456', 'EPMS Mobile Application');