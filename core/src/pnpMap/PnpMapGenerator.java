package pnpMap;

import com.badlogic.gdx.math.MathUtils;
import pnpObject.PnpObjectProvider;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class PnpMapGenerator {
    private int width, height, centerAmount;
    private PnpGrid grid;
    private PnpObjectProvider provider;
    private HashMap<Point, String> centerPoints;

    public PnpMapGenerator(int width, int height, PnpGrid grid) {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.provider = new PnpObjectProvider();
        this.generate();
    }

    protected void generate() {
        centerPoints = new HashMap<Point, String>();
        this.generateCenterPoints(1000);
        this.generateBlocks();

    }

    protected void generateCenterPoints(int amount) {

        for (int i = 0; i < amount; i++) {
            int x = MathUtils.random(this.width);
            int y = MathUtils.random(this.height);

            Point newPoint = new Point(x, y);
            //if (this.centerPoints.containsKey(newPoint)) {
            //    System.out.println("recursion");
            //    this.generateCenterPoints(i);
            //}
            ArrayList<String> tileTypes = this.provider.getTileTypes();

            int typeNum = MathUtils.random(tileTypes.size() - 1);
            //System.out.println(tileTypes.get(typeNum));
            this.centerPoints.put(newPoint, tileTypes.get(typeNum));
        }
    }

    protected void generateBlocks() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                Point point = new Point(i, j); //currentPoint

                Point closestPoint = new Point(0,0);
                double closestDistance = 0;
                int k = 0;

                Iterator<Point> iterator = this.centerPoints.keySet().iterator();
                while(iterator.hasNext()) {
                    Point center = iterator.next();
                    if (k == 0) {
                        closestDistance = point.distance(center);
                        closestPoint = center;
                        //System.out.println(closestDistance);
                    } else {
                        double distance = point.distance(center);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestPoint = center;

                        }
                    }

                    k++;
                }
                //System.out.println(closestPoint + " " + this.centerPoints.get(closestPoint));
                this.grid.addTile(point, new PnpTile(this.centerPoints.get(closestPoint), this.provider));
            }
        }
        //System.out.println(this.grid);
        this.grid.getMap().generated = true;
    }

    private double calculateDistance(Point grid, Point center) {
        return grid.distance(center);
    }
}
