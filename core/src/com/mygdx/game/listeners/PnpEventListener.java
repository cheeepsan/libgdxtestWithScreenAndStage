package com.mygdx.game.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.mygdx.game.GameScreen;
import com.mygdx.game.GameStage;
import pnpObject.PnpObject;

public class PnpEventListener implements EventListener {
    private Actor object;
    private GameStage stage;
    public PnpEventListener() {}
    public PnpEventListener(Actor object, GameStage stage) {
        this.object = object;
        this.stage = stage;
    }

    @Override
    public boolean handle(Event event) {

        String eventType = event.toString(); //IS this ok?
        if (eventType.equals("touchDown")) {
            System.out.println("Button clicked");
            GameScreen screen = this.stage.getScreen();
            if (object instanceof PnpObject) {
                screen.seleectedPnpObject = (PnpObject) object;
                screen.pnpObjectSelected = true;
                screen.resetUiTable();
            } else {
                screen.closeUiWindow();
            }

//            screen.getMultiplexer().removeProcessor(this.stage);
//            Gdx.input.setInputProcessor(screen.getMultiplexer());
        }

        return false;
    }


}
