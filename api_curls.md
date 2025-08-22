# EPMS API cURL Commands

## Authentication
All API endpoints require client credentials in headers:
- `X-Client-Id`: Client identifier
- `X-Client-Secret`: Client secret

## Available Client Credentials
```bash
# Client 1
CLIENT_ID="epms-client-001"
CLIENT_SECRET="secret123"

# Client 2 (Alternative)
CLIENT_ID="epms-client-002"
CLIENT_SECRET="secret456"
```

## Base URL
```bash
BASE_URL="http://localhost:8080"
CLIENT_ID="epms-client-001"
CLIENT_SECRET="secret123"
```

## 1. Get All Employees (Paginated)
```bash
# Get first page (page 0) with default size 5
curl -X GET "${BASE_URL}/api/employees" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Get specific page with size 5
curl -X GET "${BASE_URL}/api/employees?page=0&size=5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Get second page
curl -X GET "${BASE_URL}/api/employees?page=1&size=5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"
```

## 2. Get Employees with Performance Score Filter
```bash
# Minimum performance score >= 4.0
curl -X GET "${BASE_URL}/api/employees?performanceScore=4.0" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Minimum performance score >= 3.5
curl -X GET "${BASE_URL}/api/employees?performanceScore=3.5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"
```

## 3. Get Employees by Review Date
```bash
# Specific review date
curl -X GET "${BASE_URL}/api/employees?reviewDate=2023-12-01" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Different review date
curl -X GET "${BASE_URL}/api/employees?reviewDate=2023-06-15" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"
```

## 4. Get Employees by Department IDs
```bash
# Single department
curl -X GET "${BASE_URL}/api/employees?departmentIds=1" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Multiple departments
curl -X GET "${BASE_URL}/api/employees?departmentIds=1,2,3" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"
```

## 5. Get Employees by Project IDs
```bash
# Single project
curl -X GET "${BASE_URL}/api/employees?projectIds=1" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Multiple projects
curl -X GET "${BASE_URL}/api/employees?projectIds=1,2" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"
```

## 6. Get Employees by Performance Score for Specific Review Date (Paginated)
```bash
# Performance score >= 4.0 for review date 2023-12-01 with pagination
curl -X GET "${BASE_URL}/api/employees?performanceScore=4.0&reviewDate=2023-12-01&page=0&size=5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Performance score >= 3.5 for review date 2023-06-15 with pagination
curl -X GET "${BASE_URL}/api/employees?performanceScore=3.5&reviewDate=2023-06-15&page=0&size=5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Quick test - Direct values with pagination
curl -X GET "http://localhost:8080/api/employees?performanceScore=4.0&reviewDate=2023-12-01&page=0&size=5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: epms-client-001" \
  -H "X-Client-Secret: secret123"
```

## 7. Combined Filters with Pagination
```bash
# Performance score + department with pagination
curl -X GET "${BASE_URL}/api/employees?performanceScore=4.0&departmentIds=1&page=0&size=5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Performance score + projects with pagination
curl -X GET "${BASE_URL}/api/employees?performanceScore=3.5&projectIds=1,2&page=0&size=5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# All filters combined with pagination
curl -X GET "${BASE_URL}/api/employees?performanceScore=4.0&reviewDate=2023-12-01&departmentIds=1,2&projectIds=1&page=0&size=5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"
```

## 7. Get Employee Details by ID
```bash
# Employee ID 1
curl -X GET "${BASE_URL}/api/employees/1" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Employee ID 2
curl -X GET "${BASE_URL}/api/employees/2" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Employee ID 5
curl -X GET "${BASE_URL}/api/employees/5" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"
```

## 8. Test with Pretty JSON Output
```bash
# Get all employees with formatted JSON
curl -X GET "${BASE_URL}/api/employees" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}" | jq '.'

# Get employee details with formatted JSON
curl -X GET "${BASE_URL}/api/employees/1" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}" | jq '.'
```

## 9. Authentication Error Testing
```bash
# Missing authentication headers
curl -X GET "${BASE_URL}/api/employees" \
  -H "Content-Type: application/json"

# Invalid client credentials
curl -X GET "${BASE_URL}/api/employees" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: invalid-client" \
  -H "X-Client-Secret: invalid-secret"

# Missing client secret
curl -X GET "${BASE_URL}/api/employees" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}"
```

## 10. Data Validation Error Testing
```bash
# Non-existent employee ID
curl -X GET "${BASE_URL}/api/employees/999" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Invalid performance score
curl -X GET "${BASE_URL}/api/employees?performanceScore=invalid" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"

# Invalid date format
curl -X GET "${BASE_URL}/api/employees?reviewDate=invalid-date" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}"
```

## 11. Batch Testing Script
```bash
#!/bin/bash
BASE_URL="http://localhost:8080"
CLIENT_ID="epms-client-001"
CLIENT_SECRET="secret123"

echo "Testing EPMS API endpoints..."

echo "1. Get all employees:"
curl -s -X GET "${BASE_URL}/api/employees" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}" | jq '.[0:2]'

echo -e "\n2. Filter by performance score:"
curl -s -X GET "${BASE_URL}/api/employees?performanceScore=4.0" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}" | jq '.[0:1]'

echo -e "\n3. Get employee details:"
curl -s -X GET "${BASE_URL}/api/employees/1" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}" | jq '.name, .department.name'

echo -e "\n4. Filter by department:"
curl -s -X GET "${BASE_URL}/api/employees?departmentIds=1" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: ${CLIENT_ID}" \
  -H "X-Client-Secret: ${CLIENT_SECRET}" | jq 'length'

echo "API testing completed."
```

## 12. Quick Test Commands (Direct Values)
```bash
# Quick test - Get all employees
curl -X GET "http://localhost:8080/api/employees" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: epms-client-001" \
  -H "X-Client-Secret: secret123"

# Quick test - Get employee details
curl -X GET "http://localhost:8080/api/employees/1" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: epms-client-001" \
  -H "X-Client-Secret: secret123"

# Quick test - Filter by performance score
curl -X GET "http://localhost:8080/api/employees?performanceScore=4.0" \
  -H "Content-Type: application/json" \
  -H "X-Client-Id: epms-client-001" \
  -H "X-Client-Secret: secret123"
```