package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

public class Inventory extends Window {
//
//    static private final Vector2 tmpPosition = new Vector2();
//    static private final Vector2 tmpSize = new Vector2();
//    static private final int MOVE = 1 << 5;
//
//    private WindowStyle style;
//    boolean isMovable = true, isModal, isResizable;
//    int resizeBorder = 8;
//    boolean keepWithinStage = true;
//    Label titleLabel;
//    Table titleTable;
//    boolean drawTitleTable;
//
    public Inventory (String title, Skin skin) {

        super(title, skin.get(WindowStyle.class));
        super.setSkin(skin);

    }
//
//    public Inventory (String title, Skin skin, String styleName) {
//        this(title, skin.get(styleName, WindowStyle.class));
//        super.setSkin(skin);
//    }
//
//
//    public Inventory (String title, WindowStyle style) {
//
//        if (title == null) throw new IllegalArgumentException("title cannot be null.");
//        setTouchable(Touchable.enabled);
//        setClip(true);
//
//        titleLabel = new Label(title, new Label.LabelStyle(style.titleFont, style.titleFontColor));
//        titleLabel.setEllipsis(true);
//
//        titleTable = new Table() {
//            public void draw (Batch batch, float parentAlpha) {
//                if (drawTitleTable) super.draw(batch, parentAlpha);
//            }
//        };
//        titleTable.add(titleLabel).expandX().fillX().minWidth(0);
//        addActor(titleTable);
//
//        setStyle(style);
//        setWidth(150);
//        setHeight(150);
//
//        addCaptureListener(new InputListener() {
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                toFront();
//                return false;
//            }
//        });
//        addListener(new InputListener() {
//            float startX, startY, lastX, lastY;
//
//            private void updateEdge (float x, float y) {
//                float border = resizeBorder / 2f;
//                float width = getWidth(), height = getHeight();
//                float padTop = getPadTop(), padLeft = getPadLeft(), padBottom = getPadBottom(), padRight = getPadRight();
//                float left = padLeft, right = width - padRight, bottom = padBottom;
//                edge = 0;
//                if (isResizable && x >= left - border && x <= right + border && y >= bottom - border) {
//                    if (x < left + border) edge |= Align.left;
//                    if (x > right - border) edge |= Align.right;
//                    if (y < bottom + border) edge |= Align.bottom;
//                    if (edge != 0) border += 25;
//                    if (x < left + border) edge |= Align.left;
//                    if (x > right - border) edge |= Align.right;
//                    if (y < bottom + border) edge |= Align.bottom;
//                }
//                if (isMovable && edge == 0 && y <= height && y >= height - padTop && x >= left && x <= right) edge = MOVE;
//            }
//
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                if (button == 0) {
//                    updateEdge(x, y);
//                    dragging = edge != 0;
//                    startX = x;
//                    startY = y;
//                    lastX = x - getWidth();
//                    lastY = y - getHeight();
//                }
//                return edge != 0 || isModal;
//            }
//
//            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                dragging = false;
//            }
//
//            public void touchDragged (InputEvent event, float x, float y, int pointer) {
//                if (!dragging) return;
//                float width = getWidth(), height = getHeight();
//                float windowX = getX(), windowY = getY();
//
//                float minWidth = getMinWidth(), maxWidth = getMaxWidth();
//                float minHeight = getMinHeight(), maxHeight = getMaxHeight();
//                Stage stage = getStage();
//                boolean clampPosition = keepWithinStage && getParent() == stage.getRoot();
//
//                if ((edge & MOVE) != 0) {
//                    float amountX = x - startX, amountY = y - startY;
//                    windowX += amountX;
//                    windowY += amountY;
//                }
//                if ((edge & Align.left) != 0) {
//                    float amountX = x - startX;
//                    if (width - amountX < minWidth) amountX = -(minWidth - width);
//                    if (clampPosition && windowX + amountX < 0) amountX = -windowX;
//                    width -= amountX;
//                    windowX += amountX;
//                }
//                if ((edge & Align.bottom) != 0) {
//                    float amountY = y - startY;
//                    if (height - amountY < minHeight) amountY = -(minHeight - height);
//                    if (clampPosition && windowY + amountY < 0) amountY = -windowY;
//                    height -= amountY;
//                    windowY += amountY;
//                }
//                if ((edge & Align.right) != 0) {
//                    float amountX = x - lastX - width;
//                    if (width + amountX < minWidth) amountX = minWidth - width;
//                    if (clampPosition && windowX + width + amountX > stage.getWidth()) amountX = stage.getWidth() - windowX - width;
//                    width += amountX;
//                }
//                if ((edge & Align.top) != 0) {
//                    float amountY = y - lastY - height;
//                    if (height + amountY < minHeight) amountY = minHeight - height;
//                    if (clampPosition && windowY + height + amountY > stage.getHeight())
//                        amountY = stage.getHeight() - windowY - height;
//                    height += amountY;
//                }
//                setBounds(Math.round(windowX), Math.round(windowY), Math.round(width), Math.round(height));
//            }
//
//            public boolean mouseMoved (InputEvent event, float x, float y) {
//                updateEdge(x, y);
//                return isModal;
//            }
//
//            public boolean scrolled (InputEvent event, float x, float y, int amount) {
//                return isModal;
//            }
//
//            public boolean keyDown (InputEvent event, int keycode) {
//                return isModal;
//            }
//
//            public boolean keyUp (InputEvent event, int keycode) {
//                return isModal;
//            }
//
//            public boolean keyTyped (InputEvent event, char character) {
//                return isModal;
//            }
//        });
//    }
}
