package com.spectrecode.auth;

import com.spectrecode.randomscripts.RandomMethods;

import java.util.HashMap;

public class Login {
    static HashMap<String, Login> openInstances = new HashMap<>();
    private String cuid;
    private LoginState state;
    public Login(){
        cuid = RandomMethods.gen80CharString();
        openInstances.put(cuid, this);

        this.state = LoginState.OPENED;

    }
    public String getCuid(){
        return cuid;
    }

    public LoginState getState(){
        return state;
    }

    public void setState(LoginState state){
        this.state = state;
        if(this.state == state.ACCEPTED || this.state == state.REJECTED){
            openInstances.remove(cuid);
        }
    }
}
