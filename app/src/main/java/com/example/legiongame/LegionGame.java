package com.example.legiongame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LegionGame implements Screen {


    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private Texture background;
    private Texture playerTexture;

    //timings
    private int backgroundOffset;

    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    private Player player;

    LegionGame() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        background = new Texture("background.png");
        backgroundOffset = 0;
        playerTexture = new Texture("player.jpg");

        //setup players or game objects
        player = new Player(1,1,WORLD_WIDTH/2,WORLD_HEIGHT/5,10,10, playerTexture);

        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        batch.begin();


        player.draw(batch);

        batch.end();
    }

    public void move(){
        //scrolling background
        backgroundOffset++;
        if (backgroundOffset % WORLD_HEIGHT == 0){
            backgroundOffset = 0;
        }

        batch.draw(background, 0,0,-WORLD_WIDTH+WORLD_HEIGHT, WORLD_HEIGHT);
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
