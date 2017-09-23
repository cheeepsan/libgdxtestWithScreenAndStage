package pnpObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Json;

import java.awt.*;

public class PnpObject extends Actor {
    public Texture texture;
    public String name;
    public Point position;

    protected final String itemsPath = "core/assets/res/items.json";
    protected final String unitsPath = "core/assets/res/units.json";

    public PnpObject() {
        this.texture = new Texture("core/assets/res/object.png");
    }

    public PnpObject(String name) {
        this.name = name;
        this.texture = new Texture("core/assets/res/object.png");
    }

    public void setName(String name) {
        this.name = name;
    }
}
