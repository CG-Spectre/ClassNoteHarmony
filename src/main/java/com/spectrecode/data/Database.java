package com.spectrecode.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static Connection con;
    static Gson gson = new Gson();
    public static void init() throws FileNotFoundException {
        File file = new File("src/main/resources/json/credentials.json");
        JsonObject credentials = gson.fromJson(new JsonReader(new BufferedReader(new FileReader(file))), JsonObject.class);
        String url  = "jdbc:mysql://"+credentials.get("host").getAsString()+":3306/XFA";
        String user = credentials.get("username").getAsString();
        String pass = credentials.get("password").getAsString();

        try{
            con = DriverManager.getConnection(url, user, pass);
            if(con != null){
                System.out.println("Connection to database secure.");
                con.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
