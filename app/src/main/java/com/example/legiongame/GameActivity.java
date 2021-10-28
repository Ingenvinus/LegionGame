package com.example.legiongame;

import com.badlogic.gdx.Game;

public class GameActivity extends Game {

    LegionGame game;

    @Override
    public void create() {
        game = new LegionGame();
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