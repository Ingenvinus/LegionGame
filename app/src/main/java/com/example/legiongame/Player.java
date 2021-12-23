package com.example.legiongame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    //player characteristics
    float movementSpeed;
    int direction;
    int lives;
    float score;

    //position & dimension
    float xCentre, yCentre;
    float width, height;
    Rectangle boundingBoxPlayer;

    //graphics
    Texture playerTexture;

    public Player(float movementSpeed, int direction, int lives, float score,
                  float xCentre, float yCentre,
                  float width, float height,
                  Texture playerTexture) {
        this.movementSpeed = movementSpeed;
        this.direction = direction;
        this.lives = lives;
        this.score = score;
        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.width = width;
        this.height = height;
        this.boundingBoxPlayer = new Rectangle(xCentre, yCentre, width, height);
        this.playerTexture = playerTexture;
    }

    public void draw(Batch batch){
        batch.draw(playerTexture, xCentre, yCentre, width , height);
    }

    public boolean intersects(Rectangle otherRectangle){
        return boundingBoxPlayer.overlaps(otherRectangle);
    }

    public void translate(float xChange, float yChange){
        boundingBoxPlayer.setPosition((boundingBoxPlayer.x + xChange),(boundingBoxPlayer.getY() + yChange));
        this.xCentre += xChange;
        this.yCentre += yChange;
    }
    public void update(float deltaTime){
        boundingBoxPlayer.set(xCentre, yCentre, width, height);
    }
}
