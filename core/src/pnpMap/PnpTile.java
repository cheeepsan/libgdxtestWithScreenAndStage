package pnpMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import pnpObject.pnpTypes.ObjectType;
import pnpObject.PnpObject;
import pnpObject.PnpObjectProvider;

import java.util.ArrayList;

public class PnpTile extends PnpObject {
    public ArrayList<PnpObject> objectList;
    public Texture texture;
    public int x, y;
    public String type;
    public boolean passable = true;

    public PnpTile(String type, PnpObjectProvider p) {
        this.type = type;
        JsonValue data = p.getTileDataByType(type);
        if (data != null) {
            this.passable = data.getBoolean("passable");
            this.texture = p.getTexture(data.getString("texture"));
        } else {
            this.passable = false;
            this.texture = p.getTexture("core/assets/res/textures/no_text.png");
        }

        this.objectList = new ArrayList<PnpObject>();
        super.objectType = ObjectType.TILE;
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
