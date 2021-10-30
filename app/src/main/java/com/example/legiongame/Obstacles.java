package com.example.legiongame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

public class Obstacles {

    //Obstacle characteristics
    float speed;
    float timeBetweenObstacle;
    float timeSinceLastObstacle = 0;

    //Position & dimension
    float xCentre;
    float yCentre;
    float width, height;

    //Graphics
    Texture obstacleTexture;

    public Obstacles(float xCentre, float yCentre, float width, float height, Texture obstacleTexture, float speed, float timeBetweenObstacle) {

        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.width = width;
        this.height = height;
        this.obstacleTexture = obstacleTexture;
        this.speed = speed;
        this.timeBetweenObstacle = timeBetweenObstacle;
    }

    public void draw(Batch batch){
        batch.draw(obstacleTexture, xCentre, yCentre,width,height);
    }

    public void setxCentre(float xCentre) {
        this.xCentre = xCentre;
    }

    public void setyCentre(float yCentre) {
        this.yCentre = yCentre;
    }

    public float getxCentre() {
        return xCentre;
    }

    public float getyCentre() {
        return yCentre;
    }

    public void update(float deltaTime){
        timeSinceLastObstacle += deltaTime;
    }
    public boolean canFallMeteorite(){
        boolean result = timeSinceLastObstacle - timeBetweenObstacle >= 0;

        if (result){
            timeSinceLastObstacle = 0;
            return result;
        }
        else{
            return result;
        }
    }

    public Obstacles[] fallenMeteorites(){
        Obstacles[] obstacles = new Obstacles[3];

        Random r = new Random();

        float minxCentre = 1;
        float speed = 30;
        float timeBetweenObstacle = 2;

        float minSize = 10;
        float maxSize = 25;

        float randomLocation;
        float randomSize;

        randomLocation = minxCentre + r.nextFloat() * (65 - minxCentre);
        randomSize = minSize + r.nextFloat() * (maxSize - minSize);
        obstacles[0] = new Obstacles(randomLocation, 128, randomSize, randomSize, obstacleTexture, speed, timeBetweenObstacle);

        randomLocation = minxCentre + r.nextFloat() * (72 - minxCentre);
        randomSize = minSize + r.nextFloat() * (maxSize - minSize);
        obstacles[1] = new Obstacles(randomLocation, 128, randomSize, randomSize, obstacleTexture, speed, timeBetweenObstacle);

        randomLocation = minxCentre + r.nextFloat() * (72 - minxCentre);
        randomSize = minSize + r.nextFloat() * (maxSize - minSize);
        obstacles[2] = new Obstacles(randomLocation, 128, randomSize, randomSize, obstacleTexture, speed, timeBetweenObstacle);

        return obstacles;
    }


}
