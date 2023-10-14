package com.spectrecode.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Schools {
    static Gson gson = new Gson();
    static JsonObject psObj;
    public static void init() throws IOException {
        File file = new File("src/main/resources/json/publicschools.json");
        psObj = gson.fromJson(new JsonReader(new BufferedReader(new FileReader(file))), JsonObject.class);
    }
    public static List<School> search(String query){
        List<School> res = new ArrayList();
        query = query.toUpperCase().replace(".", "").replace(",", "");
        for(int i = 1; i <= psObj.size(); i++){
            JsonObject school = psObj.get(String.valueOf(i)).getAsJsonObject();
            String name = school.get("NAME").getAsString();
            if(name.toUpperCase().contains(query)){
                res.add(new School(name, school.get("CITY").getAsString(), school.get("STATE").getAsString(), school.get("OBJECTID").getAsString(), school.get("ZIP").getAsString(), school.get("NCESID").getAsString()));
            }
        }
        return res;
    }
}
