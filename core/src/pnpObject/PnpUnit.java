package pnpObject;

import com.badlogic.gdx.graphics.Texture;
import pnpObject.pnpTypes.ObjectType;

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.Map;

public class PnpUnit extends PnpObject {

    private Map<PnpUnitSlot, PnpItem> equipment;
    private ArrayList<PnpItem> inventory;

    public PnpUnit() {this.init();}

    private void init() {
        super.objectType = ObjectType.UNIT;
        this.equipment = new LinkedHashMap<> ();
        this.inventory = new ArrayList<>();


        for (int i = 0; i < 7; i++)  //where 0 = head, 6 = feet. Check PnpUnitSlot
            this.equipment.put(new PnpUnitSlot(i), null);
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
    public void setTexture(Texture texture) {
        super.texture = texture;
    }

    public ArrayList<PnpItem> getInventory() {
        return inventory;
    }

    public Map<PnpUnitSlot, PnpItem> getEquipment() {
        return this.equipment;
    }
}
