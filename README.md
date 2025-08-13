# Users Service API

A service to manage the buyers and sellers for the blind auction application. 

**Base URL:** `http://localhost:8080/api/v1`
**Open Api docs:** `http://localhost:8080/api/v1/swagger-ui/index.html`

---

## Overview

The Users Service API provides secure user authentication, token generation, validation, and user detail retrieval. It also manages user privileges and roles that control access to different resources and operations.

This service supports:

- User login with opaque token generation
- Token validation to authenticate users
- Retrieving detailed user information and subsets such as contact, profile, and summary
- Privilege and role-based access control

---

## Key Features

- **Token-Based Authentication:**  
  Generates opaque tokens for user sessions and validates tokens on protected endpoints.

- **Role and Privilege Management:**  
  Users have assigned roles, each associated with specific privileges controlling API access.

- **User Information Endpoints:**  
  Retrieve full user details or specific subsets (contact, profile, summary) depending on privileges.

---

## API Endpoints

### Authentication

- **POST /auth/token**  
  Generates an opaque token based on username and password.

- **GET /auth/validate**  
  Validates a token and returns user details including their scopes/privileges.

### User Information

All user endpoints require Bearer token authorization with relevant privileges.

- **GET /users/{userId}** — Retrieve full user details for the logged-in user 
- **GET /users/{userId}/contact** — Retrieve contact details  
- **GET /users/{userId}/profile** — Retrieve profile details  
- **GET /users/{userId}/summary** — Retrieve basic user details summary  

---

## Roles and Privileges

This service uses a set of predefined roles and privileges stored in the database to control access.

### Privileges

| Code                   | Description                  |
|------------------------|------------------------------|
| user:read_summary      | Access user summary info      |
| user:read_contact      | Access user contact info      |
| user:read_profile      | Access user profile info      |
| auction:view           | View auctions                |
| auction:create         | Create auctions             |
| auction:close          | Close auctions              |
| auctionedProduct:view  | View auctioned products      |
| auctionedProduct:create| Create auctioned products    |
| bid:create             | Create bids                  |

### Roles

| Code   | Associated Privileges                                           |
|--------|----------------------------------------------------------------|
| seller | user:read_contact, user:read_summary, auction:create, auction:view, auction:close, auctionedProduct:view, auctionedProduct:create |
| buyer  | user:read_profile, user:read_summary, bid:create, auction:view, auctionedProduct:view |


