package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pnpMap.PnpMap;

import java.awt.Point;


public class GameStage extends Stage {



    public GameStage() {

    }
    public GameStage(Viewport viewport) {
        super.setViewport(viewport);

    }

    @Override
    public void draw() {
        System.out.println("draw");
        super.draw();
    }
}
