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

        PnpItem item1 = new PnpItem();
        item1.setName("Item1");
        PnpItem item2 = new PnpItem();
        item2.setName("Item2");
        PnpItem item3 = new PnpItem();
        item3.setName("Item3");
        PnpItem item4 = new PnpItem();
        item4.setName("Item3");
        PnpItem item5 = new PnpItem();
        item5.setName("Item3");
        PnpItem item6 = new PnpItem();
        item6.setName("Item3");

        this.inventory.add(item1);
        this.inventory.add(item2);
        this.inventory.add(item3);
        this.inventory.add(item4);
        this.inventory.add(item5);
        this.inventory.add(item6);

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
