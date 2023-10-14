package com.spectrecode.data;

import com.google.gson.JsonObject;

public class School {
    private String name;
    private String city;
    private String state;
    private String id;
    private String zip;
    private String ncesid;

    public School(String name, String city, String state, String id, String zip, String ncesid){
        this.name = name;
        this.city = city;
        this.state = state;
        this.id = id;
        this.zip = zip;
        this.ncesid = ncesid;
    }

    public String getName(){
        return name;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public String getId(){
        return id;
    }

    public String getZip(){
        return zip;
    }

    public String getNcesid(){
        return ncesid;
    }

    public JsonObject toJson(){
        JsonObject res = new JsonObject();
        res.addProperty("name", name);
        res.addProperty("city", city);
        res.addProperty("state", state);
        res.addProperty("id", id);
        res.addProperty("zip", zip);
        res.addProperty("ncesid", ncesid);
        return res;
    }
}
