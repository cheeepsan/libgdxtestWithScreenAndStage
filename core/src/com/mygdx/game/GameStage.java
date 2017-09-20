package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pnpMap.PnpMap;
import pnpMap.PnpObject;

import java.awt.Point;


public class GameStage extends Stage {

    private GameScreen screen;
    private Game game;
    public GameStage() {

    }
    public GameStage(Viewport viewport, GameScreen screen, Game game) {
        this.screen = screen;
        this.game = game;
        super.setViewport(viewport);

    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //System.out.println(this.getWidth() + "   " + this.getHeight() + ", " + screenX);

        //Gdx.input.setInputProcessor(this.screen);
        return true;
    }
    public void addButtonListener(TextButton button) {

    }
    public void addButtonListenerWithObject(TextButton button, PnpObject object) {
        button.addCaptureListener(new PnpEventListener(object, this));
    }
    public void addListeners() {
        Array<Actor> actors = this.getActors();
    }

    public GameScreen getScreen() {return this.screen;}


}
