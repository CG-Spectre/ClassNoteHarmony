package com.spectrecode.randomscripts;

public class RandomMethods {
    public static String gen80CharString(){
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12345678900123456789";
        String str = "";
        for(int i = 0; i < 80; i++){
            str += (chars.charAt((int)(Math.random()*chars.length())));
        }
        return str;
    }
}
