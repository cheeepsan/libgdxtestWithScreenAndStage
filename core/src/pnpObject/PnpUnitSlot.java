package pnpObject;


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

    public int type;
    public PnpUnitSlot(int type){}

    public static String getSlotName(int type) {
        switch (type){
            case 0:
                return "Head";
            case 1:
                return "Shoulders";
            case 2:
                return "Chest";
            case 3:
                return "Right hand";
            case 4:
                return "Left hand";
            case 5:
                return "Legs";
            case 6:
                return "Feet";
            default:
                throw new RuntimeException("Error, wrong slot");
        }
    }
}
