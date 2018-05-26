package pnpMap;

import com.badlogic.gdx.math.MathUtils;


import linn.core.Linn;
import linn.core.execute.LinnExecutor;
import linn.core.execute.state.LinnTurtle;
import linn.core.lang.LinnBuilder;
import other.Point;
import pnpObject.PnpObjectProvider;


import java.nio.channels.Pipe;
import java.util.*;

public class PnpMapGenerator {
    private int width, height, centerAmount;
    private PnpGrid grid;
    private PnpObjectProvider provider;
    private HashMap<Point, String> centerPoints;
    private ArrayList<Point> rooms;
    private ArrayList<Point> centerPointsList;
    private ArrayList<Point> usedCenterPoints;
    private LinnExecutor linnExecutor;

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
        this.usedCenterPoints = new ArrayList<Point>();
        this.generateCenterPoints(500);

        System.out.println("Generating rooms");
        this.generateRooms(500);
        System.out.println("Fitting rooms");
        this.fitRooms();

//        System.out.println("Carve corridors");
//        this.carveCorridors();

        System.out.println("Filling empty spaces");
        this.fillEmpty();
        System.out.println("Done");
        this.grid.getMap().generated = true;
    }

    private void carveCorridors() {

        Collections.sort(this.usedCenterPoints); //sort by x
        Point prev = null;

        for (int i = 0; i < this.usedCenterPoints.size(); i++) {
            if (i == 0) {
                prev = this.usedCenterPoints.get(i);
                continue;
            }
            Point current = this.usedCenterPoints.get(i);
            while (!current.equals(prev)) {
                this.grid.addTile(new Point(prev), new PnpTile("grey", this.provider));
                System.out.println("PREV: " + prev + " " + i);
                System.out.println("CURRENT : "+ current +  " " + i);
                if (prev.x == current.x) {
                    if (prev.y > current.y) {
                        prev.y--;
                    } else {
                        prev.y++;
                    }
                } else if (prev.y == current.y) {
                    if (prev.x > current.x) {
                        prev.x--;
                    } else {
                        prev.x++;
                    }
                } else {
                    if (prev.y > current.y ) {
                        prev.y--;
                    } else if (prev.y < current.y ) {
                        prev.y++;
                    } else if (prev.x > current.x) {
                        prev.x--;
                    } else if (prev.x < current.x) {
                        prev.x++;
                    }
                }

            }
            prev = current;

        }

    }

    protected void generateCenterPoints(int amount) {
        ArrayList<String> tileTypes = this.provider.getTileTypes();
        for (int i = 0; i < amount; i++) {
            int x = MathUtils.random(this.width);
            int y = MathUtils.random(this.height);

            Point newPoint = new Point(x, y);



            int typeNum = MathUtils.random(tileTypes.size() - 1);
            this.centerPoints.put(newPoint, tileTypes.get(typeNum));
            this.centerPointsList.add(newPoint);
        }
    }

    private void corridorVertical(int x, int y, Point center) {

    }

    private void corridorHorizontal(int x, int y, Point center) {

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

            if (this.rooms.isEmpty()) break;

            Point center = centerIterator.next();
            boolean fits = false;

            Iterator<Point> roomIterator = this.rooms.iterator();

            while (roomIterator.hasNext()) {

                Point room = roomIterator.next();

                roomLoop:
                //bad practice?
                for (int i = 0; i < room.x; i++) {
                    for (int j = 0; j < room.y; j++) {
                        Point tile = new Point((center.x + i), (center.y + j));
                        boolean hasTile = this.grid.hasTile(tile);

                        if ((tile.getX() + 1) > this.width || (tile.getY() + 1) > this.height || hasTile) {
                            fits = false;
                            break roomLoop;
                        } else {
                            fits = true;
                        }
                    }
                }

                if (fits) {

                    for (int i = 0; i < room.x; i++) {
                        for (int j = 0; j < room.y; j++) {
                            Point tile;
                            if (i == (room.x - 1) || j == (room.y - 1) || i == 0 || j == 0) {

                                tile = new Point(center.x + i, center.y + j);
                                this.grid.addTile(tile, new PnpTile("stone", this.provider));
                                continue;
                            }
                            tile = new Point((center.x + i), (center.y + j));
                            this.grid.addTile(tile, new PnpTile("roomWall", this.provider));
                        }
                    }
                    this.usedCenterPoints.add(center);
                    roomsToDelete.add(room);
                    break;
                }
            }
            if (roomsToDelete.size() > 0) {
                for (Point room : roomsToDelete) {
                    this.rooms.remove(room);
                }
                roomsToDelete.clear();
            }
        }
    }

    public void fillEmpty() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.grid.hasTile(new Point(i, j))) {

                } else {
                    this.grid.addTile(new Point(i, j), new PnpTile("dirt", this.provider));
                }
            }
        }
    }
/*
    private double calculateDistance(Point grid, Point center) {
        return grid.distance(center);
    }

    public void checkRadius(Point point) {

    }


    //Maybe one day
    protected void generateRoads(ArrayList<Point> road) {
        int LEN = 10;
        double ANGLE = 1 / 2.0 * Math.PI;
        Linn linn = LinnBuilder.newLinn("SierpinskiExample").withAuthor("Thomas Trojer")
                .withDefaultMoveLength(LEN).withDefaultAngles(ANGLE)
                // rule: A ---> A + B - A A + A + A A + A B + A A - B + A A - A - A A - A B - A A A
                .withRule("A").andProduction().F("A").yaw().f("B").negYaw().F("A").F("A").yaw().F("A").yaw().F("A").F("A").yaw().F("A").f("B").yaw().F("A").F("A").negYaw().f("B").yaw().F("A").F("A").negYaw().F("A").negYaw().F("A").F("A").negYaw().F("A").f("B").negYaw().F("A").F("A").F("A").done()
                // rule: B ---> B B B B B B
                .withRule("B").andProduction().f("B").f("B").f("B").f("B").f("B").f("B").done()
                // finalize
                .build();

        this.linnExecutor = LinnExecutor.newExecutor().useLinn(linn).traceStates(true).onStateChanged(t -> {
            if (t.hasPreviousState() == false) {
                // await a second position to draw a line
                return;
            }
            // connect previous and current position with a line
            LinnTurtle tp = t.getPreviousState();
            road.add(new Point((int) tp.getX(), (int) tp.getY()));

        }).withAxiom().F("A").yaw().F("A").yaw().F("A").yaw().F("A").done();

        this.linnExecutor.executeAtMost(2);
    }

    protected void connectRoads(ArrayList<Point> road) {
        //Minimum spanning tree

        //Prim's algorithm
        //Kruskal's algorithm

        Point r0;
        Point r1;
        int deltaX, deltaY;

        for (int i = 0; i < road.size(); i++) {
            if (i + i <= road.size()) {
                r0 = road.get(i);
                r1 = road.get(i + 1);

                //X

                deltaX = ((int) r0.x - (int) r1.x);

                if (deltaX > 0) {
                    for (int j = 0; j < deltaX; j++) {
                        //new Point()
                    }
                } else {
                    deltaX = Math.abs(deltaX);
                    for (int j = 0; j < deltaX; j++) {

                    }
                }
            }
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
*/
}
