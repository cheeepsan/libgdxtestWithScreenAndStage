package pnpMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import pnpObject.PnpObject;
import pnpObject.PnpObjectProvider;

import java.util.ArrayList;

public class PnpTile extends PnpObject{
    public ArrayList<PnpObject> objectList;
    public Texture texture;
    public int x, y;
    public String type;
    public boolean passable = true;

    public PnpTile() {

    }
    public PnpTile(String type, PnpObjectProvider p) {
        this.type = type;
        JsonValue data = p.getTileDataByType(type);
        this.passable = data.getBoolean("passable");
        this.texture = new Texture(data.getString("texture"));

        this.objectList = new ArrayList<PnpObject>();
        super.objectType = "tile";
    }
    public PnpTile(String type) {
        //System.out.println(type);
        this.type = type;

        if (this.type.equals("water")) {
            this.texture = new Texture("core/assets/res/water.png");
            this.passable = false;

        } else if (this.type.equals("dirt")) {
            this.texture = new Texture("core/assets/res/dirt.png");
        } else {
            this.texture = new Texture("core/assets/res/grass.png");
        }

        this.objectList = new ArrayList<PnpObject>();
    }

    public PnpTile(int x, int y) {
        this.type = type;
        if (this.type == "WATER") {
            this.passable = false;
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
