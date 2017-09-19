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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import pnpMap.PnpMap;
import pnpMap.PnpObject;
import pnpMap.PnpTile;

import java.awt.*;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter implements InputProcessor {
    private MyGdxGame game;
    private GameStage stage;
    private Skin skin;
    private Table uiTable;

    private OrthographicCamera camera;
    private ScreenViewport viewport;

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

    public GameScreen(MyGdxGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();

        //this.viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera); OLD
        this.viewport = new ScreenViewport(this.camera);

        this.skin = new Skin(Gdx.files.internal("core/assets/cloud-form/skin/cloud-form-ui.json"));

        this.stage = new GameStage(this.viewport, this, this.game);
        this.uiTable = new Table();

        this.uiTable.setFillParent(true);
        this.stage.addActor(this.uiTable);

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

        this.stage.draw();

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
        //System.out.println("clicked");
        int iScreenY = Gdx.graphics.getHeight() - screenY;

        if (button != Input.Buttons.LEFT || pointer > 0) return false;

        Point tilePoint = new Point((int) (screenX / SCALE) + this.globalCoord.x, (int) (iScreenY / SCALE) + this.globalCoord.y);

        if (this.map.getGrid().hasTile(tilePoint)) {
            PnpTile tile = this.map.getTile(tilePoint.x, tilePoint.y);

            if (!this.tileSelected) {

                this.selectedTilePoint = tilePoint;
                this.tileSelected = true;

                this.camera.position.set(Gdx.graphics.getWidth() - screenX, screenY, 0); // why does this work?

                if (!tile.objectList.isEmpty()) {
                    Iterator<PnpObject> objectList = tile.objectList.iterator();
                    while (objectList.hasNext()) {
                        PnpObject object = objectList.next();
                        TextButton btn = new TextButton(object.name, skin);
                        btn.add(object);
                        btn.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {

                                //System.out.println("Selected: " + actor.);
                                //GameStage stage = (GameStage)actor.getParent().getStage();



                            }
                        });

                        this.uiTable.add(btn).width(100).padTop(0).uniform();
                        this.uiTable.row();
                        Gdx.input.setInputProcessor(this.stage);
                    }
                }



            } else {
                //System.out.println("new tile");
                if (this.selectedTilePoint != tilePoint) {
                    PnpTile selectedTile = this.map.getTile(this.selectedTilePoint);

                    if (!selectedTile.objectList.isEmpty()) {

                        PnpObject object = selectedTile.objectList.get(0);
                        tile.objectList.add(object);
                        selectedTile.objectList.remove(object);

                    }
                }
                this.tileSelected = false;
                this.uiTable.reset();

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
            //camera.translate(-1, 0, 0);
            System.out.println("left");
            this.redraw = true;
            this.globalCoord.x--;
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            //camera.translate(1, 0, 0);
            System.out.println("right, campos: " + camera.position);
            this.redraw = true;
            this.globalCoord.x++;
            return true;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            //camera.translate(0, -1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            //camera.translate(0, 1, 0);
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
        super.dispose();
        this.testBatch.dispose();
        this.tileBatch.dispose();
        this.map.dispose();
        this.texture.dispose();
        this.stage.dispose();
        this.skin.dispose();
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

