# CalendarAI

CalendarAI is a simple single-user mobile app that lets you upload a PDF or image, extract likely calendar events with AI/OCR, review them, and prepare them for Google Calendar integration.

## Features
- Upload PDF or image files
- Extract events using OCR + AI heuristics
- Review extracted events in the app
- View uploaded document history
- Spring Boot backend with PostgreSQL persistence

## Backend setup
1. Start PostgreSQL with Docker:
   ```bash
   docker compose up -d db
   ```
2. Run the backend:
   ```powershell
   cd backend
   .\run-backend.cmd
   ```
3. Set environment variables if needed:
   ```bash
   export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/calendarai
   export SPRING_DATASOURCE_USERNAME=calendarai
   export SPRING_DATASOURCE_PASSWORD=calendarai
   export OPENAI_API_KEY=your_key_here
   ```

## Flutter setup
1. Install Flutter SDK and ensure it is on your PATH.
2. Install dependencies:
   ```bash
   cd mobile
   flutter pub get
   ```
3. Run the app:
   ```bash
   flutter run
   ```
4. Build an APK:
   ```bash
   flutter build apk
   ```

## Render deployment
1. Push this repository to GitHub.
2. Create a new Web Service on Render.
3. Connect the repository.
4. Use the following build and start commands:
   ```bash
   cd backend && mvn -DskipTests package
   java -jar target/backend-0.0.1-SNAPSHOT.jar
   ```
5. Set the environment variables:
   - PORT = 10000
   - OPENAI_API_KEY = your_key_here
6. Add a PostgreSQL database in Render and set:
   - SPRING_DATASOURCE_URL
   - SPRING_DATASOURCE_USERNAME
   - SPRING_DATASOURCE_PASSWORD

## Google Calendar setup
- Create a Google Cloud project.
- Enable the Google Calendar API.
- Configure OAuth credentials for a mobile app.
- Pass the access token in the Authorization header for calendar calls.

## OpenAI API setup
- Create an OpenAI API key.
- Set it in the backend environment as OPENAI_API_KEY.
- If no key is provided, the backend falls back to a simple heuristic extractor.
