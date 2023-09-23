package com.spectrecode.networking;

import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class FileHandler extends Handler {
    public FileHandler(String folder, String path) {
        super(path, null);
        this.path = folder + "/" + path;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
        File file = new File("src/main/resources/" + path);
        exchange.sendResponseHeaders(200, file.length());
        OutputStream os = exchange.getResponseBody();
        Files.copy(file.toPath(), os);
        os.close();
    }
}
