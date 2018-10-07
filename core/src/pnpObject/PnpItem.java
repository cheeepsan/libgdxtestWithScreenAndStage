package pnpObject;


import pnpObject.pnpTypes.ItemType;
import pnpObject.pnpTypes.ObjectType;

public class PnpItem extends PnpObject {

    protected ItemType type;

    public PnpItem() {
        super.objectType = ObjectType.ITEM;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public ItemType getItemType() {
        return this.type;
    }
}
