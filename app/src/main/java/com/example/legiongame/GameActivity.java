package com.example.legiongame;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.example.legiongame.Database.DB;

public class GameActivity extends AndroidApplication {

    GameView view;
    LegionGame game;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        view = new GameView();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(view, config);

    }


    public static class GameView extends Game {

        public LegionGame game;

        @Override
        public void create() {
            game = new LegionGame();
            game.resize(72, 128);
            setScreen(game);
        }

        @Override
        public void dispose() {
            super.dispose();
            game.dispose();
        }

        @Override
        public void render() {
            super.render();
        }

        @Override
        public void resize(int width, int height) {
            game.resize(width, height);
        }
    }
}