package pnpMap;

import com.badlogic.gdx.graphics.Texture;
import pnpObject.PnpObject;

import java.util.ArrayList;

public class PnpTile {
    public ArrayList<PnpObject> objectList;
    public Texture texture;
    public int x, y;
    public String type;
    public boolean canGo = true;
    public enum Type {
        WATER, GROUND
    }

    public PnpTile() {

    }

    public PnpTile(int x, int y) {
        this.type = type;
        if (this.type == "WATER") {
            this.canGo = false;
        }

        this.objectList = new ArrayList<PnpObject>();

        if (x % 2 == 0) {
            this.texture = new Texture("core/assets/res/testSprite.png");
        } else {
            this.texture = new Texture("core/assets/res/grey.png");
        }

        this.x = x;
        this.y = y;
    }
    public PnpTile(int x, int y, String type) {
        this.type = type;

        this.objectList = new ArrayList<PnpObject>();

        if (x % 2 == 0) {
            this.texture = new Texture("core/assets/res/testSprite.png");
        } else {
            this.texture = new Texture("core/assets/res/grey.png");
        }

        this.x = x;
        this.y = y;
    }
    public void addObject(PnpObject object) {
        this.objectList.add(object);
    }
    public void tilePressed() {
        System.out.println("Tile pressed: " + this.x + ", " + this.y);
    }

    public void dispose() {
        this.texture.dispose();
    }
}
