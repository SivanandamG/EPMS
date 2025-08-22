# Employee Performance Management System (EPMS)

## Overview
Scalable REST API for Employee Performance Management System built with Spring Boot, JPA, MySQL, and enterprise-grade security features.

## Architecture & Design Patterns

### Design Patterns Implemented
- **Repository Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic separation
- **DTO Pattern**: Data transfer objects for API responses
- **Aspect-Oriented Programming (AOP)**: Cross-cutting concerns (security)
- **Filter Chain Pattern**: Request processing pipeline
- **Exception Handling Pattern**: Centralized error management

### Key Features
- ✅ Client-based API authentication using AOP
- ✅ Rate limiting and request validation filters
- ✅ Comprehensive exception handling with custom exceptions
- ✅ Stream API usage for data processing
- ✅ Optional class for null safety
- ✅ Extensive logging and monitoring
- ✅ Complete test coverage (Unit, Integration, Repository tests)

## Security Implementation

### Client Authentication
API endpoints are secured using custom `@SecureApi` annotation with AOP:

```java
@GetMapping
@SecureApi
public ResponseEntity<List<EmployeeDTO>> getEmployees(...)
```

**Required Headers:**
- `X-Client-Id`: Client identifier
- `X-Client-Secret`: Client secret key

**Sample Client Credentials:**
- Client ID: `epms-client-001`
- Client Secret: `secret123`

### Filters
1. **RequestValidationFilter**: Request logging and validation
2. **RateLimitFilter**: API rate limiting (100 requests/minute per IP)

## Database Setup

### 1. Create Client Credentials Table
```bash
mysql -u root -p < MySQLDB/client_schema.sql
```

### 2. Create Main Schema
```bash
mysql -u root -p < MySQLDB/schema.sql
```

### 3. Insert Sample Data
```bash
mysql -u root -p < MySQLDB/sample_data.sql
mysql -u root -p < MySQLDB/extended_data.sql
```

## API Endpoints

### 1. Get Employees with Filters
**GET** `/api/employees`

**Headers:**
```
X-Client-Id: epms-client-001
X-Client-Secret: secret123
```

**Query Parameters:**
- `performanceScore` (optional): Minimum performance score (0.0-5.0)
- `reviewDate` (optional): Specific review date (YYYY-MM-DD)
- `departmentIds` (optional): List of department IDs (comma-separated)
- `projectIds` (optional): List of project IDs (comma-separated)

**Examples:**
```bash
# Get all employees
curl -H "X-Client-Id: epms-client-001" -H "X-Client-Secret: secret123" \
  http://localhost:8080/api/employees

# Filter by performance score >= 4.0
curl -H "X-Client-Id: epms-client-001" -H "X-Client-Secret: secret123" \
  "http://localhost:8080/api/employees?performanceScore=4.0"

# Combined filters
curl -H "X-Client-Id: epms-client-001" -H "X-Client-Secret: secret123" \
  "http://localhost:8080/api/employees?performanceScore=4.0&departmentIds=1,2&projectIds=1,2"
```

### 2. Get Employee Details
**GET** `/api/employees/{id}`

**Headers:**
```
X-Client-Id: epms-client-001
X-Client-Secret: secret123
```

**Example:**
```bash
curl -H "X-Client-Id: epms-client-001" -H "X-Client-Secret: secret123" \
  http://localhost:8080/api/employees/1
```

## Exception Handling

### Custom Exceptions
- `EmployeeNotFoundException`: Employee not found scenarios
- `InvalidFilterException`: Invalid filter parameters
- `UnauthorizedException`: Authentication failures

### Error Response Format
```json
{
  "timestamp": "2023-12-01T10:30:00",
  "status": 404,
  "error": "Employee Not Found",
  "message": "Employee not found with id: 999"
}
```

## Testing

### Run All Tests
```bash
mvn test
```

### Test Categories
1. **Unit Tests**: Service and component testing
2. **Integration Tests**: End-to-end API testing
3. **Repository Tests**: Data access layer testing
4. **AOP Tests**: Security aspect testing

### Test Coverage
- EmployeeService: Business logic validation
- EmployeeController: API endpoint testing
- SecurityAspect: Authentication testing
- Repository: Data access testing

## Running the Application

### 1. Database Setup
Ensure MySQL is running and execute schema scripts

### 2. Application Properties
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/epms
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3. Start Application
```bash
mvn spring-boot:run
```

### 4. Test API
```bash
curl -H "X-Client-Id: epms-client-001" -H "X-Client-Secret: secret123" \
  http://localhost:8080/api/employees
```

## Performance Features

### Stream API Usage
- Efficient data processing and filtering
- Functional programming approach
- Memory-efficient operations

### Optional Class
- Null safety in service methods
- Explicit handling of optional values
- Reduced NullPointerException risks

### Logging & Monitoring
- Request/response logging
- Performance metrics
- Error tracking
- Client authentication logs

## Production Considerations

### Security
- Client credential rotation
- Rate limiting per client
- Request validation
- Comprehensive audit logging

### Scalability
- Stateless design
- Database connection pooling
- Efficient query optimization
- Caching strategies (can be added)

### Monitoring
- Application metrics
- Health checks
- Performance monitoring
- Error alerting

## Technology Stack
- **Framework**: Spring Boot 3.5.4
- **Database**: MySQL 8.0
- **ORM**: JPA/Hibernate
- **Security**: Custom AOP-based authentication
- **Testing**: JUnit 5, Mockito, H2 (test database)
- **Build Tool**: Maven
- **Java Version**: 17