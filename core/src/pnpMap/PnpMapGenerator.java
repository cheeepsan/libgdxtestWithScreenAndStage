package pnpMap;

import com.badlogic.gdx.math.MathUtils;
import pnpObject.PnpObjectProvider;

import java.awt.*;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class PnpMapGenerator {
    private int width, height, centerAmount;
    private PnpGrid grid;
    private PnpObjectProvider provider;
    private HashMap<Point, String> centerPoints;
    private ArrayList<Point> rooms;
    private ArrayList<Point> centerPointsList;


    private int BIG_ROOM_X = 20;
    private int BIG_ROOM_Y = 20;
    private int MEDIUM_ROOM_X = 12;
    private int MEDIUM_ROOM_Y = 12;
    private int SMALL_ROOM_X = 5;
    private int SMALL_ROOM_Y = 5;


    public PnpMapGenerator(int width, int height, PnpGrid grid, PnpObjectProvider provider) {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.provider = provider;
        this.generate();
    }

    protected void generate() {
        this.rooms = new ArrayList<Point>();
        this.centerPoints = new HashMap<Point, String>();
        this.centerPointsList = new ArrayList<Point>();
        this.generateCenterPoints(100);
        System.out.println("Generating rooms");
        this.generateRooms(10);
        System.out.println("Fitting rooms");
        this.fitRooms();
        System.out.println("Filling empty spaces");
        this.fillEmpty();
        System.out.println("Done");
        this.grid.getMap().generated = true;
    }

    protected void generateCenterPoints(int amount) {

        for (int i = 0; i < amount; i++) {
            int x = MathUtils.random(this.width);
            int y = MathUtils.random(this.height);

            Point newPoint = new Point(x, y);

            ArrayList<String> tileTypes = this.provider.getTileTypes();

            int typeNum = MathUtils.random(tileTypes.size() - 1);
            this.centerPoints.put(newPoint, tileTypes.get(typeNum));
            this.centerPointsList.add(newPoint);
        }
    }

    protected void fillRandomTiles() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                Point point = new Point(i, j); //currentPoint

                Point closestPoint = new Point(0, 0);
                double closestDistance = 0;
                int k = 0;

                Iterator<Point> iterator = this.centerPoints.keySet().iterator();
                while (iterator.hasNext()) {
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
                this.grid.addTile(point, new PnpTile(this.centerPoints.get(closestPoint), this.provider));
            }
        }

    }

    private void generateRooms(int amountOfRooms) {

        int r = MathUtils.random(3);
        for (int i = 0; i < amountOfRooms; i++) {
            int width = 0;
            int height = 0;
            switch (r) { //small
                case 0:
                    width = MathUtils.random(4, SMALL_ROOM_X);
                    height = MathUtils.random(4, SMALL_ROOM_Y);
                    break;
                case 1: //medium
                    width = MathUtils.random(4, MEDIUM_ROOM_X);
                    height = MathUtils.random(4, MEDIUM_ROOM_Y);
                    break;
                case 2: //big
                    width = MathUtils.random(4, BIG_ROOM_X);
                    height = MathUtils.random(4, BIG_ROOM_Y);
                    break;
                default:
                    width = MathUtils.random(4, SMALL_ROOM_X);
                    height = MathUtils.random(4, SMALL_ROOM_Y);
                    break;
            }
            this.rooms.add(new Point(width, height));
        }

    }
    private void fitRooms() {
        Iterator<Point> centerIterator = this.centerPointsList.iterator();
        ArrayList<Point> roomsToDelete = new ArrayList<Point>();
        while (centerIterator.hasNext()) {
            Point center = centerIterator.next();
            boolean fits = false;

            Iterator<Point> roomIterator = this.rooms.iterator();
            int num = 0;
            while (roomIterator.hasNext()) {

                //System.out.println("checking space for room " + num);
                System.out.println("center: " + center);
                Point room = roomIterator.next();

                roomLoop: //bad practice?
                for (int i = 0; i < room.x; i++) {
                    for (int j  = 0; j < room.y; j++) {
                        Point tile = new Point((center.x + i), (center.y + j));
                        boolean hasTile = this.grid.hasTile(tile);
                        //System.out.println("x: " + tile.getX() + ", y: " + tile.getY() + " has tile: " + hasTile);
                        System.out.println(this.width + " asd " + tile.getX());
                        if ((int)tile.getX() > this.width || (int)tile.getY() > this.height) {
                            fits = false;
                            System.out.println("TOO BIG");
                            break roomLoop;
                        }
                        if (hasTile) {
                            fits = false;
                            break roomLoop;
                        } else {
                            fits = true;
                        }
                    }
                }
                //System.out.println("new iteration");
                if (fits) {
                    //System.out.println("fits into " +  (center.x + room.x) + ", y " +  (center.y + room.y));
                    for (int i = 0; i < room.x; i++) {
                        for (int j = 0; j < room.y; j++) {
                            Point tile;
                            if ( i == (room.x - 1) || j == (room.y - 1) || i == 0 || j == 0 ) {

                                tile = new Point(center.x + room.x + i, center.y + room.y +j);
                                System.out.println(tile.getX());
                                this.grid.addTile(tile, new PnpTile("stone", this.provider));
                                continue;
                            }
                            tile = new Point((center.x + room.x + i), (center.y + room.y +j));
                            this.grid.addTile(tile, new PnpTile("roomWall", this.provider));
                        }
                    }
                    roomsToDelete.add(room);
                    continue;
                }
            }
            if (roomsToDelete.size() > 0) {
                for (Point room : roomsToDelete) {
                    this.rooms.remove(room);
                }
                roomsToDelete.clear();
            }
            if (fits) break;
        }
    }

    public void fillEmpty() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.grid.hasTile(new Point(i,j))) {

                } else {
                    this.grid.addTile(new Point(i, j), new PnpTile("dirt", this.provider));
                }
            }
        }
    }
    private double calculateDistance(Point grid, Point center) {
        return grid.distance(center);
    }

    public void checkRadius(Point point) {
        
    }
}
