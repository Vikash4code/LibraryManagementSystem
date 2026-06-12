# 📚 Library Management System

A full-stack Library Management System built with **Spring Boot 3 (Java 21)** and **React**, featuring JWT authentication, an admin/member role system, book loans with fine calculation, reservations, wishlists, dashboards with charts, email reminders, and a free **E-Library** of public-domain books.

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 3.4, Spring Security (JWT), Spring Data JPA / Hibernate, Bean Validation |
| Database | MySQL 8 |
| Frontend | React 19 (Vite), Tailwind CSS, React Router, Axios, Recharts |
| Testing | JUnit 5, Mockito, Spring MockMvc |
| Docs | Swagger / OpenAPI (springdoc) |
| DevOps | Docker, Docker Compose, GitHub Actions CI |

## Features

- **Authentication & authorization** — register/login with BCrypt-hashed passwords, stateless JWT, role-based access (`ADMIN` / `USER`)
- **Book management** — admin CRUD with validation, duplicate-ISBN protection, cover images
- **Search, filter & pagination** — by title/author and category, server-side `Pageable`
- **Loans** — issue (7-day period) and return books; copies tracked automatically
- **Fine calculation** — ₹10 per day charged automatically on late returns
- **Reservations** — members reserve out-of-stock books; fulfilled automatically on issue
- **Wishlist** — save books to borrow later
- **Dashboards** — admin: stat cards + category pie + monthly loan bar charts; member: personal stats
- **E-Library** — search and read/download thousands of free books via the Open Library / Internet Archive API
- **Email reminders** — daily scheduled job emails members whose books are due within 2 days
- **Global exception handling** — consistent JSON error body with field-level validation messages
- **API documentation** — interactive Swagger UI with JWT authorize support

## Getting Started

### Option A — Run everything with Docker (recommended, zero config)

The only requirement is **Docker Desktop**. One command builds and starts MySQL, the backend and the frontend together:

```bash
docker compose up --build
```

Then open:

| What | URL |
|---|---|
| **The app** | http://localhost:8088 |
| Swagger API docs | http://localhost:8086/swagger-ui.html |

Default admin login: `admin@library.com` / `admin123`

To stop: `docker compose down` (add `-v` to also wipe the database volume).

> Ports 8088/8086 are used (instead of 80/8080) so the stack doesn't clash with other things running on your machine. MySQL runs only inside Docker — it is not exposed to your host.

### Option B — Run each part manually (for development)

Prerequisites: Java 21, Node.js 20+, Docker Desktop (just for MySQL).

```bash
docker compose up -d mysql        # 1. database
cd backend && mvn spring-boot:run # 2. backend  -> http://localhost:8080
cd frontend && npm install && npm run dev   # 3. frontend -> http://localhost:5173
```

- Swagger UI: http://localhost:8080/swagger-ui.html
- Default admin: `admin@library.com` / `admin123`

### Run tests

```bash
cd backend
mvn test
```

## Configuration

All settings have sensible local defaults and can be overridden with environment variables:

| Variable | Purpose |
|---|---|
| `SPRING_DATASOURCE_URL` / `USERNAME` / `PASSWORD` | MySQL connection |
| `JWT_SECRET` | HMAC signing key (256-bit minimum) |
| `CORS_ALLOWED_ORIGINS` | Comma-separated frontend origins |
| `MAIL_USERNAME` / `MAIL_PASSWORD` | Gmail SMTP (app password) for due-date reminder emails |
| `VITE_API_URL` | Backend URL used by the frontend build |

Business rules (loan period, fine per day) live under `lms.*` in [`application.yml`](backend/src/main/resources/application.yml).

## API Overview

```
POST   /api/auth/register | /api/auth/login          authentication
GET    /api/books?search=&category=&page=            public catalog (paginated)
POST   /api/books                                    add book (ADMIN)
POST   /api/loans                                    issue book (ADMIN)
PUT    /api/loans/{id}/return                        return book + fine (ADMIN)
GET    /api/loans/my                                 borrowing history
POST   /api/reservations                             reserve an out-of-stock book
GET    /api/wishlist                                 my wishlist
GET    /api/dashboard                                role-aware statistics
GET    /api/elibrary/search?q=&topic=&page=          free books (Open Library)
```

Full interactive documentation in Swagger UI.

## Free Deployment (optional)

The app can be deployed entirely on free tiers:

- **Backend** → [Render](https://render.com) free web service (uses `backend/Dockerfile`; sleeps after 15 min idle)
- **MySQL** → [Aiven](https://aiven.io) free tier (add `?sslMode=REQUIRED` to the JDBC URL, keep Hikari pool ≤ 5)
- **Frontend** → [Vercel](https://vercel.com) (set `VITE_API_URL` to the Render URL; add a SPA rewrite to `index.html`)

Set `CORS_ALLOWED_ORIGINS` on Render to the Vercel URL.

## Design Notes

- **DTOs are Java records**, entities use Lombok — records can't be JPA entities (they're immutable with no no-arg constructor), which is exactly why both tools are used where they fit.
- **No refresh tokens** — a single 8-hour access token keeps the auth flow easy to reason about; refresh-token rotation is a noted production improvement.
- **Fines are computed at return time** rather than by a background job, so the system has one source of truth and no scheduler/database race conditions.
- **The E-Library proxies Open Library through the backend** to avoid browser CORS issues and keep API handling in one place. Only books with `ebook_access:public` are requested, so every result is genuinely free to read.
