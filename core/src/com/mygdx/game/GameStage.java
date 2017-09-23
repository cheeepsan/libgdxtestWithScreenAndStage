package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import pnpObject.PnpObject;


public class GameStage extends Stage {

    private GameScreen screen;
    private Game game;
    //private Camera camera;
    public GameStage() {

    }
    public GameStage(Viewport viewport, GameScreen screen, Game game) {
        this.screen = screen;
        this.game = game;
        //this.camera = this.getViewport().getCamera();
        super.setViewport(viewport);

    }

    @Override
    public void draw() {

        super.draw();

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }
    @Override
    public boolean keyDown(int keycode) {
        /*
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {

            camera.translate(0, 1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(1, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-1, 0, 0);
        }*/
        //System.out.println("input handle");
        return false;
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
