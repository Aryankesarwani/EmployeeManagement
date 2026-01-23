#!/bin/bash

echo "Testing GraphQL Endpoint..."
echo ""

# Test if server is running
echo "1. Checking if server is running on port 8080..."
if curl -s http://localhost:8080/graphiql > /dev/null 2>&1; then
    echo "✓ Server is running"
else
    echo "✗ Server is NOT running or not accessible"
    echo "Please start your Spring Boot application first: mvn spring-boot:run"
    exit 1
fi

echo ""
echo "2. Testing GraphQL mutation..."

curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { addEmployee(employeeDTO: { name: \"Test User\", email: \"test@example.com\", phone: \"1234567890\", address: \"Test Address\", designation: \"Developer\", CTC: 5.5 }) { id name email } }"
  }' \
  -w "\n\nHTTP Status: %{http_code}\n" \
  -v

echo ""
echo "3. You can also test manually at: http://localhost:8080/graphiql"
