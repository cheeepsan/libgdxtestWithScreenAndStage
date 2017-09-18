package pnpMap;

import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;

import java.awt.*;
import java.util.HashMap;


public class PnpGrid {
    public HashMap<Point, PnpTile> gridMap;


    public PnpGrid(int width, int height) {

        gridMap = new HashMap<Point, PnpTile>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                PnpTile tile = new PnpTile(i, j);
                if (i % 13 == 0 ) {

                    PnpObject object = new PnpObject("test " + i + " by " + j);
                    PnpObject object1 = new PnpObject("test1 " + i + " by " + j);
                    PnpObject object2 = new PnpObject("test2 " + i + " by " + j);
                    PnpObject object3 = new PnpObject("test3 " + i + " by " + j);
                    tile.objectList.add(object);
                    tile.objectList.add(object1);
                    tile.objectList.add(object2);
                    tile.objectList.add(object3);

                }
                gridMap.put(new Point(i, j), tile);
            }
        }

    }

    public PnpTile getTile(int x, int y) {
        return this.gridMap.get(new Point(x, y));
    }

    public PnpTile getTile(Point tile) {
        return this.gridMap.get(tile);
    }

    public boolean hasTile(Point point) {
        if (this.gridMap.containsKey(point)) {
            return true;
        }

        return false;
    }

    public void disposeOfTiles() {
        for (HashMap.Entry<Point, PnpTile> entry : this.gridMap.entrySet()) {
            entry.getValue().dispose();
        }

    }
}
