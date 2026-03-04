<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.11-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java"/>
  <img src="https://img.shields.io/badge/MySQL-8.x-blue?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/JWT-Auth-red?style=for-the-badge&logo=jsonwebtokens" alt="JWT"/>
  <img src="https://img.shields.io/badge/License-Proprietary-lightgrey?style=for-the-badge" alt="License"/>
</p>

# 🏥 MediCloud ERP — Backend

**Cloud-based Medical Store ERP SaaS Backend** built with Spring Boot.  
Supports **multi-tenant architecture**, billing, inventory management, supplier management, subscription plans, and role-based access control — designed to power any number of independent medical stores from a single deployment.

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Features](#-features)
- [Security Features](#-security-features)
- [API Documentation (Swagger)](#-api-documentation-swagger)
- [Setup Instructions](#-setup-instructions)
- [Database Configuration](#-database-configuration)
- [Environment Variables](#-environment-variables)
- [API Authentication Flow](#-api-authentication-flow)
- [Example API Requests](#-example-api-requests)
- [Error Handling](#-error-handling)
- [Performance Considerations](#-performance-considerations)
- [Future Improvements](#-future-improvements)
- [Author](#-author)

---

## 🏢 Project Overview

MediCloud ERP is an enterprise-grade, multi-tenant SaaS backend engineered for medical store operations. Each registered **Shop** operates as an isolated tenant — its medicines, bills, suppliers, and staff are completely separated from every other shop on the platform.

### What It Does

| Domain | Capability |
|--------|-----------|
| **Shop Onboarding** | Register new medical stores with a single API call |
| **User Management** | Create Owner, Admin, and Staff accounts per shop |
| **Inventory** | Add, list, and track medicines with batch numbers, expiry dates, and stock quantities |
| **Billing** | Create bills that automatically deduct stock and calculate totals |
| **Supplier Management** | Maintain supplier directory per shop |
| **Dashboard** | Real-time metrics — today's sale, total revenue, profit, low-stock alerts |
| **Subscriptions** | Activate and manage subscription plans per shop |
| **Super Admin** | Platform-wide oversight — view all shops and subscriptions |

---

## 🛠 Tech Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Language** | Java | 21 (LTS) |
| **Framework** | Spring Boot | 3.5.11 |
| **Security** | Spring Security | 6.x |
| **Authentication** | JSON Web Tokens (JWT) | jjwt 0.12.6 |
| **ORM** | Hibernate / Spring Data JPA | 6.x |
| **Database** | MySQL | 8.x+ |
| **Build Tool** | Apache Maven | 3.9+ (wrapper included) |
| **API Docs** | SpringDoc OpenAPI (Swagger UI) | 2.8.6 |
| **Validation** | Jakarta Bean Validation | 3.x |
| **Connection Pool** | HikariCP | (bundled with Spring Boot) |
| **Object Mapping** | ModelMapper | 3.1.1 |
| **Monitoring** | Spring Boot Actuator | (bundled) |

---

## 📁 Project Structure

```
src/main/java/com/medicloud/
│
├── MedicloudBackendApplication.java          # Application entry point
│
├── config/                                    # Application-level configuration
│   ├── AppConfig.java                         # BCryptPasswordEncoder bean
│   ├── DataInitializer.java                   # Seeds default super-admin on first run
│   ├── ModelMapperConfig.java                 # ModelMapper bean
│   └── OpenApiConfig.java                     # Swagger / OpenAPI 3.0 configuration
│
├── common/                                    # Cross-cutting concerns
│   ├── constants/                             # Application constants
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java        # Centralized @RestControllerAdvice
│   │   ├── ResourceNotFoundException.java     # 404 exception
│   │   ├── BusinessException.java             # 400 business rule violation
│   │   └── CustomException.java               # General-purpose exception
│   ├── response/
│   │   └── ApiResponse.java                   # Unified API response wrapper
│   └── util/
│       ├── TenantUtil.java                    # Extracts shopId from JWT context
│       ├── SecurityUtil.java                  # Current user email helper
│       ├── DateUtil.java                      # Date formatting utilities
│       └── JwtUtil.java                       # JWT utility helpers
│
├── security/                                  # Security layer
│   ├── config/
│   │   └── SecurityConfig.java                # Filter chain, RBAC rules, CORS
│   ├── jwt/
│   │   ├── JwtAuthFilter.java                 # OncePerRequestFilter — token extraction
│   │   └── JwtService.java                    # Token generation, validation, claim parsing
│   └── service/
│       └── CustomUserDetailsService.java      # UserDetailsService (Admin + User tables)
│
├── infrastructure/                            # Infrastructure concerns
│   ├── config/
│   │   ├── CorsConfig.java                    # CORS origin rules
│   │   └── JpaAuditingConfig.java             # @EnableJpaAuditing
│   ├── external/
│   │   ├── notification/EmailService.java     # Email integration (placeholder)
│   │   └── razorpay/                          # Payment gateway integration
│   └── repository/
│       └── BaseRepository.java                # Shared repository interface
│
└── modules/                                   # Business modules (domain-driven)
    ├── auth/                                  # Authentication module
    │   ├── controller/AuthController.java
    │   ├── dto/ (LoginRequest, RegisterRequest)
    │   ├── entity/Role.java                   # SUPER_ADMIN, OWNER, ADMIN, STAFF
    │   ├── repository/
    │   └── service/AuthService.java
    │
    ├── billing/                               # Billing module
    │   ├── controller/BillController.java
    │   ├── dto/ (BillRequest, BillResponse)
    │   ├── entity/ (Bill, BillItem)
    │   ├── repository/BillRepository.java
    │   └── service/BillService.java
    │
    ├── dashboard/                             # Dashboard analytics
    │   ├── controller/DashboardController.java
    │   ├── dto/DashboardResponse.java
    │   └── service/DashboardService.java
    │
    ├── medicine/                              # Inventory management
    │   ├── controller/MedicineController.java
    │   ├── dto/ (MedicineRequest, MedicineResponse)
    │   ├── entity/Medicine.java
    │   ├── mapper/MedicineMapper.java
    │   ├── repository/MedicineRepository.java
    │   └── service/MedicineService.java
    │
    ├── shop/                                  # Shop (tenant) management
    │   ├── controller/ShopController.java
    │   ├── dto/ (ShopRequest, ShopResponse)
    │   ├── entity/Shop.java
    │   ├── mapper/ShopMapper.java
    │   ├── repository/ShopRepository.java
    │   └── service/ShopService.java
    │
    ├── subscription/                          # Subscription plans
    │   ├── controller/SubscriptionController.java
    │   ├── dto/SubscriptionResponse.java
    │   ├── entity/ (Subscription, Plan)
    │   ├── repository/SubscriptionRepository.java
    │   └── service/SubscriptionService.java
    │
    ├── supplier/                              # Supplier management
    │   ├── controller/SupplierController.java
    │   ├── dto/ (SupplierRequest, SupplierResponse)
    │   ├── entity/Supplier.java
    │   ├── mapper/SupplierMapper.java
    │   ├── repository/SupplierRepository.java
    │   └── service/SupplierService.java
    │
    └── user/                                  # User management
        ├── controller/UserController.java
        ├── dto/ (UserRequest, UserResponse)
        ├── entity/User.java
        ├── mapper/UserMapper.java
        ├── repository/UserRepository.java
        └── service/UserService.java

superadmin/                                    # Platform administration
    ├── controller/AdminController.java
    ├── dto/AdminRequest.java
    ├── entity/Admin.java
    ├── repository/AdminRepository.java
    └── service/AdminService.java
```

### Architecture Layers

| Layer | Package | Responsibility |
|-------|---------|---------------|
| **Controller** | `*.controller` | REST endpoints, request validation, HTTP status codes |
| **Service** | `*.service` | Business logic, transaction management, tenant isolation |
| **Repository** | `*.repository` | Data access via Spring Data JPA |
| **Entity** | `*.entity` | JPA entity mapping to database tables |
| **DTO** | `*.dto` | Request/response data transfer objects with validation |
| **Mapper** | `*.mapper` | Entity ↔ DTO conversion |
| **Config** | `config/`, `security/config/` | Beans, security filter chain, CORS, OpenAPI |
| **Exception** | `common/exception/` | Global error handling |

---

## ✨ Features

### Authentication & Authorization
- **JWT-based stateless authentication** — login returns a signed token containing email, shopId, and role
- **User registration** with BCrypt password hashing (strength 12)
- **Role-Based Access Control (RBAC)** with four roles: `SUPER_ADMIN`, `OWNER`, `ADMIN`, `STAFF`
- **Method-level security** via `@EnableMethodSecurity`

### Multi-Tenant Architecture
- Every API operation is scoped to the **current shop** extracted from the JWT token
- `TenantUtil` reads `shopId` from the request context — zero database queries for tenant resolution
- Complete data isolation between shops

### Inventory Management
- Add medicines with batch number, company, expiry date, purchase/selling price
- List all medicines for the current shop
- Automatic stock deduction on billing
- Low-stock alerts on the dashboard

### Billing
- Create bills with multiple medicine line items
- Automatic stock quantity deduction (transactional)
- Per-item total and bill grand total calculation using `BigDecimal` (precise currency arithmetic)

### Supplier Management
- Full CRUD for supplier directory per shop
- Delete restricted to `OWNER` role

### Dashboard Analytics
- Today's sale (date-range query)
- Total revenue and profit calculations
- Low-stock medicine count

### Subscription Management
- Activate subscription plans for shops
- Plan renewal logic for existing subscriptions
- Active subscription status check

### Super Admin Panel
- Platform-wide shop listing
- Cross-tenant subscription visibility
- Separate authentication flow

### Shop Onboarding
- Public endpoint to register new medical stores
- Auto-seeds default super-admin on first startup

---

## 🔐 Security Features

| Feature | Implementation |
|---------|---------------|
| **Authentication** | JWT Bearer tokens (HMAC-SHA256 signing) |
| **Password Storage** | BCrypt with strength factor 12 |
| **Role Authorization** | Spring Security `hasAuthority()` + `@EnableMethodSecurity` |
| **Stateless Sessions** | `SessionCreationPolicy.STATELESS` — no server-side sessions |
| **CSRF Protection** | Disabled (stateless API — CSRF not applicable) |
| **CORS** | Restricted to `localhost:3000` and `localhost:5173` |
| **Security Headers** | `X-Frame-Options: DENY`, `X-Content-Type-Options: nosniff` |
| **Input Validation** | Jakarta Bean Validation (`@NotBlank`, `@Email`, `@Positive`, `@Size`) |
| **Tenant Isolation** | shopId extracted from JWT — enforced on every data query |
| **Token Expiration** | Configurable via `jwt.expiration` (default: 24 hours) |
| **Error Masking** | Generic 500 messages in production — full details logged server-side |

### RBAC Matrix

| Endpoint | SUPER_ADMIN | OWNER | ADMIN | STAFF |
|----------|:-----------:|:-----:|:-----:|:-----:|
| `POST /auth/login` | — | ✅ | ✅ | ✅ |
| `POST /auth/register` | — | ✅ | ✅ | ✅ |
| `POST /superadmin/login` | ✅ | — | — | — |
| `GET /superadmin/**` | ✅ | ❌ | ❌ | ❌ |
| `POST /users` | ❌ | ✅ | ✅ | ❌ |
| `GET /users/{id}` | ❌ | ✅ | ✅ | ❌ |
| `DELETE /suppliers/{id}` | ❌ | ✅ | ❌ | ❌ |
| `POST /medicines` | ❌ | ✅ | ✅ | ✅ |
| `POST /billing/create` | ❌ | ✅ | ✅ | ✅ |
| `GET /dashboard` | ❌ | ✅ | ✅ | ✅ |

---

## 📄 API Documentation (Swagger)

This project uses **SpringDoc OpenAPI 3.0** with **Swagger UI** for interactive API documentation.

### Access Swagger UI

Once the application is running, navigate to:

```
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI JSON Spec

```
http://localhost:8080/v3/api-docs
```

### How to Test APIs Using Swagger

1. **Open** Swagger UI at `http://localhost:8080/swagger-ui/index.html`
2. **Login first** — expand `POST /auth/login` (or `POST /superadmin/login`), click **Try it out**, enter credentials, and execute
3. **Copy the JWT token** from the response
4. **Authorize** — click the 🔒 **Authorize** button at the top of the page
5. **Paste your token** in the `Bearer Authentication` field (no need to add the `Bearer ` prefix)
6. **Click Authorize** → now all subsequent API calls will include your token
7. **Test any endpoint** — expand it, click **Try it out**, fill parameters, and execute

### API Tags

| Tag | Endpoints |
|-----|-----------|
| Authentication | `/auth/login`, `/auth/register` |
| Super Admin | `/superadmin/login`, `/superadmin/shops`, `/superadmin/subscriptions` |
| User Management | `/users` |
| Shop Management | `/shops` |
| Medicine / Inventory | `/medicines` |
| Billing | `/billing/create` |
| Supplier Management | `/suppliers` |
| Dashboard | `/dashboard` |
| Subscription | `/api/subscriptions/health` |

---

## 🚀 Setup Instructions

### Prerequisites

- **Java 21** (LTS) — [Download](https://adoptium.net/)
- **MySQL 8.x** — [Download](https://dev.mysql.com/downloads/)
- **Maven 3.9+** (or use the included Maven wrapper)
- **Git**

### Step-by-Step

#### 1. Clone the Repository

```bash
git clone https://github.com/your-username/medicloud-backend.git
cd medicloud-backend
```

#### 2. Create the MySQL Database

```sql
CREATE DATABASE medicloud_db;
```

> The application uses `createDatabaseIfNotExist=true`, so the database will be auto-created on first run if it doesn't exist.

#### 3. Configure Database Credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medicloud_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

#### 4. Configure JWT Secret

```properties
jwt.secret=your-very-long-secret-key-at-least-256-bits-for-hmac-sha256
jwt.expiration=86400000
```

#### 5. Install Dependencies & Build

```bash
# Using Maven wrapper (no Maven installation required)
./mvnw clean install

# Or with Maven installed globally
mvn clean install
```

#### 6. Run the Application

```bash
./mvnw spring-boot:run
```

#### 7. Verify

- **Health Check:** `http://localhost:8080/actuator/health`
- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
- **Default Super Admin:** `admin@medicloud.com` / `admin123`

> ⚠️ **Change the default super-admin password immediately in production.**

---

## 🗄 Database Configuration

### Connection Properties

| Property | Value |
|----------|-------|
| **Driver** | `com.mysql.cj.jdbc.Driver` |
| **URL** | `jdbc:mysql://localhost:3306/medicloud_db` |
| **DDL Mode** | `update` (auto-creates/updates tables) |
| **SQL Logging** | Disabled (`show-sql=false`) |
| **Connection Pool** | HikariCP (max 10, min idle 2) |
| **Batch Size** | 25 (inserts and updates) |

### Entity-Relationship Summary

```
┌──────────┐       ┌───────────┐       ┌──────────────┐
│  Shop    │1────*│   User    │       │   Admin      │
│          │       │  (staff)  │       │ (superadmin) │
└────┬─────┘       └───────────┘       └──────────────┘
     │
     │1
     ├──────────*┐
     │           │
┌────┴─────┐ ┌───┴────────┐ ┌───────────┐ ┌──────────────┐
│ Medicine │ │  Supplier  │ │   Bill    │ │ Subscription │
│          │ │            │ │           │ │              │
└──────────┘ └────────────┘ └─────┬─────┘ └──────┬───────┘
                                  │1              │*
                                  │               │
                            ┌─────┴─────┐   ┌────┴────┐
                            │ BillItem  │   │  Plan   │
                            └───────────┘   └─────────┘
```

### Tables Auto-Generated

| Table | Description |
|-------|-------------|
| `admins` | Platform super-admin accounts |
| `users` | Shop staff (OWNER, ADMIN, STAFF) |
| `shops` | Registered medical stores (tenants) |
| `medicines` | Medicine inventory per shop |
| `bills` | Sales transactions per shop |
| `bill_items` | Individual line items in each bill |
| `suppliers` | Supplier directory per shop |
| `subscriptions` | Active subscription plans per shop |
| `plans` | Available subscription plan definitions |

---

## 🔑 Environment Variables

For **production** deployment, override these values using environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_URL` | MySQL JDBC connection URL | `jdbc:mysql://localhost:3306/medicloud_db` |
| `DB_USERNAME` | Database username | `root` |
| `DB_PASSWORD` | Database password | `root` |
| `JWT_SECRET` | HMAC-SHA256 signing key (≥256 bits) | hardcoded dev key |
| `JWT_EXPIRATION` | Token expiry in milliseconds | `86400000` (24h) |
| `SERVER_PORT` | Application port | `8080` |

### Example: Running with Environment Variables

```bash
# Linux / macOS
export DB_URL="jdbc:mysql://prod-db:3306/medicloud_db?useSSL=true"
export DB_USERNAME="medicloud_user"
export DB_PASSWORD="s3cur3P@ssw0rd"
export JWT_SECRET="your-production-secret-key-min-256-bits"
./mvnw spring-boot:run

# Windows PowerShell
$env:DB_URL = "jdbc:mysql://prod-db:3306/medicloud_db?useSSL=true"
$env:DB_USERNAME = "medicloud_user"
$env:DB_PASSWORD = "s3cur3P@ssw0rd"
$env:JWT_SECRET = "your-production-secret-key-min-256-bits"
.\mvnw.cmd spring-boot:run
```

---

## 🔄 API Authentication Flow

```
┌──────────┐       ┌──────────────┐       ┌────────────┐
│  Client  │──────▶│ POST /auth/  │──────▶│  Validate  │
│          │       │    login     │       │ Credentials│
└──────────┘       └──────────────┘       └─────┬──────┘
                                                │
                          ┌─────────────────────┤
                          │                     │
                     ❌ 401                  ✅ 200
                   Unauthorized          ┌──────┴──────┐
                                         │ Generate JWT│
                                         │ {email,     │
                                         │  shopId,    │
                                         │  role}      │
                                         └──────┬──────┘
                                                │
                                         ┌──────┴──────┐
                                         │ Return Token│
                                         └──────┬──────┘
                                                │
┌──────────┐       ┌──────────────┐       ┌─────┴──────┐
│  Client  │──────▶│ GET /medicines│──────▶│ JwtAuth   │
│ (token)  │       │ Authorization:│      │  Filter    │
│          │       │ Bearer <token>│      └─────┬──────┘
└──────────┘       └──────────────┘             │
                                          ┌─────┴──────┐
                                          │ Extract:   │
                                          │ • email    │
                                          │ • shopId   │
                                          │ • role     │
                                          └─────┬──────┘
                                                │
                                         ┌──────┴──────┐
                                         │ Set Security│
                                         │  Context    │
                                         └──────┬──────┘
                                                │
                                         ┌──────┴──────┐
                                         │ Controller  │
                                         │ processes   │
                                         │ request for │
                                         │ shopId only │
                                         └─────────────┘
```

### Flow Summary

1. **Client** sends `POST /auth/login` with `{ email, password }`
2. **Server** validates credentials via `AuthenticationManager` + BCrypt
3. **On success**, server generates JWT containing `email`, `shopId`, and `role` claims
4. **Client** stores the token and sends it in the `Authorization: Bearer <token>` header for all subsequent requests
5. **JwtAuthFilter** intercepts each request, validates the token, extracts claims, and sets the security context
6. **TenantUtil** reads `shopId` from the request context — all database queries are scoped to this shop
7. **Spring Security** checks RBAC rules before the controller is invoked

---

## 📡 Example API Requests

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "owner@shop1.com",
    "password": "securePassword123"
  }'
```

**Response (200 OK):**

```json
{
  "success": true,
  "message": "Login successful",
  "data": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvd25lckBzaG9wMS5jb20iLCJzaG9wSWQiOjEsInJvbGUiOiJPV05FUiIsImlhdCI6MTcwOTUyOTYwMCwiZXhwIjoxNzA5NjE2MDAwfQ...",
  "timestamp": 1709529600000
}
```

### Register New User

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Rajesh Kumar",
    "email": "rajesh@shop1.com",
    "password": "password123",
    "shopId": 1
  }'
```

### Create a Medicine

```bash
curl -X POST http://localhost:8080/medicines \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "name": "Paracetamol 500mg",
    "company": "Cipla",
    "batchNo": "BATCH-2026-001",
    "quantity": 500,
    "purchasePrice": 2.50,
    "sellingPrice": 5.00,
    "expiryDate": "2027-12-31"
  }'
```

**Response (201 Created):**

```json
{
  "success": true,
  "message": "Medicine added successfully",
  "data": {
    "id": 1,
    "name": "Paracetamol 500mg",
    "company": "Cipla",
    "batchNo": "BATCH-2026-001",
    "quantity": 500,
    "sellingPrice": 5.00
  },
  "timestamp": 1709529600000
}
```

### Create a Bill

```bash
curl -X POST http://localhost:8080/billing/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "items": [
      { "medicineId": 1, "quantity": 10 },
      { "medicineId": 2, "quantity": 5 }
    ]
  }'
```

### Get Dashboard Metrics

```bash
curl -X GET http://localhost:8080/dashboard \
  -H "Authorization: Bearer <your-jwt-token>"
```

**Response (200 OK):**

```json
{
  "success": true,
  "message": "Dashboard loaded",
  "data": {
    "todaySale": 12500.00,
    "totalSale": 485000.00,
    "totalProfit": 125000.00,
    "lowStockCount": 7
  },
  "timestamp": 1709529600000
}
```

### Super Admin — List All Shops

```bash
curl -X GET http://localhost:8080/superadmin/shops \
  -H "Authorization: Bearer <super-admin-jwt-token>"
```

---

## ⚠️ Error Handling

The application uses a **centralized `GlobalExceptionHandler`** (`@RestControllerAdvice`) that intercepts all exceptions and returns consistent JSON responses.

### Error Response Format

```json
{
  "success": false,
  "message": "Resource not found",
  "data": null,
  "timestamp": 1709529600000
}
```

### Exception Mapping

| Exception | HTTP Status | Message |
|-----------|:-----------:|---------|
| `ResourceNotFoundException` | 404 | Dynamic message from exception |
| `BusinessException` | 400 | Dynamic message from exception |
| `MethodArgumentNotValidException` | 400 | Field-level validation errors (e.g., `"email: must not be blank, password: size must be between 6 and 50"`) |
| `BadCredentialsException` | 401 | `"Invalid email or password"` |
| `AccessDeniedException` | 403 | `"Access denied"` |
| `Exception` (catch-all) | 500 | `"Something went wrong"` |

> All 500 errors are logged with full stack traces server-side while returning a safe generic message to the client.

---

## ⚡ Performance Considerations

| Strategy | Implementation |
|----------|---------------|
| **Connection Pooling** | HikariCP with max 10 connections, min 2 idle |
| **Batch Operations** | Hibernate batch size 25 for inserts/updates (`hibernate.jdbc.batch_size=25`) |
| **Ordered Writes** | `hibernate.order_inserts=true`, `hibernate.order_updates=true` — reduces DB round-trips |
| **Lazy Loading** | `open-in-view=false` — prevents N+1 queries via accidental lazy loading in views |
| **Stateless Auth** | No server-side session storage — JWT is self-contained |
| **Tenant Resolution** | `TenantUtil` reads shopId from JWT context — zero extra DB queries |
| **BigDecimal Arithmetic** | All monetary calculations use `BigDecimal` — no floating-point precision loss |
| **Database Indexes** | Indexes on `shopId`, `email`, `expiryDate`, `createdAt`, `billId` columns |
| **Transactional Integrity** | All service methods with writes annotated with `@Transactional` |
| **Read-Only Transactions** | Dashboard queries use `@Transactional(readOnly = true)` for Hibernate optimizations |
| **SQL Logging** | Disabled in production (`show-sql=false`) |

---

## 🔮 Future Improvements

| Area | Improvement |
|------|-------------|
| **Caching** | Redis caching for frequently accessed data (medicines, dashboard metrics) |
| **Pagination** | Paginated responses for medicine and supplier listing endpoints |
| **Search** | Full-text search for medicines by name, batch number, company |
| **Audit Trail** | Entity change history with Hibernate Envers or custom audit tables |
| **Rate Limiting** | API rate limiting using Bucket4j or Spring Cloud Gateway |
| **File Uploads** | Medicine image and invoice attachment support (S3/MinIO) |
| **Notifications** | Email/SMS/WhatsApp notifications for low stock, expiry alerts, billing receipts |
| **Reports** | PDF/Excel export for billing reports, stock summaries, profit analysis |
| **Docker** | Dockerfile + docker-compose for containerized MySQL + Application deployment |
| **CI/CD** | GitHub Actions pipeline for build, test, and deploy automation |
| **Testing** | Comprehensive unit and integration test suite with Testcontainers |
| **Multi-DB** | Per-tenant database isolation for enterprise customers |
| **API Versioning** | URL-based or header-based API versioning (`/api/v1/...`) |
| **Refresh Tokens** | Short-lived access tokens + long-lived refresh tokens for better security |
| **Payment Integration** | Razorpay checkout flow for subscription payments |
| **Monitoring** | Prometheus + Grafana metrics and distributed tracing |

---

## 👨‍💻 Author

**Santu** — Full-Stack Developer

- **GitHub:** [@Santu-developer](https://github.com/Santu-developer-github-repos)
- **Project:** [medicloud-backend](https://github.com/Santu-developer-github-repos/medicloud-backend)

---

<p align="center">
  <b>Jay Shree Ram 🙏</b>
</p>

---

> Built with ❤️ using Spring Boot · Designed for scale · Secured by design
