package pnpMap;

import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;

import java.awt.*;

public class PnpMap extends Map {
    private PnpGrid grid;
    private MapLayer gridLayer;
    private int width, height = 0;

    public PnpMap(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new PnpGrid(width, height);

    }

    public void drawGrid() {
        //this.grid.grid(this.width, this.height);
    }
    /*public ImmediateModeRenderer20 getLineRenderer() {
        return this.grid.getLineRenderer();
    }*/

    public void createGridLayer() {
        this.gridLayer = new MapLayer();
        super.getLayers().add(gridLayer);

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
