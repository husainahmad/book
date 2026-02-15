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
### Success Response

```json
{
  "timeStamp": 1771090758026,
  "data": {
    "id": 1,
    "title": "Domain-Driven Design",
    "author": "Eric Evans",
    "isbn": "978-0321125217",
    "totalCopies": 5,
    "availableCopies": 5
  }
}
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
### Success Response

```json
{
  "timeStamp": 1771090758026,
  "data": [
    {
      "id": 1,
      "title": "Domain-Driven Design",
      "author": "Eric Evans",
      "isbn": "978-0321125217",
      "totalCopies": 5,
      "availableCopies": 5
    },
    {
      "id": 2,
      "title": "Clean Code",
      "author": "Robert C. Martin",
      "isbn": "978-0132350884",
      "totalCopies": 3,
      "availableCopies": 3
    }
  ]
}
```
---

## Get Book By ID

### Endpoint

```
GET /api/books/{id}
```

### cURL
```bash
curl --location 'http://localhost:8080/api/books/1' \
--header 'Authorization: Bearer <your_token>'
```
### Success Response

```json
{
  "timeStamp": 1771090758026,
  "data": {
    "id": 1,
    "title": "Domain-Driven Design",
    "author": "Eric Evans",
    "isbn": "978-0321125217",
    "totalCopies": 5,
    "availableCopies": 5
  }
}
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
### Success Response

```json
{
  "timeStamp": 1771090758026,
  "data": {
    "id": 1,
    "name": "Ahmad Doe",
    "email": "ahmad.doe@book.loan"
  }
}
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

### Success Response

```json
{
  "timeStamp": 1771090758026,
  "data": {
    "id": 1,
    "name": "Ahmad Doe",
    "email": "ahmad.doe@book.loan"
  }
}
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

### Success Response

```json
{
  "timeStamp": 1771126145448,
  "data": {
    "id": 4,
    "book_id": 1,
    "book": {
      "title": "Effective Java",
      "author": "Joshua Bloch",
      "isbn": "978-0134685991",
      "total_copies": 0,
      "available_copies": 0
    },
    "member_id": 1,
    "member": {
      "name": "Jhon Doe",
      "email": "jhon.doe@book.loan"
    },
    "borrowed_at": "2026-02-15T10:29:05.433061",
    "due_date": "2026-02-20T10:29:05.433162"
  }
}
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

### Success Response
```json
{
  "timeStamp": 1771170587712,
  "data": {
    "id": 1,
    "book": {
      "title": "Effective Java",
      "author": "Joshua Bloch",
      "isbn": "978-0134685991",
      "total_copies": 0,
      "available_copies": 0
    },
    "book_id": 1,
    "member": {
      "name": "Jhon Doe",
      "email": "jhon.doe@book.loan"
    },
    "member_id": 1,
    "borrowed_at": "2026-02-15T00:31:43",
    "due_date": "2026-02-16T00:31:44",
    "returned_at": "2026-02-15T22:49:47.706899"
  }
}
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
### Success Response

```json
{
  "timeStamp": 1771170500743,
  "data": [
    {
      "id": 1,
      "book": {
        "title": "Effective Java",
        "author": "Joshua Bloch",
        "isbn": "978-0134685991",
        "total_copies": 0,
        "available_copies": 0
      },
      "book_id": 1,
      "member_id": 1,
      "member": {
        "name": "Jhon Doe",
        "email": "jhon.doe@book.loan"
      },
      "borrowed_at": "2026-02-15T00:31:43",
      "due_date": "2026-02-16T00:31:44",
      "returned_at": "2026-02-15T10:12:20"
    },
    {
      "id": 2,
      "book": {
        "title": "Clean Code",
        "author": "Robert C. Martin",
        "isbn": "978-0132350884",
        "total_copies": 0,
        "available_copies": 0
      },
      "book_id": 2,
      "member_id": 1,
      "member": {
        "name": "Jhon Doe",
        "email": "jhon.doe@book.loan"
      },
      "borrowed_at": "2026-02-15T00:46:32",
      "due_date": "2026-02-16T00:46:32"
    }
  ]
}
```
---
