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
                if (i == 0 && j == 0) {
                    System.out.println(tile.x + " " + tile.y);
                    PnpObject object = new PnpObject();
                    tile.objectList.add(object);
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
