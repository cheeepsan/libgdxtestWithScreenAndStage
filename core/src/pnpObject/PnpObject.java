package pnpObject;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Json;

import java.awt.*;

public class PnpObject extends Actor {
    public Texture texture;
    public String name;
    public Point position;

    protected String objectType;
    protected String team = null;
    protected int attack = 0;
    protected int hp = 0;


    protected final String itemsPath = "core/assets/res/items.json";
    protected final String unitsPath = "core/assets/res/units.json";

    public PnpObject(){}

    public PnpObject(PnpObjectProvider p) {
        //this.texture = new Texture("core/assets/res/object.png");
        this.texture = p.getTexture("core/assets/res/textures/object.png");
    }

    public PnpObject(String name, PnpObjectProvider p) {
        this.name = name;
        //this.texture = new Texture("core/assets/res/object.png");
        this.texture = p.getTexture("core/assets/res/textures/object.png");
        this.objectType = "object";
    }

    public void setName(String name) {
        this.name = name;
        this.objectType = "object";
    }

    public String getObjectType() {
        return this.objectType;
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
