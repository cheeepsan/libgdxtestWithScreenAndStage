package pnpObject;

public class PnpUnit extends PnpObject {
    private int attack;
    private int hp;

    public PnpUnit() {}
    public PnpUnit(int hp, int attack) {
        this.hp = hp;
        this.attack = attack;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setAttack(int attack) {
        this.attack = attack;
        this.attack = attack;
    }
}
