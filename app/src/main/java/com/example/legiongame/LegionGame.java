package com.example.legiongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class LegionGame implements Screen {


    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private Texture background;
    private Texture playerTexture;
    private Texture obstacleTexture;

    //timings
    private int backgroundOffset = 0;
    private int meteoriteOffset = 0;

    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;
    private final float TOUCH_MOVE_THRESHOLD = 0.0000001f;

    private Player player;
    private Obstacles obstacle;
    private LinkedList<Obstacles> obstacleList;

    boolean gameOver = false;


    LegionGame() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        background = new Texture("background.png");
        backgroundOffset = 0;
        playerTexture = new Texture("player.jpg");
        obstacleTexture = new Texture("meteorite.png");

        //setup players or game objects
        player = new Player(30,1,WORLD_WIDTH/2-4,WORLD_HEIGHT/4,10,10, playerTexture);

        obstacle = new Obstacles(0, WORLD_HEIGHT, 1, 1, obstacleTexture, 100, 1);

        obstacleList = new LinkedList<>();

        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        batch.begin();

        detectInput(deltaTime);

        player.update(deltaTime);

        batch.draw(background, 0,-backgroundOffset, WORLD_WIDTH, WORLD_HEIGHT);

        player.draw(batch);

        renderObstacles(deltaTime);

        obstacle.update(deltaTime);

        detectCollision();

        batch.end();

    }

    public void renderObstacles(float deltaTime){
        if (obstacle.canFallMeteorite()){
            Obstacles[] obstacles = obstacle.fallenMeteorites();
            for (Obstacles obstacle: obstacles) {
                obstacleList.add(obstacle);
            }
        }

        ListIterator<Obstacles> iterator = obstacleList.listIterator();
        while(iterator.hasNext()){
            Obstacles obstacles = iterator.next();
            obstacles.draw(batch);
            obstacles.yCentre -= obstacles.speed*deltaTime;
            if(obstacles.yCentre > WORLD_HEIGHT){
                iterator.remove();
            }
        }
    }

    public void detectCollision(){
        ListIterator<Obstacles> iterator = obstacleList.listIterator();
        while(iterator.hasNext()){
            Obstacles obstacles = iterator.next();
            if(player.intersects(obstacles.getBoundingBox())){

            }
        }
    }

    public void detectInput(float deltaTime){
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -player.boundingBoxPlayer.x;
        downLimit = -player.boundingBoxPlayer.y;
        rightLimit = WORLD_WIDTH - player.boundingBoxPlayer.x - player.boundingBoxPlayer.width;
        upLimit = WORLD_HEIGHT / 2 - player.boundingBoxPlayer.y - player.boundingBoxPlayer.height;

        if (Gdx.input.isTouched()){
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);

            Vector2 playerCentre = new Vector2( player.boundingBoxPlayer.x + player.boundingBoxPlayer.width,
                                                player.boundingBoxPlayer.y + player.boundingBoxPlayer.height);
            float touchDistance = touchPoint.dst(playerCentre);
            if(touchDistance > TOUCH_MOVE_THRESHOLD){
                float xTouchDifference = touchPoint.x- playerCentre.x;
                float yTouchDifference = touchPoint.y- playerCentre.y;

                float xMove = xTouchDifference / touchDistance * player.movementSpeed * deltaTime;
                float yMove = yTouchDifference / touchDistance * player.movementSpeed * deltaTime;

                if(xMove > 0 ) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);

                if(yMove > 0 ) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);

                player.translate(xMove, yMove);
            }

        }

    }

    public void move(){
        //scrolling background
        backgroundOffset++;
        if (backgroundOffset % WORLD_HEIGHT == 0){
            backgroundOffset = 0;
        }


        batch.draw(background, 0,0,-backgroundOffset, WORLD_HEIGHT);
    }

    public void obstacle(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Random r = new Random();

        float minxCentre = 1;
        float speed = 10;
        float timeBetweenObstacle = 10;

        float minSize = 20;
        float maxSize = 50;

        float randomLocation = minxCentre + r.nextFloat() * (WORLD_WIDTH - minxCentre);
        float randomSize = minSize + r.nextFloat() * (maxSize - minSize);

        obstacle = new Obstacles(randomLocation, WORLD_HEIGHT, randomSize, randomSize, obstacleTexture, speed, timeBetweenObstacle);

        obstacle.draw(batch);

        //meteoriteOffset++;
        //if (meteoriteOffset % WORLD_HEIGHT == 0){
        //    meteoriteOffset = 0;
        //}

        //batch.draw(obstacleTexture, 0,-meteoriteOffset, WORLD_WIDTH, WORLD_HEIGHT);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
