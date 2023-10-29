package com.spectrecode.networking;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.spectrecode.data.Database;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jetbrains.annotations.Nullable;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class RefreshHandler extends Handler {
    public RefreshHandler() {
        super("/api/refresh", null);
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
        String query = exchange.getRequestURI().getQuery();
        String reftoken = null;
        if(query != null){
            String[] params = query.split("&");
            for(String param : params){
                String key = param.split("=")[0];
                String value = param.split("=")[1];
                if(key.equals("reftoken")){ reftoken = value;}
            }
        }
        if(reftoken == null){
            String resp = "{\"success\":false,\"message\":\"Invalid refresh token\"}";
            byte[] respBytes = resp.getBytes();
            exchange.sendResponseHeaders(200, respBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(respBytes);
            os.close();
            return;
        }
        HttpURLConnection con = (HttpURLConnection) (new URL(Config.HOST + "api/verifytoken?token=none&reftoken="+reftoken)).openConnection();
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
            resp = "{\"success\":false,\"message\":\"Invalid refresh token\"}";
        }else{
            String tokenf = gsonResp.get("data").getAsJsonObject().get("token").getAsString();
            String reftokenf = gsonResp.get("data").getAsJsonObject().get("reftoken").getAsString();
            boolean hasAccount = Database.checkLogin(tokenf);
            resp = "{\"success\":true,\"token\":\""+tokenf+"\",\"reftoken\":\""+reftokenf+"\", \"hasAccount\":"+hasAccount+"}";
        }
        System.out.println(resp);
        byte[] respBytes = resp.getBytes();
        exchange.sendResponseHeaders(200, respBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(respBytes);
        os.close();
    }
}
