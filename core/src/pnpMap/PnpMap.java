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
    public int width, height = 0;
    private PnpObjectProvider provider;
    public boolean generated = false;

    public PnpMap(int width, int height, PnpObjectProvider provider) {
        this.width = width;
        this.height = height;
        this.provider = provider;
    }
    public void createGrid() {
       this.grid = new PnpGrid(width, height, this.provider);
       this.grid.setMap(this);
       this.grid.generate();
    }
    public void createGridLayer() {
        this.gridLayer = new MapLayer();
        super.getLayers().add(gridLayer);

    }
    public void initElemts() {

        //Add units
        ArrayList<PnpObject> units = this.provider.getObjects("unit");
        //Add items
        //ArrayList<PnpObject> items = p.getObjects("item");

        Iterator<PnpObject> iterator = units.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            System.out.println("unit " + i);
            this.grid.addObject(new Point(i,0), iterator.next());
            i++;
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
