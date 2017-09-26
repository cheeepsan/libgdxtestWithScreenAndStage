package pnpMap;

import pnpObject.PnpObject;
import pnpObject.PnpObjectProvider;

import java.awt.*;
import java.util.HashMap;


public class PnpGrid {
    public HashMap<Point, PnpTile> gridMap;
    public PnpMapGenerator generator;
    private PnpMap map;
    private int width, height;
    private PnpObjectProvider provider;

    public PnpGrid(int width, int height, PnpObjectProvider provider) {
        this.width = width;
        this.height = height;
        this.gridMap = new HashMap<Point, PnpTile>();
        this.provider = provider;
        //this.fillGrid();

    }

    public void addTile(Point point, PnpTile tile) {
        this.gridMap.put(point, tile);
    }

    public void addObject(Point point, PnpObject object) {
        PnpTile tile = this.getTile(point);
        tile.addObject(object);
        this.gridMap.put(point, tile);
    }

    public void generate() {
        this.generator = new PnpMapGenerator(this.width, this.height, this, this.provider);

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
    public void setMap(PnpMap map) {
        this.map = map;
    }
    public PnpMap getMap() {
        return this.map;
    }
}
