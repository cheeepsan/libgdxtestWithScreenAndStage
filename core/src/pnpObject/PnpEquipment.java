package pnpObject;

import pnpObject.pnpTypes.ItemType;

public class PnpEquipment extends PnpItem {

    private PnpUnitSlot slot;

    PnpEquipment() {
        super.type = ItemType.EQUIPMENT;
    }
    PnpEquipment(PnpUnitSlot slot) {
        super.type = ItemType.EQUIPMENT;
        this.slot = slot;
    }

    PnpEquipment(int slot) {
        super.type = ItemType.EQUIPMENT;
        this.slot = new PnpUnitSlot(slot);
    }

    public PnpUnitSlot getSlot() {
        return this.slot;
    }
}
