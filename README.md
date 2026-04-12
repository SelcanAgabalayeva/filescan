# 🛡️ FileScan — Virus Scanning Service (Spring Boot + ClamAV)

FileScan is a backend service built with Spring Boot that scans uploaded files for viruses and malware using ClamAV via TCP socket integration.

It supports asynchronous scanning, blocks infected files, and logs scan results for auditing.

---

## 🚀 Features

* 📤 Upload files via REST API (multipart/form-data)
* 🦠 Virus scanning with ClamAV
* ⚡ Asynchronous processing using @Async
* ❌ Blocks infected files
* 📝 Stores scan results in database
* ⚠️ Handles errors (virus detection, connection failure)

---

## ⚙️ Tech Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 / PostgreSQL
* ClamAV
* Maven

---

## 📡 API

### Upload File

POST /api/files/upload

### Request

* Content-Type: multipart/form-data
* Key: `file`

---

### ✅ Success Response

```json
{
  "id": 1,
  "fileName": "image.png",
  "infected": false,
  "message": "stream: OK",
  "scannedAt": "2026-04-11T19:00:00"
}
```

---

### ❌ Virus Detected

```
Virus detected: stream: Eicar-Test-Signature FOUND
```

---

## 🧪 Test Virus (EICAR)

Create a file and paste:

```
X5O!P%@AP[4\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*
```

Upload it to verify detection.

---

## 🐳 Run ClamAV (Docker)

```bash
docker run -d -p 3310:3310 clamav/clamav
```

---

## ▶️ Run Application

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🧱 Architecture

Controller → Service → ClamAV → Database

---

## ⚠️ Notes

* Ensure ClamAV is running on port 3310
* Configure file size limits in application.properties
* Async processing improves performance

---

## ⭐ Future Improvements

* Quarantine system
* Email notifications
* Cloud storage integration
* Frontend UI

---

## 👩‍💻 Author

Selcan Ağabalayeva
