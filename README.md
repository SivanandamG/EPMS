# Employee Performance Management System (EPMS)

## Overview
REST API for Employee Performance Management System built with Spring Boot, JPA, and MySQL.

## Database Setup
1. Run MySQL server on port 3306
2. Execute schema creation:
   ```bash
   mysql -u root -p < MySQLDB/schema.sql
   ```
3. Insert sample data:
   ```bash
   mysql -u root -p < MySQLDB/sample_data.sql
   ```

## API Endpoints

### 1. Get Employees with Filters
**GET** `/api/employees`

**Query Parameters:**
- `performanceScore` (optional): Minimum performance score (decimal)
- `reviewDate` (optional): Specific review date (YYYY-MM-DD)
- `departmentIds` (optional): List of department IDs (comma-separated)
- `projectIds` (optional): List of project IDs (comma-separated)
- `page` (optional, default: 0): Page number (0-based)
- `size` (optional, default: 5): Page size (1-100)

**Examples:**
```bash
# Get all employees (first page, size 5)
GET /api/employees

# Get specific page
GET /api/employees?page=1&size=5

# Filter by performance score >= 4.0 with pagination
GET /api/employees?performanceScore=4.0&page=0&size=5

# Filter by review date
GET /api/employees?reviewDate=2023-12-01

# Filter by departments
GET /api/employees?departmentIds=1,2

# Filter by projects
GET /api/employees?projectIds=1,2

# Combined filters
GET /api/employees?performanceScore=4.0&departmentIds=1&projectIds=1,2
```

### 2. Get Employee Details
**GET** `/api/employees/{id}`

Returns detailed employee information including:
- Basic employee info
- Department details
- Associated projects with roles
- Last 3 performance reviews

**Example:**
```bash
GET /api/employees/1
```

## Response Format

### Employee List Response (Paginated)
```json
{
  "employees": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@company.com",
      "departmentName": "Engineering",
      "dateOfJoining": "2020-01-15",
      "salary": 75000.00,
      "managerName": null
    }
  ],
  "currentPage": 0,
  "totalPages": 3,
  "totalElements": 15,
  "pageSize": 5,
  "hasNext": true,
  "hasPrevious": false
}
```

### Employee Detail Response
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@company.com",
  "department": {
    "id": 1,
    "name": "Engineering",
    "budget": 1000000.00
  },
  "dateOfJoining": "2020-01-15",
  "salary": 75000.00,
  "managerName": null,
  "projects": [
    {
      "id": 1,
      "name": "Project Alpha",
      "startDate": "2023-01-01",
      "endDate": "2023-12-31",
      "role": "Project Manager",
      "assignedDate": "2023-01-01"
    }
  ],
  "lastThreeReviews": [
    {
      "id": 1,
      "reviewDate": "2023-12-01",
      "score": 4.5,
      "comments": "Excellent performance and leadership"
    }
  ]
}
```

## Running the Application
1. Ensure MySQL is running with correct credentials in `application.properties`
2. Run: `mvn spring-boot:run`
3. API will be available at: `http://localhost:8080/api/employees`

## Database Schema
- **employees**: Employee information with department and manager references
- **departments**: Department details with budget
- **projects**: Project information linked to departments
- **performance_reviews**: Employee performance reviews with scores
- **employee_projects**: Many-to-many relationship between employees and projects