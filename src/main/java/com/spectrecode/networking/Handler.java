package com.spectrecode.networking;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;

public abstract class Handler implements HttpHandler {
    String path;
    HashMap<String, String> params;
    public Handler(String path, @Nullable HashMap<String, String> params){
        this.path = path.replace("/", "_");
        this.params = params;
    }


    public abstract void handle(HttpExchange exchange) throws IOException;
}
