# REST API Documentation

Base URL:

```
http://localhost:8080
```

---

# Authentication

## Login

### Endpoint

```
POST /auth/login
```

### cURL Example

```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "admin",
    "password": "admin123"
}'
```

### Success Response

```json
{
  "timeStamp": 1771090758026,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

---

## Using the JWT Token

Include the token in the `Authorization` header:

```bash
--header 'Authorization: Bearer <your_token>'
```

Example:

```bash
curl --location 'http://localhost:8080/api/books' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...' 
```

---

# Books

## Create Book

### Endpoint

```
POST /api/books
```

### cURL Example

```bash
curl --location 'http://localhost:8080/api/books' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <your_token>' \
--data '{
    "title": "Domain-Driven Design",
    "author": "Eric Evans",
    "isbn": "978-0321125217",
    "totalCopies": 5,
    "availableCopies": 5
}'
```

---

## Get All Books

### Endpoint

```
GET /api/books
```
### cURL Example

```bash
curl --location 'http://localhost:8080/api/books' \
--header 'Authorization: Bearer <your_token>'
```

---

## Get Book By ID

### Endpoint

```
GET /api/books/{bookId}
```

### cURL
```bash
curl --location 'http://localhost:8080/api/books/1' \
--header 'Authorization: Bearer <your_token>'
```

---
# Members

### Create Member

```
POST /api/members
```

### cURL Example

```bash
curl --location 'http://localhost:8080/api/members' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <your_token>' \
--data '{
  "name": "Ahmad Doe",
  "email": "ahmad.doe@book.loan"
}'
```

---
## Get All Members

### Endpoint

```
GET /api/members
```

```bash
curl --location 'http://localhost:8080/api/members' \
--header 'Authorization: Bearer <your_token>'
```

---

## Get Member By ID

### Endpoint

```
GET /api/members/{memberId}
```
### cURL Example

```bash
curl --location 'http://localhost:8080/api/members/1' \
--header 'Authorization: Bearer <your_token>'
```

---
# Loans

## Borrow Book

### Endpoint

```
POST /api/loans
```

### Body

```json
{
  "book_id": 1,
  "member_id": 1
}
```

### cURL Example

```bash
curl --location 'http://localhost:8080/api/loans' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <your_token>' \
--data '{
    "book_id": 1,
    "member_id": 1
}'
```

---

## Return Book

### Endpoint

```
PUT /api/loans/{id}/return
```

### Body

```json
{
  "book_id": 1,
  "member_id": 1
}
```

### cURL

```bash
curl --location --request PUT 'http://localhost:8080/api/loans/1/return' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <your_token>' \
--data '{
    "book_id": 1,
    "member_id": 1
}'
```

---

## Get All Loans

### Endpoint
```
GET /api/loans
```

```bash
curl --location 'http://localhost:8080/api/loans' \
--header 'Authorization: Bearer <your_token>'
```

---
