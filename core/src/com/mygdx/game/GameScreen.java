package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import pnpMap.PnpMap;
import pnpMap.PnpObject;
import pnpMap.PnpTile;

import java.awt.*;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter implements InputProcessor {
    private MyGdxGame game;
    private GameStage stage;

    private OrthographicCamera camera;
    private ExtendViewport viewport;

    public final static float SCALE = 32f;
    public final static float INV_SCALE = 1.f / SCALE;

    public final static float VP_WIDTH = 1280 * INV_SCALE;
    public final static float VP_HEIGHT = 720 * INV_SCALE;

    public Point globalCoord;
    public Point selectedTilePoint;
    public PnpMap map;
    public boolean redraw = true;
    public boolean tileSelected = false;


    public Texture texture;
    public SpriteBatch testBatch;
    public SpriteBatch tileBatch;
    public SpriteBatch objectBatch;
    public Vector3 touchPos;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);
        this.stage = new GameStage(this.viewport);
        this.globalCoord = new Point(0, 0);
        this.map = new PnpMap(50, 50);
        this.selectedTilePoint = new Point(0, 0);
        testBatch = new SpriteBatch();
        tileBatch = new SpriteBatch();
        objectBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        if (redraw) {

            System.out.println(this.camera.position);
            this.redraw = false;
        }

        testBatch.begin();
        for (int i = 0; i < this.VP_WIDTH; i++) {
            for (int j = 0; j < this.VP_HEIGHT; j++) {
                Point tilePoint = new Point((int) this.globalCoord.x + i, (int) this.globalCoord.y + j);
                if (this.map.getGrid().hasTile(tilePoint)) {
                    testBatch.draw(this.map.getGrid().getTile((int) tilePoint.getX(), (int) tilePoint.getY()).texture, i * SCALE, j * SCALE);
                }
            }
        }
        testBatch.end();
        objectBatch.begin();
        for (int i = 0; i < this.VP_WIDTH; i++) {
            for (int j = 0; j < this.VP_HEIGHT; j++) {
                Point tilePoint = new Point((int) this.globalCoord.x + i, (int) this.globalCoord.y + j);
                if (this.map.getGrid().hasTile(tilePoint)) {
                    PnpTile tile = this.map.getTile(tilePoint);
                    Iterator<PnpObject> objectIterator = tile.objectList.iterator();
                    //int x = 0;
                    while (objectIterator.hasNext()) {
                        //System.out.println(x);
                        objectBatch.draw(objectIterator.next().texture, i * SCALE, j * SCALE);
                        //x++;
                    }
                }
            }
        }
        objectBatch.end();

    }

    boolean dragging;

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        int iScreenY = Gdx.graphics.getHeight() - screenY;

        if (button != Input.Buttons.LEFT || pointer > 0) return false;

        Point tilePoint = new Point((int) (screenX / SCALE) + this.globalCoord.x, (int) (iScreenY / SCALE) + this.globalCoord.y);

        if (this.map.getGrid().hasTile(tilePoint)) {
            PnpTile tile = this.map.getTile(tilePoint.x, tilePoint.y);

            if (!this.tileSelected) {
                //System.out.println("tile selected " + tilePoint);
                this.selectedTilePoint = tilePoint;
                this.tileSelected = true;
                /*Table table = new Table();
                table.setPosition(screenX, screenY);
                table.add(new Actor());
                table.setVisible(true);
                this.stage.addActor(table);*/
            } else {
                //System.out.println("new tile");
                PnpTile selectedTile = this.map.getTile(this.selectedTilePoint);

                if (!selectedTile.objectList.isEmpty()) {
                    //System.out.println("moving object to " + tilePoint);
                    PnpObject object = selectedTile.objectList.get(0); // ADD ITERATOR TO SHOW LIST
                    tile.objectList.add(object);
                    selectedTile.objectList.remove(object);

                }
                this.tileSelected = false;

            }
        } else {
            System.out.println("no point");
        }
        //System.out.println(tile.x + " " + tile.y);
        dragging = true;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!dragging) return false;
        //camera.unproject(tp.set(screenX, screenY, 0));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        //camera.unproject(tp.set(screenX, screenY, 0));
        dragging = false;
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-1, 0, 0);
            System.out.println("left");
            this.redraw = true;
            this.globalCoord.x--;
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(1, 0, 0);
            System.out.println("right, campos: " + camera.position);
            this.redraw = true;
            this.globalCoord.x++;
            return true;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 1, 0);
        }
        //System.out.println("input handle");
        return false;
    }

    @Override
    public void resize(int width, int height) {
        // viewport must be updated for it to work properly
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        // disposable stuff must be disposed
        //shapes.dispose();
        this.testBatch.dispose();
        this.tileBatch.dispose();
        this.map.dispose();
        this.texture.dispose();

    }


    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

