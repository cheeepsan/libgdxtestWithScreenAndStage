package pnpMap;

import pnpObject.PnpObject;

import java.awt.*;
import java.util.HashMap;


public class PnpGrid {
    public HashMap<Point, PnpTile> gridMap;
    private int width, height;

    public PnpGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.gridMap = new HashMap<Point, PnpTile>();
        this.fillGrid();

    }
    public void fillGrid() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                PnpTile tile = new PnpTile(i, j);
                //if (i % 13 == 0 ) {

                    //PnpObject object = new PnpObject("test " + i + " by " + j);
                    //tile.objectList.add(object);

                //}
                this.gridMap.put(new Point(i, j), tile);
            }
        }
    }
    public void addTile(Point point, PnpTile tile) {
        this.gridMap.put(point, tile);
    }

    public void addObject(Point point, PnpObject object) {
        PnpTile tile = this.getTile(point);
        tile.addObject(object);
        this.gridMap.put(point, tile);
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
