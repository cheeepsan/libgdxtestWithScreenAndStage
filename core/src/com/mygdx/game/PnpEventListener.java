package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import pnpObject.PnpObject;

public class PnpEventListener implements EventListener {
    private PnpObject object;
    private GameStage stage;
    public PnpEventListener() {}
    public PnpEventListener(PnpObject object, GameStage stage) {
        this.object = object;
        this.stage = stage;
    }
    @Override
    public boolean handle(Event event) {
        String eventType = event.toString(); //IS this ok?
        if (eventType.equals("touchDown")) {
            GameScreen screen = this.stage.getScreen();
            screen.seleectedPnpObject = object;
            screen.pnpObjectSelected = true;
            screen.resetUiTable();
            Gdx.input.setInputProcessor(screen);
        }

        return false;
    }


}
