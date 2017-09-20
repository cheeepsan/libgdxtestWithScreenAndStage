package pnpMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PnpObject {
    public Texture texture;
    public String name;
    public PnpObject() {
        this.texture = new Texture("core/assets/object.png");
    }
    public PnpObject(String name) {
        this.name = name;
        this.texture = new Texture("core/assets/object.png");
    }
}
