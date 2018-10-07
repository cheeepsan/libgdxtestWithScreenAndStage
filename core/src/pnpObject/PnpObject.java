package pnpObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import pnpObject.pnpTypes.ObjectType;

import java.awt.*;

public class PnpObject extends Actor {
    public Texture texture;
    public String name;
    public String description;
    public Point position;

    protected ObjectType objectType;
    protected String team = null;
    protected int attack = 0;
    protected int hp = 0;


    protected final String itemsPath = "core/assets/res/items.json";
    protected final String unitsPath = "core/assets/res/units.json";

    public PnpObject(){}

    public PnpObject(PnpObjectProvider p) {
        this.texture = p.getTexture("core/assets/res/textures/object.png");
    }

    public PnpObject(String name, PnpObjectProvider p) {
        this.name = name;
        this.texture = p.getTexture("core/assets/res/textures/object.png");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectType getObjectType() {
        return this.objectType;
    }
    public void setObjectType(ObjectType type) {
        this.objectType = type;
    }
    public String getTeam() {
        return this.team;
    }
    public void setTeam(String team) {
        this.team = team;
    }

    public boolean recieveDamage(int attack) {
        this.hp =- attack;
        if (this.hp >= 0) {
            //die
            return false;
        }
        return true;
    }
    public int getAttack() {
        return this.attack;
    }
}
