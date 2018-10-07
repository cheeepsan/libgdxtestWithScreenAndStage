package com.mygdx.game;

import com.badlogic.gdx.Game;
import pnpMap.PnpMap;
import pnpMap.PnpTile;
import pnpObject.pnpTypes.ObjectType;
import pnpObject.PnpObject;
import pnpObject.PnpUnit;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EpMechanics {

    private Game game;
    private GameScreen screen;
    private GameStage stage;
    private PnpMap map;

    public EpMechanics(Game game, GameScreen screen, GameStage stage, PnpMap map) {
        this.game = game;
        this.screen = screen;
        this.stage = stage;
        this.map = map;
    }

    public void attack(PnpUnit attacker, PnpUnit defender) {
        boolean defenderAlive = defender.recieveDamage(attacker.getAttack());
        if (defenderAlive) {

        } else {
            //get removed from tile
        }
    }
    public boolean canGo(PnpObject unit, Point point) {
        PnpTile tile = this.map.getTile(point);
        if (tile.passable) {
            if (tile.objectList.isEmpty()) {
                return true;
            } else  {
                ArrayList<PnpObject> objects = tile.objectList;
                Iterator<PnpObject> iterator = objects.iterator();
                while (iterator.hasNext()) {
                    PnpObject object = iterator.next();
                    ObjectType objectType = object.getObjectType();
                    if (unit != null && objectType == ObjectType.UNIT && unit.getTeam().equals(object.getTeam())) {
                        return true;
                    } else if (unit != null && objectType == ObjectType.UNIT && !unit.getTeam().equals(object.getTeam())) {
                        this.attack((PnpUnit)unit, (PnpUnit)object);
                        return false;
                    }
                }
            }
        }
        return false;
    }
    public void move(PnpObject unit, Point point) {
        if (unit.getObjectType().equals("unit")) {

        }
    }
}
