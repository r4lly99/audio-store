# AudioStore

AudioStore is a backend service that allows users to upload, convert, store, and retrieve audio files associated with specific users and phrases. 
---

## Features

- Accept audio file uploads and convert them to a specified format (e.g., m4a to wav).
- Store audio file metadata in a relational database (H2).
- Retrieve stored audio files, converting them back to the requested format.
- Expose RESTful APIs for uploading and retrieving audio files.

---

## Tech Stack

- **Java 17** (Spring Boot Framework)
- **H2 Database** (In-memory relational database)
- **Apache Commons IO** (File handling utilities)
- **Docker** (Containerization)

---

## API Endpoints

### 1. Upload Audio File
**Endpoint:** `POST /audio/user/{userId}/phrase/{phraseId}`  
**Description:** Uploads an audio file and stores it on the server after converting it to WAV format.  
**Request:**
- `MultipartFile` (key: `audio_file`)

**Example Curl Command:**
```bash
curl --request POST 'http://localhost:8080/audio/user/1/phrase/1' \
     --form 'audio_file=@"./test_audio_file_1.m4a"'
```

## Project Structure
```
src/main/java/com/example/mini
    ├── controller       # REST controllers
    ├── service          # Business logic
    ├── repository       # JPA repositories
    ├── entity           # Database entity classes
    └── AudioStoreApplication.java  # Main application class
```

## How to Run

**Prerequisites**
1. Docker installed
2. Java 17 installed (optional for local run)
**Build the Docker Image**
```bash
docker build -t audio-store .
```
**Run the Docker Container**
```bash
docker run -p 8080:8080 audio-store
```