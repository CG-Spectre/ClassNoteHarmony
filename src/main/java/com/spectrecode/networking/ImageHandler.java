package com.spectrecode.networking;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class ImageHandler extends Handler {
    public ImageHandler(String path) {
        super(path, null);
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
        if(path.endsWith(".svg")){
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "image/svg+xml");
        }

        File file = new File("src/main/resources/image/" + path);
        exchange.sendResponseHeaders(200, file.length());
        OutputStream os = exchange.getResponseBody();
        Files.copy(file.toPath(), os);
        os.close();
    }
}
