package com.spectrecode.networking;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.logging.FileHandler;

public class Server {
    HttpServer server;
    public Server() throws Exception {
        server = HttpServer.create(new InetSocketAddress(80), 0);
        HashMap<String, String> params = new HashMap<>();
        server.createContext("/login", new ViewHandler("/login", null));
        for(File file : new File("src/main/resources/image").listFiles()){
            String path = file.getName().replace("_", "/");
            server.createContext("/api/resources/image"+path, new ImageHandler(path));
        }
        server.setExecutor(null);
        server.start();
    }
}
