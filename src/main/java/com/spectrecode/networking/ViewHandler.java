package com.spectrecode.networking;

import com.spectrecode.auth.Login;
import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.HashMap;

public class ViewHandler extends Handler{
    public ViewHandler(String path, @Nullable HashMap<String, String> params) {
        super(path, params == null ? new HashMap<>() : params);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);
        String method = path.replace("_", "");
        switch (method){
            case "login":
                Login login = new Login();
                params.put("cuid", login.getCuid());
                break;
            default:
                break;
        }
        File file = new File("src/main/resources/views/" + path + ".html");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st = "";
        String s;
        while((s = br.readLine()) != null){st+=s;}
        String response = st;
        if(params != null){
            String paramsString = "";
            for(String key : params.keySet()){
                paramsString += "<meta id='"+key+"' content='"+params.get(key)+"'>";
            }
            response = response.replace("<head>", "<head>"+paramsString);
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
