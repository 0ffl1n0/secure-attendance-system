# Technical Report Outline: Object-Oriented Design of the Secure Attendance System

## 1. Introduction (1 Page)
- Project Overview: Goal of building a secure, remote-accessible attendance system.
- Problem Statement: Preventing proxy attendance, location spoofing, and VPN usage.
- High-Level Architecture: Client-Server model with a Swing Teacher Dashboard and web-based Student Client.

## 2. Core OOP Principles Applied (2 Pages)

### 2.1 Encapsulation
- Restricting direct access to class attributes (e.g., student details, session coordinates, attendance lists).
- Usage of getters/setters and private modifiers to protect internal state.

### 2.2 Inheritance
- Base `User` class abstracting common attributes (`id`, `name`, `email`).
- Specialized classes: `Student` and `Teacher` inheriting from `User`.
- `Session` base class inherited by `TDSession` and `CourseSession` to differentiate behavioral logic.

### 2.3 Abstraction & Interfaces
- The `SecurityValidator` interface defining a contract for security rules without exposing the underlying implementation.
- Abstract methods like `getSessionType()` in the `Session` class, enforcing implementation in subclasses.

### 2.4 Polymorphism
- Method overriding: `isValidStudentForSession(Student)` overridden in `TDSession` to check groups, and in `CourseSession` to check sections.
- Treating all session types uniformly via the `Session` reference type in the `AttendanceManager`.

## 3. Advanced Security & Anti-Cheating Implementation (3 Pages)

### 3.1 Dynamic QR Engine (QRTokenGenerator)
- Generation of short-lived (30-second) base64-encoded random tokens.
- Validation mechanism to prevent reusing expired QR codes.

### 3.2 Geofencing (Geolocation Validation)
- Utilizing browser Geolocation API (`navigator.geolocation`).
- Haversine formula implemented in `AttendanceSecurityManager` to calculate the distance between the classroom and the student, enforcing a 50m radius limit.

### 3.3 Anti-VPN & Timezone Checks
- Extracting browser timezone using `Intl.DateTimeFormat().resolvedOptions().timeZone`.
- Backend validation to detect hidden or mismatched timezones indicative of VPN usage.

### 3.4 Device/Machine ID Lock (Fingerprinting)
- Gathering IP address and generating a basic browser fingerprint (User Agent + Screen Resolution).
- Registering the `Student ID -> Device Fingerprint` map per session.
- Blocking multiple IDs from scanning on the same device and preventing a single ID from using multiple devices.

## 4. System Architecture & Components (2 Pages)

### 4.1 Data Persistence
- In-memory HashMaps simulating the database.
- File I/O (`AuditLogger`) appending security events to `audit_log.txt`.

### 4.2 Network Module
- Embedded Java `HttpServer` serving the attendance web page.
- Handling asynchronous POST requests containing location and fingerprint data.

### 4.3 Teacher GUI (Swing)
- Session control panel, real-time log updates via `javax.swing.Timer`, and dynamic QR rendering using `api.qrserver.com`.

## 5. Error Handling & Custom Exceptions (1 Page)
- Design and usage of `SecurityViolationException`.
- Graceful degradation: providing meaningful feedback to the student UI without crashing the server.

## 6. Conclusion & Future Enhancements (1 Page)
- Summary of the robustness of the system.
- Potential improvements: Database integration (SQL), advanced browser fingerprinting (Canvas/WebGL), HTTPS configuration for production.
