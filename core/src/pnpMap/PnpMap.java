package pnpMap;

import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import pnpObject.PnpObject;
import pnpObject.PnpObjectProvider;

import javax.sql.rowset.spi.XmlReader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PnpMap extends Map {
    private PnpGrid grid;
    private MapLayer gridLayer;
    private int width, height = 0;

    public PnpMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.createGrid();
        this.initElemts();

    }
    public void createGrid() {
       this.grid = new PnpGrid(width, height);
    }
    public void createGridLayer() {
        this.gridLayer = new MapLayer();
        super.getLayers().add(gridLayer);

    }
    public void initElemts() {
        PnpObjectProvider p = new PnpObjectProvider();
        //Add units
        ArrayList<PnpObject> units = p.getObjects("unit");
        //Add items
        //ArrayList<PnpObject> items = p.getObjects("item");
        Iterator<PnpObject> iterator = units.iterator();
        while (iterator.hasNext()) {
            this.grid.addObject(new Point(0,0), iterator.next());
        }
    }
    public PnpGrid getGrid() {
        return grid;
    }

    public PnpTile getTile(int x, int y) {
        return this.grid.getTile(x, y);
    }

    public PnpTile getTile(Point tile) {
        return this.grid.getTile(tile);
    }

    @Override
    public void dispose() {
        this.grid.disposeOfTiles();

    }
}
