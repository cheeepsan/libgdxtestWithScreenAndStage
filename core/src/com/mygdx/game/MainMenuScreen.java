package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;

public class MainMenuScreen extends ScreenAdapter {
    private MyGdxGame game;
    private GameScreen gameScreen;
    public MainMenuScreen(MyGdxGame game) {
        this.game = game;
        this.gameScreen = new GameScreen(game);


    }

    @Override
    public void render(float delta) {
        this.game.setScreen(this.gameScreen);

    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
