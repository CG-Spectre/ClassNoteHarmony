package com.spectrecode.networking;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.HashMap;

public class LoginHandler extends Handler{
    public LoginHandler() {
        super("/api/login", null);
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
        String query = exchange.getRequestURI().getQuery();
        String token = null;
        String reftoken = null;
        if(query != null){
            String[] params = query.split("&");
            for(String param : params){
                String key = param.split("=")[0];
                String value = param.split("=")[1];
                if(key.equals("token")){ token = value;}
                if(key.equals("reftoken")){ reftoken = value;}
            }
        }
        if(token == null || reftoken == null){
            String resp = "{\"success\":false,\"message\":\"Invalid token or refresh token\"}";
            byte[] respBytes = resp.getBytes();
            exchange.sendResponseHeaders(200, respBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(respBytes);
            os.close();
            return;
        }
        HttpURLConnection con = (HttpURLConnection) (new URL(Config.HOST + "api/verifytoken?token="+token+"&reftoken="+reftoken+"x")).openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        String resp = content.toString();
        Gson gson = new Gson();
        JsonObject gsonResp = gson.fromJson(resp, JsonObject.class);
        boolean success = gsonResp.get("success").getAsBoolean();
        if(!success){
            resp = "{\"success\":false,\"message\":\"Invalid token or refresh token\"}";
        }else{
            String tokenf = gsonResp.get("data").getAsJsonObject().get("token").getAsString();
            String reftokenf = gsonResp.get("data").getAsJsonObject().get("reftoken").getAsString();
            resp = "{\"success\":true,\"token\":\""+tokenf+"\",\"reftoken\":\""+reftokenf+"\"}";
        }
        System.out.println(resp);
        byte[] respBytes = resp.getBytes();
        exchange.sendResponseHeaders(200, respBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(respBytes);
        os.close();
    }
}
