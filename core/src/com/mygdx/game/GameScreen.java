package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import pnpMap.PnpMap;
import pnpObject.PnpObject;
import pnpMap.PnpTile;
import pnpObject.PnpObjectProvider;
import pnpObject.PnpUnit;

import java.awt.*;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter implements InputProcessor {
    private MyGdxGame game;
    private GameStage stage;
    private Skin skin;
    private Table uiTable;
    private Window uiWindow;
    private AssetManager assetManager;
    private PnpObjectProvider provider;


    private OrthographicCamera camera;
    private ScreenViewport viewport;

    public final static float SCALE = 32f;
    public final static float INV_SCALE = 1.f / SCALE;

    public final static float VP_WIDTH = 1280 * INV_SCALE;
    public final static float VP_HEIGHT = 720 * INV_SCALE;

    public Point globalCoord;
    public Point selectedTilePoint;
    public PnpObject seleectedPnpObject;
    public PnpMap map;
    public boolean redraw = true;
    public boolean tileSelected = false;
    public boolean pnpObjectSelected = false;


    public Texture texture;
    public SpriteBatch miscBatch;
    public SpriteBatch tileBatch;
    public SpriteBatch objectBatch;
    public ShapeRenderer shapeRenderer;

    public EpMechanics mechanics;
    public GameScreen(MyGdxGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.assetManager = game.assetManager;
        this.provider = game.provider;

        this.viewport = new ScreenViewport(this.camera);

        this.skin = new Skin(Gdx.files.internal("core/assets/cloud-form/skin/cloud-form-ui.json"));

        this.stage = new GameStage(this.viewport, this, this.game);

        this.uiTable = new Table();

        this.uiTable.setFillParent(true);
        this.stage.addActor(this.uiTable);

        this.globalCoord = new Point(0, 0);

        this.map = new PnpMap(50, 50, this.provider);
        this.map.createGrid();
        this.map.initElemts();

        this.selectedTilePoint = new Point(0, 0);

        this.miscBatch = new SpriteBatch();
        this.tileBatch = new SpriteBatch();
        this.objectBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.mechanics = new EpMechanics(this.game, this, this.stage, this.map);
        Gdx.input.setInputProcessor(this);




    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        if (this.map.generated) {

            if (redraw) {
                //System.out.println(this.camera.position);
                this.redraw = false;
            }

            tileBatch.begin();
            for (int i = 0; i < this.VP_WIDTH; i++) {
                for (int j = 0; j < this.VP_HEIGHT; j++) {
                    Point tilePoint = new Point(this.globalCoord.x + i, this.globalCoord.y + j);
                    if (this.map.getGrid().hasTile(tilePoint)) {
                        tileBatch.draw(this.map.getGrid().getTile((int) tilePoint.getX(), (int) tilePoint.getY()).texture, i * SCALE, j * SCALE);
                    }
                }
            }
            tileBatch.end();
            objectBatch.begin();
            for (int i = 0; i < this.VP_WIDTH; i++) {
                for (int j = 0; j < this.VP_HEIGHT; j++) {
                    Point tilePoint = new Point(this.globalCoord.x + i, this.globalCoord.y + j);
                    //System.out.println(i + "  " + j);
                    if (this.map.getGrid().hasTile(tilePoint)) {
                        PnpTile tile = this.map.getTile(tilePoint);
                        Iterator<PnpObject> objectIterator = tile.objectList.iterator();
                        while (objectIterator.hasNext()) {
                            objectBatch.draw(objectIterator.next().texture, i * SCALE, j * SCALE);
                        }
                    }
                }
            }
            objectBatch.end();
            //shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            if (this.tileSelected) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1, 0, 0, 0);
                shapeRenderer.rect((this.selectedTilePoint.x - this.globalCoord.x) * SCALE, (this.selectedTilePoint.y - this.globalCoord.y) * SCALE, 32, 32);
                shapeRenderer.end();
            }
            //miscBatch.begin();

            //miscBatch.end();
            this.stage.draw();
        }
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
        int screenWidht = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int iScreenY = screenHeight - screenY;

        if (button != Input.Buttons.LEFT || pointer > 0) return false;

        Point tilePoint = new Point((int) (screenX / SCALE) + this.globalCoord.x, (int) (iScreenY / SCALE) + this.globalCoord.y);

        if (this.map.getGrid().hasTile(tilePoint)) {
            PnpTile tile = this.map.getTile(tilePoint.x, tilePoint.y);

            if (!this.tileSelected) {

                this.selectedTilePoint = tilePoint;
                this.tileSelected = true;

                if ((screenY + 64 > screenHeight) && screenX - 64 < 0) { //leftBottom
                    this.camera.position.set(Gdx.graphics.getWidth() - screenX - 64, screenY - 64, 0);
                } else if (screenX - 64 < 0 ||  (screenY - 64 < 0 && screenX - 64 < 0)) { //LeftTop || left
                    this.camera.position.set( Gdx.graphics.getWidth() - screenX - 64, screenY + 64, 0);
                } else if (screenY - 64 < 0 && screenX + 64 > screenWidht) { //rightTop
                    this.camera.position.set(Gdx.graphics.getWidth() - screenX + 64, screenY + 64, 0);
                } else if ((screenY + 64 > screenHeight) && (screenX + 64 > screenWidht)) { //rightBottom
                    this.camera.position.set(Gdx.graphics.getWidth() - screenX + 64, screenY - 64, 0);
                } else if ((screenX + 64 > screenWidht)) { // Right
                    this.camera.position.set(Gdx.graphics.getWidth() - screenX + 64, screenY, 0);
                } else {
                    this.camera.position.set(Gdx.graphics.getWidth() - screenX - 32, screenY - 32, 0);
                }


                if (!tile.objectList.isEmpty()) {
                    Iterator<PnpObject> objectList = tile.objectList.iterator();
                    while (objectList.hasNext()) {
                        PnpObject object = objectList.next();
                        TextButton btn = new TextButton(object.name, skin);
                        this.stage.addButtonListenerWithObject(btn, object);


                        this.uiTable.add(btn).width(100).padTop(0).uniform();
                        this.uiTable.row();
                        Gdx.input.setInputProcessor(this.stage);
                    }
                }


            } else {

                if (this.selectedTilePoint != tilePoint) {
                    PnpTile selectedTile = this.map.getTile(this.selectedTilePoint);

                    if (this.mechanics.canGo(this.seleectedPnpObject, tilePoint) &&
                            !selectedTile.objectList.isEmpty() && this.pnpObjectSelected &&
                            selectedTile.objectList.contains(this.seleectedPnpObject)) {

                        tile.objectList.add(this.seleectedPnpObject);
                        selectedTile.objectList.remove(this.seleectedPnpObject);
                        this.pnpObjectSelected = false;

                    }
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

    public void resetUiTable() {
        this.uiTable.reset();
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
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            if (this.seleectedPnpObject != null)
                System.out.println(this.seleectedPnpObject.getObjectType());
                this.invokeInventory((PnpUnit)this.seleectedPnpObject);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            //camera.translate(-1, 0, 0);
            //System.out.println("left");
            this.redraw = true;
            this.globalCoord.x -= 10;
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            //camera.translate(1, 0, 0);
            //System.out.println("right, campos: " + camera.position);
            this.redraw = true;
            this.globalCoord.x += 10;
            return true;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            //camera.translate(0, -1, 0);
            this.globalCoord.y -= 10;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            //camera.translate(0, 1, 0);
            this.globalCoord.y += 10;
        }

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
        }
        //System.out.println("input handle");
        return false;
    }
    public void invokeInventory(PnpUnit unit) {
        System.out.println(unit.getAttack());
        uiWindow = new Window("Inventory screen", this.skin);
        uiWindow.setVisible(true);
        List equipment = new List(this.skin);
        List inventory = new List(this.skin);

        SplitPane invPane = new SplitPane(equipment, inventory, true, this.skin);
        uiWindow.addActor(invPane);
        this.stage.addActor(uiWindow);
        Gdx.input.setInputProcessor(this.stage);

    }
    @Override
    public void resize(int width, int height) {
        // viewport must be updated for it to work properly
        //System.out.println("resize");
        viewport.update(width, height, true);
        this.miscBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        this.tileBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        this.objectBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);

        this.shapeRenderer.updateMatrices();
        this.shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, width, height);

    }

    @Override
    public void dispose() {
        // disposable stuff must be disposed
        //shapes.dispose();
        super.dispose();
        this.miscBatch.dispose();
        this.tileBatch.dispose();
        this.map.dispose();
        this.texture.dispose();
        this.stage.dispose();
        this.skin.dispose();
        this.shapeRenderer.dispose();
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

