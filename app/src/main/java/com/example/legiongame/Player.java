package com.example.legiongame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Player {

    //player characteristics
    float movementSpeed;
    int direction;

    //position & dimension
    float xCentre, yCentre;
    float width, height;

    //graphics
    Texture playerTexture;

    public Player(float movementSpeed, int direction,
                  float xCentre, float yCentre,
                  float width, float height,
                  Texture playerTexture) {
        this.movementSpeed = movementSpeed;
        this.direction = direction;
        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.width = width;
        this.height = height;
        this.playerTexture = playerTexture;
    }

    public void draw(Batch batch){
        batch.draw(playerTexture, xCentre, yCentre,width,height);
    }

}
