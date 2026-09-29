# MedCore HMS

MedCore HMS is a Hospital Management System built using Java and Spring Boot.

The project provides REST APIs to manage hospital information such as hospitals, patients, doctors, appointments, medical records, prescriptions, and billing.

The project is being developed step by step with a focus on learning real-world backend development concepts including layered architecture, dependency injection, DTOs, Spring Data JPA, PostgreSQL, Spring Security, JWT authentication, validation, exception handling, and testing.

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Spring Security
- JWT
- Maven
- REST API

## Local JWT Configuration

Set a randomly generated Base64 secret of at least 32 bytes in PowerShell before starting the application. Keep it in the environment, not in source control:

```powershell
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
$env:JWT_SECRET = [Convert]::ToBase64String($bytes)
$rng.Dispose()
& .\mvnw.cmd spring-boot:run
```

The existing PostgreSQL settings also require `DB_USERNAME` and `DB_PASSWORD` in the same PowerShell session. `JWT_SECRET` is required at startup and is never logged.