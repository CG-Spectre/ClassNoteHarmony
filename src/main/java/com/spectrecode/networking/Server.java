package com.spectrecode.networking;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class Server {
    HttpServer server;
    public Server() throws Exception {
        int port = 81;
        server = HttpServer.create(new InetSocketAddress(port), 0);
        HashMap<String, String> params = new HashMap<>();
        server.createContext("/login", new ViewHandler("/login", null));
        server.createContext("/api/resources/font/Product-Sans-Regular.ttf", new FileHandler("font", "Product-Sans-Regular.ttf"));
        for(File file : new File("src/main/resources/image").listFiles()){
            String path = file.getName().replace("_", "/");
            server.createContext("/api/resources/image"+path, new ImageHandler(path));
        }
        server.setExecutor(null);
        server.start();
        System.out.println("Started on port: " + port);
    }
}
