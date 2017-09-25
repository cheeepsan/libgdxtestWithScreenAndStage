package pnpObject;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;

public class PnpUnit extends PnpObject {

    private HashMap<PnpUnitSlot, PnpItem> equipment;
    private ArrayList<PnpItem> inventory;

    public PnpUnit() {}
    public PnpUnit(int hp, int attack) {
        super.hp = hp;
        super.attack = attack;
        super.objectType = "unit";
        //create slots for head, hands, body and legs
    }
    public void setHp(int hp) {
        super.hp = hp;
    }
    public void setAttack(int attack) {
        super.attack = attack;
    }

    public void setTexture(String texture) {
        super.texture = new Texture(texture);
    }

}
