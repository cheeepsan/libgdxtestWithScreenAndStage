package pnpObject;


import pnpObject.pnpTypes.SlotType;

/*
0 head
1 shoulders
2 chest
3 right hand
4 left hand
5 legs
6 feet
 */
public class PnpUnitSlot {

    public SlotType type;
    public PnpUnitSlot(int type){
        try {
            this.type = SlotType.values()[type];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No such slot");
        }
    }
    public PnpUnitSlot(SlotType type){
       this.type = type;
    }

    public void setSlotType(SlotType type) {
        this.type = type;
    }
    public void setSlotType(int type) {
        try {
            this.type = SlotType.values()[type];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No such slot");
        } finally {
            this.type = null;
        }
    }

    public static String getSlotName(SlotType type) {
        switch (type){
            case HEAD:
                return "Head";
            case SHOULDERS:
                return "Shoulders";
            case CHEST:
                return "Chest";
            case RIGHT_HAND:
                return "Right hand";
            case LEFT_HAND:
                return "Left hand";
            case LEGS:
                return "Legs";
            case FEET:
                return "Feet";
            default:
                throw new RuntimeException("Error, wrong slot");
        }
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("comparing");
        PnpUnitSlot comparable = null;
        if (o != null) {
            comparable = (PnpUnitSlot)o;
        }
        return this.type == comparable.type;
    }

    public int hashCode() {
        return 0;
    }
}
