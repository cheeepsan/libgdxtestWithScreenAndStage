package pnpObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PnpUnit extends PnpObject {

    private Map<PnpUnitSlot, PnpItem> equipment;
    private Array<PnpItem> inventory;

    public PnpUnit() {this.init();}
    public PnpUnit(int hp, int attack) {
        super.hp = hp;
        super.attack = attack;
        super.objectType = "unit";
        this.init();
        //create slots for head, hands, body and legs
    }
    private void init() {
        this.equipment = new HashMap();
        this.inventory = new Array<>();


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

    public Array<PnpItem> getInventory() {
        return inventory;
    }

    public Map<PnpUnitSlot, PnpItem> getEquipment() {
        return this.equipment;
    }
}
