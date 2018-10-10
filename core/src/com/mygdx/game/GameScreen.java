package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pnpMap.PnpMap;
import pnpObject.*;
import pnpMap.PnpTile;
import ui.PnpJFrame;

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
    private InputMultiplexer multiplexer;

    private OrthographicCamera camera;
    private ScreenViewport viewport;

    public final static float SCALE = 32f;
    public final static float INV_SCALE = 1.f / SCALE;

    //public final static float VP_WIDTH = 1280 * INV_SCALE;
    //public final static float VP_HEIGHT = 720 * INV_SCALE;
    public final static float VP_WIDTH = 1280;
    public final static float VP_HEIGHT = 720;

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

        this.multiplexer = new InputMultiplexer(this.stage, this); //sequence is important.
        Gdx.input.setInputProcessor(this.multiplexer);

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
        System.out.println("clicked");
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
                } else if (screenX - 64 < 0 || (screenY - 64 < 0 && screenX - 64 < 0)) { //LeftTop || left
                    this.camera.position.set(Gdx.graphics.getWidth() - screenX - 64, screenY + 64, 0);
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
//                        Gdx.input.setInputProcessor(this.stage);
                        if (!this.multiplexer.getProcessors().contains(this.stage, false)) {
                            System.out.println("Doesn't conatin");
                            this.multiplexer.addProcessor(this.stage);
                        } else {
                            this.multiplexer.getProcessors().forEach(inputProcessor -> {
                                System.out.println(inputProcessor);
                            });
                        }

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

    public void closeUiWindow() {
        this.uiWindow.remove();
        this.uiWindow = null;
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
            this.invokeInventory((PnpUnit) this.seleectedPnpObject);
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

        PnpJFrame w = new PnpJFrame(unit);

        w.setVisible(true);
//        uiWindow = new Window("Inventory screen", this.skin);
//        uiWindow.setSize(400, 400);
//        //closing button
//        TextButton closeButton = new TextButton("x", this.skin);
//        closeButton.setColor(Color.RED);
//        closeButton.addListener(new PnpEventListener(uiWindow, this.stage));
//        uiWindow.getTitleTable().add(closeButton);
//
//        Table equipmentTable = new Table(this.skin);
//        equipmentTable.setWidth(200);
//        Table inventoryTable = new Table(this.skin);
//        inventoryTable.setWidth(200);
//
//        for (int i = 0; i < unit.getEquipment().size(); i++) {
//            CharSequence slot = PnpUnitSlot.getSlotName(i);
//            equipmentTable.add(new Label(slot,  this.skin));
//
//
//            PnpItem item = unit.getEquipment().get(i);
//            if (item == null) {
//                CharSequence label = "Empty";
//                equipmentTable.add(new Label(label,  this.skin));
//            } else {
//                CharSequence label = item.name;
//                equipmentTable.add(new Label(label,  this.skin));
//            }
//            equipmentTable.row();
//
//        }
//        List inventoryList = new List<>(this.skin);
//        inventoryList.setFillParent(true);
//
//        inventoryList.setItems(unit.getInventory());
//        inventoryTable.add(inventoryList);
//
//        SplitPane splitPane = new SplitPane(equipmentTable, inventoryTable, false, this.skin);
//
//        uiWindow.add(splitPane);
//        uiWindow.setVisible(true);
//
//        this.stage.addActor(uiWindow);
//        if (!this.multiplexer.getProcessors().contains(this.stage, false)) {
//            this.multiplexer.addProcessor(this.stage);
//        }
//
//        this.multiplexer.getProcessors().forEach(inputProcessor -> {
//            System.out.println(inputProcessor);
//        });

    }

    public InputMultiplexer getMultiplexer() {
        return this.multiplexer;
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

