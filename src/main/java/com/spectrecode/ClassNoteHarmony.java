package com.spectrecode;

import com.spectrecode.data.Schools;
import com.spectrecode.networking.Server;

public class ClassNoteHarmony {
    static Server server;
    public static void main(String[] args) throws Exception {
        server = new Server();
        Schools.init();
    }
}