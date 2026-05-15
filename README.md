# Secure Attendance System

A robust, secure attendance management system built with Java.

## Features
- **Modern UI**: Dark-themed dashboard for teachers and administrators.
- **QR Code Integration**: Generates secure tokens for student check-ins.
- **Security Protocols**: Geofencing, machine ID locking, and timezone-based validation to prevent fraud.
- **Audit Logging**: Comprehensive logs of all attendance activities.
- **Real-time Monitoring**: Monitor student attendance as it happens.

## Project Structure
- `src/core`: Core logic for attendance management and token generation.
- `src/gui`: Swing-based user interface components.
- `src/models`: Data models for Students, Teachers, and Sessions.
- `src/network`: Web server for student interactions.
- `src/security`: Security validation and enforcement logic.

## How to Run
1. Ensure you have Java 11+ installed.
2. Compile the project:
   ```bash
   javac -d out src/**/*.java
   ```
3. Run the application:
   ```bash
   java -cp out Main
   ```

## License
MIT License
