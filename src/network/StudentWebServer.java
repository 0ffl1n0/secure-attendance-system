package network;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import core.AttendanceManager;
import core.AuditLogger;
import models.Session;
import models.Student;
import security.AttendanceSecurityManager;
import security.SecurityViolationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

public class StudentWebServer {
    private HttpServer server;
    private final AttendanceSecurityManager securityManager;
    private final int port;

    public StudentWebServer(int port) {
        this.port = port;
        this.securityManager = new AttendanceSecurityManager();
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/attendance", new AttendancePageHandler());
        server.createContext("/submit", new SubmitAttendanceHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Web Server started on port " + port);
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("Web Server stopped.");
        }
    }

    private class AttendancePageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String token = "";
                if (query != null && query.startsWith("token=")) {
                    token = query.split("=")[1];
                }

                String html = "<!DOCTYPE html><html><head><title>Mark Attendance</title>" +
                        "<meta name='viewport' content='width=device-width, initial-scale=1'>" +
                        "<style>" +
                        "body { font-family: 'Inter', sans-serif; background-color: #0f172a; color: white; display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100vh; margin: 0; }" +
                        ".container { background: rgba(255, 255, 255, 0.1); padding: 2rem; border-radius: 12px; backdrop-filter: blur(10px); box-shadow: 0 4px 6px rgba(0,0,0,0.1); text-align: center; width: 90%; max-width: 400px; }" +
                        "input { width: 100%; padding: 10px; margin: 10px 0; border-radius: 6px; border: none; outline: none; box-sizing: border-box; }" +
                        "button { width: 100%; padding: 12px; background: #3b82f6; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; transition: background 0.3s; }" +
                        "button:hover { background: #2563eb; }" +
                        "#status { margin-top: 15px; color: #fbbf24; font-size: 0.9em; }" +
                        "</style>" +
                        "</head><body>" +
                        "<div class='container'>" +
                        "<h2>Scan Successful</h2>" +
                        "<p>Enter your Student ID to mark attendance.</p>" +
                        "<input type='text' id='studentId' placeholder='e.g., 001' required />" +
                        "<button onclick='submitAttendance()'>Mark Attendance</button>" +
                        "<div id='status'></div>" +
                        "</div>" +
                        "<script>" +
                        "function submitAttendance() {" +
                        "  const studentId = document.getElementById('studentId').value;" +
                        "  if(!studentId) { alert('Please enter Student ID'); return; }" +
                        "  const token = '" + token + "';" +
                        "  const tz = Intl.DateTimeFormat().resolvedOptions().timeZone;" +
                        "  const fp = navigator.userAgent + '|' + screen.width + 'x' + screen.height;" +
                        "  document.getElementById('status').innerText = 'Getting location...';" +
                        "  if(navigator.geolocation) {" +
                        "    navigator.geolocation.getCurrentPosition(" +
                        "      (pos) => sendData(studentId, token, tz, fp, pos.coords.latitude, pos.coords.longitude)," +
                        "      (err) => { document.getElementById('status').innerText = 'Location access denied or unavailable.'; }" +
                        "    );" +
                        "  } else {" +
                        "    document.getElementById('status').innerText = 'Geolocation not supported.';" +
                        "  }" +
                        "}" +
                        "function sendData(id, token, tz, fp, lat, lng) {" +
                        "  document.getElementById('status').innerText = 'Submitting...';" +
                        "  const payload = 'id='+encodeURIComponent(id)+'&token='+encodeURIComponent(token)+'&tz='+encodeURIComponent(tz)+'&fp='+encodeURIComponent(fp)+'&lat='+lat+'&lng='+lng;" +
                        "  fetch('/submit', {" +
                        "    method: 'POST'," +
                        "    headers: {'Content-Type': 'application/x-www-form-urlencoded'}," +
                        "    body: payload" +
                        "  }).then(res => res.text()).then(text => {" +
                        "    document.getElementById('status').innerText = text;" +
                        "  }).catch(err => {" +
                        "    document.getElementById('status').innerText = 'Network error.';" +
                        "  });" +
                        "}" +
                        "</script>" +
                        "</body></html>";

                sendResponse(exchange, 200, html);
            } else {
                sendResponse(exchange, 405, "Method Not Allowed");
            }
        }
    }

    private class SubmitAttendanceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    InputStream is = exchange.getRequestBody();
                    java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
                    int nRead;
                    byte[] data = new byte[1024];
                    while ((nRead = is.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    String body = new String(buffer.toByteArray(), StandardCharsets.UTF_8);
                    
                    Map<String, String> params = parseForm(body);

                    String studentId = params.get("id");
                    String token = params.get("token");
                    String tz = params.get("tz");
                    String fp = params.get("fp");
                    double lat = 0;
                    double lng = 0;
                    
                    try {
                        lat = Double.parseDouble(params.get("lat"));
                        lng = Double.parseDouble(params.get("lng"));
                    } catch (Exception e) {
                        sendResponse(exchange, 400, "Invalid coordinates format.");
                        return;
                    }

                    String ipAddress = exchange.getRemoteAddress().getAddress().getHostAddress();

                    AttendanceManager am = AttendanceManager.getInstance();
                    Session activeSession = am.getActiveSession();

                    if (activeSession == null) {
                        AuditLogger.log(studentId, ipAddress, "ATTEMPT", "No active session");
                        sendResponse(exchange, 400, "No active session.");
                        return;
                    }

                try {
                    // Security Validations
                    securityManager.validateToken(token, am.qrTokenGenerator.getCurrentToken());
                    securityManager.validateTimezone(tz);
                    securityManager.validateLocation(lat, lng, activeSession.getLatitude(), activeSession.getLongitude());
                    securityManager.validateDevice(activeSession.getSessionId(), studentId, ipAddress, fp);

                    // Validate Student Data
                    Student student = am.getStudent(studentId);
                    if (student == null) {
                        throw new SecurityViolationException("Student not found in database.");
                    }
                    if (!activeSession.isValidStudentForSession(student)) {
                        throw new SecurityViolationException("Student does not belong to this " + activeSession.getSessionType() + ".");
                    }

                    // Success
                    activeSession.addAttendance(studentId);
                    AuditLogger.log(studentId, ipAddress, "SUCCESS", "Attendance marked");
                    sendResponse(exchange, 200, "✅ Attendance marked successfully!");

                } catch (SecurityViolationException e) {
                    AuditLogger.log(studentId, ipAddress, "BLOCKED", e.getMessage());
                    sendResponse(exchange, 403, "❌ Blocked: " + e.getMessage());
                } catch (Exception e) {
                    AuditLogger.log(studentId, ipAddress, "ERROR", e.getMessage());
                    sendResponse(exchange, 500, "Internal Server Error.");
                }
                } catch (Throwable t) {
                    t.printStackTrace();
                    sendResponse(exchange, 500, "Server Error: " + t.getMessage());
                }
            } else {
                sendResponse(exchange, 405, "Method Not Allowed");
            }
        }
    }

    private Map<String, String> parseForm(String formData) {
        Map<String, String> map = new java.util.HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                map.put(kv[0], java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            } else if (kv.length == 1) {
                map.put(kv[0], "");
            }
        }
        return map;
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
