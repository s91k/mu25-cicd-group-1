<h1 align="center">📚 Library API</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-Backend-orange" />
  <img src="https://img.shields.io/badge/Spring%20Boot-Framework-brightgreen" />
  <img src="https://img.shields.io/badge/JUnit-Testing-red" />
  <img src="https://img.shields.io/badge/Mockito-Mocking-green" />
  <img src="https://img.shields.io/badge/GitHub%20Actions-CI/CD-blue" />
  <img src="https://img.shields.io/badge/Docker-Container-2496ED" />
  <img src="https://img.shields.io/badge/Render-Deploy-black" />
</p>

A simple Spring Boot REST API developed as a group project with focus on Git workflow, automated testing and CI/CD using GitHub Actions.

<h4 align="center">Live-API Render [Dev]: https://library-api-dev-3yd2.onrender.com/books</h4>
<h4 align="center">Live-API Render [Main]: https://library-api-8jzi.onrender.com/books</h4>

## Team Agreement 🤝

To keep the project consistent and avoid conflicts, we follow a shared development workflow.

### Branch Strategy

The repository uses:

```text
main
 ↑
dev
 ↑
feature/*
```

- `main` contains production-ready code
- `dev` contains the latest integrated development version
- All new development is done on `feature/*` branches
- Feature branches must always be created from an updated `dev`
- Direct pushes to `main` and `dev` are not allowed
- `main` and `dev` are protected branches

Feature branches should have clear and descriptive names, for example:

```text
feature/books
feature/authors
feature/users
feature/book-items
feature/loans
```

Before starting a new feature:

```bash
git switch dev
git pull origin dev
git switch -c feature/<name>
```

---

## Pull Requests

All code is integrated through Pull Requests.

A PR should:

- have a clear and descriptive title
- describe what has been added or changed
- mention relevant tests
- target `dev` during development
- be reviewed by at least one other team member
- have passing GitHub Actions checks before merge

Example PR description:

```text
## Summary
- Added Book service and repository
- Added GET /books and POST /books
- Added validation for authorId
- Added unit tests

## Testing
- All tests pass locally
- GET /books tested
- POST /books returns 201 for valid author
- Invalid author returns 404
```

Review comments should be resolved before merging.

---

## Project Structure

We use the same package structure across features:

```text
model
  ↓
repository
  ↓
service
  ↓
controller
```

Shared packages:

```text
controller/
dto/
exception/
model/
repository/
service/
```

### Responsibilities

- `model` – application/domain models
- `repository` – data storage and retrieval
- `service` – business logic and validation
- `controller` – HTTP requests and responses
- `dto` – request/response objects where needed
- `exception` – custom exceptions and error handling

The group should agree on structure and naming before implementing new patterns.

---

## Testing Strategy

Tests should focus on **behavior and business logic**, not every individual line of code.

### We test things such as:

- successful operations
- resources that do not exist
- invalid input
- validation between resources
- business rules
- correct HTTP status codes
- that invalid data is not saved

### Tests cover:
- Controller
- Repository
- Service

Example:

```text
BookService

✓ returns a book when it exists
✓ throws when a book does not exist
✓ creates a book when the author exists
✓ rejects a book when the author does not exist
```

Controller tests can verify:

```text
GET /books  → 200 OK
POST /books → 201 Created
Missing resource → 404 Not Found
```

We do not need separate tests for trivial code such as simple getters, setters or DTO accessors.

The goal is meaningful test coverage, not testing every single line.

---

## CI – Continuous Integration

GitHub Actions runs automated tests on Pull Requests.

```text
feature/*
    ↓
Pull Request
    ↓
GitHub Actions
    ↓
Build + Tests
    ↓
✅ Green
    ↓
Code Review
    ↓
Merge
```

A PR should not be merged while GitHub Actions is failing.

This helps prevent broken code from being introduced into `dev` or `main`.

---

## CD – Continuous Deployment

Deployment happens automatically after code has been successfully integrated.

### Development

```text
PR → dev
   ↓
Tests pass
   ↓
Review
   ↓
Merge
   ↓
Push to dev
   ↓
GitHub Actions
   ↓
Deploy DEV
```

### Production

```text
dev
 ↓
PR → main
 ↓
Tests pass
 ↓
Review
 ↓
Merge
 ↓
Push to main
 ↓
GitHub Actions
 ↓
Deploy PROD
```

This means testing happens **before merge**, while deployment happens **after approved code is pushed to the target branch**.

---

## Before Merging

Before a PR is merged:

- [ ] Feature branch was created from `dev`
- [ ] Code follows the agreed project structure
- [ ] Relevant behavior/business logic is tested
- [ ] All tests pass locally
- [ ] GitHub Actions is green
- [ ] PR contains a description of the changes
- [ ] At least one team member has reviewed the PR
- [ ] Review comments are resolved
