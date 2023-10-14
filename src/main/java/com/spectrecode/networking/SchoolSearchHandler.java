package com.spectrecode.networking;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spectrecode.data.School;
import com.spectrecode.data.Schools;
import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SchoolSearchHandler extends Handler {
    public SchoolSearchHandler() {
        super("/api/schoolsearch", null);
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
        if(exchange.getRequestURI().getQuery() != null){
            String[] params = exchange.getRequestURI().getQuery().split("&");
            HashMap<String, String> paramsMap = new HashMap<>();
            for(String param : params){
                String key = param.split("=")[0];
                String value = param.split("=")[1];
                paramsMap.put(key, value);
            }
            if(paramsMap.get("query") != null){
                JsonArray resp = new JsonArray();
                List<School> schools = Schools.search(paramsMap.get("query"));
                schools.forEach(school -> resp.add(school.toJson()));
                String resps = resp.toString();
                byte[] respBytes = resps.getBytes();
                exchange.sendResponseHeaders(200, respBytes.length);
                exchange.getResponseBody().write(respBytes);
                exchange.getResponseBody().close();
            }else{
                String resps = "{\"success\":false,\"message\":\"No query provided\"}";
                byte[] respBytes = resps.getBytes();
                exchange.sendResponseHeaders(200, respBytes.length);
                exchange.getResponseBody().write(respBytes);
                exchange.getResponseBody().close();
            }
        }else {
            String resps = "{\"success\":false,\"message\":\"No query provided\"}";
            byte[] respBytes = resps.getBytes();
            exchange.sendResponseHeaders(200, respBytes.length);
            exchange.getResponseBody().write(respBytes);
            exchange.getResponseBody().close();
        }
    }
}
