package com.example.legiongame.Models;

import java.io.Serializable;

public class User implements Serializable {

    //variables
    private String username;
    private float highscore;

    public User(String username, float highscore){
        this.username = username;
        this.highscore = highscore;
    }

    public void setHighscore(float highscore) {
        this.highscore = highscore;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getHighscore() {
        return highscore;
    }

    public String getUsername() {
        return username;
    }

}
