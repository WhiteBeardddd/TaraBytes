package com.example.midtermsexam_beauty.utilities;

import android.util.Log;
import com.example.midtermsexam_beauty.BuildConfig;
import com.example.midtermsexam_beauty.models.Profile;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SupabaseAuthService {
    private static final String TAG = "SupabaseAuth";

    public static class AuthResult {
        public final boolean success;
        public final String message;
        public final String accessToken;
        public final String userId;

        public AuthResult(boolean success, String message, String accessToken, String userId) {
            this.success = success;
            this.message = message;
            this.accessToken = accessToken;
            this.userId = userId;
        }
    }

    public boolean isConfigured() {
        return !BuildConfig.SUPABASE_URL.isEmpty() && !BuildConfig.SUPABASE_ANON_KEY.isEmpty();
    }

    private String getBaseUrl() {
        String url = BuildConfig.SUPABASE_URL;
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    public AuthResult signUp(String email, String password, String username) {
        try {
            JSONObject payload = new JSONObject()
                    .put("email", email)
                    .put("password", password)
                    .put("data", new JSONObject().put("username", username));
            HttpResponse res = post("/auth/v1/signup", payload.toString(), null);
            return res.statusCode >= 200 && res.statusCode < 300 
                ? new AuthResult(true, "Check your email.", null, null)
                : new AuthResult(false, extractErrorMessage(res.body), null, null);
        } catch (Exception e) { return new AuthResult(false, e.getMessage(), null, null); }
    }

    public AuthResult signIn(String email, String password) {
        try {
            JSONObject payload = new JSONObject().put("email", email).put("password", password);
            HttpResponse res = post("/auth/v1/token?grant_type=password", payload.toString(), null);
            if (res.statusCode >= 200 && res.statusCode < 300) {
                JSONObject json = new JSONObject(res.body);
                return new AuthResult(true, "Login success.", json.optString("access_token"), 
                    json.has("user") ? json.getJSONObject("user").getString("id") : null);
            }
            return new AuthResult(false, extractErrorMessage(res.body), null, null);
        } catch (Exception e) { return new AuthResult(false, e.getMessage(), null, null); }
    }

    public Profile getProfile(String token, String authId) {
        if (token == null || authId == null) return null;
        try {
            URL url = new URL(getBaseUrl() + "/rest/v1/profile?auth_id=eq." + authId + "&select=*");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("apikey", BuildConfig.SUPABASE_ANON_KEY);
            conn.setRequestProperty("Authorization", "Bearer " + token);
            int code = conn.getResponseCode();
            String body = readStream(code < 300 ? conn.getInputStream() : conn.getErrorStream());
            conn.disconnect();
            if (code >= 200 && code < 300) {
                JSONArray arr = new JSONArray(body);
                if (arr.length() > 0) {
                    JSONObject obj = arr.getJSONObject(0);
                    Profile p = new Profile();
                    p.setId(obj.optString("id"));
                    p.setAuthId(obj.optString("auth_id"));
                    p.setFullName(obj.optString("full_name"));
                    p.setPhone(obj.optString("phone"));
                    p.setSeller(obj.optBoolean("is_seller"));
                    return p;
                }
            }
        } catch (Exception e) { Log.e(TAG, "getProfile error", e); }
        return null;
    }

    public boolean updateProfile(String token, String authId, String fullName, String phone, boolean isSeller) {
        if (token == null || authId == null) return false;
        try {
            JSONObject payload = new JSONObject();
            payload.put("auth_id", authId); // Essential for UPSERT
            payload.put("full_name", fullName != null ? fullName : "");
            payload.put("phone", phone != null ? phone : "");
            payload.put("is_seller", isSeller);

            // Using UPSERT (POST with on_conflict and resolution=merge-duplicates)
            URL url = new URL(getBaseUrl() + "/rest/v1/profile?on_conflict=auth_id");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("apikey", BuildConfig.SUPABASE_ANON_KEY);
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Prefer", "resolution=merge-duplicates,return=representation");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();
            String resBody = readStream(code < 300 ? conn.getInputStream() : conn.getErrorStream());
            conn.disconnect();

            Log.d(TAG, "Upsert Status: " + code + " Body: " + resBody);
            return code >= 200 && code < 300;
        } catch (Exception e) { Log.e(TAG, "updateProfile error", e); return false; }
    }

    public boolean isSeller(String token, String authId) {
        Profile p = getProfile(token, authId);
        return p != null && p.isSeller();
    }

    private HttpResponse post(String path, String body, String token) throws IOException {
        URL url = new URL(getBaseUrl() + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("apikey", BuildConfig.SUPABASE_ANON_KEY);
        if (token != null) conn.setRequestProperty("Authorization", "Bearer " + token);
        try (OutputStream os = conn.getOutputStream()) { os.write(body.getBytes(StandardCharsets.UTF_8)); }
        int code = conn.getResponseCode();
        String resp = readStream(code < 300 ? conn.getInputStream() : conn.getErrorStream());
        conn.disconnect();
        return new HttpResponse(code, resp);
    }

    private String readStream(InputStream is) throws IOException {
        if (is == null) return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        return sb.toString();
    }

    private String extractErrorMessage(String body) {
        try {
            JSONObject json = new JSONObject(body);
            return json.optString("message", json.optString("msg", body));
        } catch (Exception e) { return body; }
    }

    private static class HttpResponse {
        final int statusCode; final String body;
        HttpResponse(int s, String b) { this.statusCode = s; this.body = b; }
    }
}
