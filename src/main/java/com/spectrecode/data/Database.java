package com.spectrecode.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class Database {
    static Connection con;
    static String url;
    static String user;
    static String pass;
    static Gson gson = new Gson();
    public static void init() throws FileNotFoundException {
        File file = new File("src/main/resources/json/credentials.json");
        JsonObject credentials = gson.fromJson(new JsonReader(new BufferedReader(new FileReader(file))), JsonObject.class);
        url  = "jdbc:mysql://"+credentials.get("host").getAsString()+":3306/XFA";
        user = credentials.get("username").getAsString();
        pass = credentials.get("password").getAsString();

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

    public static boolean checkLogin(String xfatoken){
        try{
            con = DriverManager.getConnection(url, user, pass);
            if(con != null){
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE token='"+xfatoken+"'");
                int xfaid = -1;
                while(rs.next()){
                    xfaid = rs.getInt("id");
                }
                if(xfaid > -1) {
                    rs = stmt.executeQuery("SELECT * FROM cnh_users WHERE xfa_id='"+xfaid+"'");
                    int res = 0;
                    while(rs.next()){
                        res++;
                    }
                    if(res > 0) {
                        return true;
                    }
                }
            }
            return false;
        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
