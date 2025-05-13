package roomStuff;

import java.util.*;
import java.awt.Point;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import creatureStuff.Player;
import creatureStuff.enemytypes.*;

public class RoomLogic {

    private int level;
    private int numRooms;
    private Room currentRoom;
    private Creature hero;
    private final int tileSize;
    HashMap<Point, Room> roomLayout = new HashMap<>();

    public RoomLogic(int tileSizeX, int tileSizeY) {
        this.level = 0;
        this.numRooms = 0;
        this.tileSize = tileSizeX; // square tiles, so X and Y are equal
        this.hero = new Player((tileSize * 13) / 2, (tileSize * 7) / 2, 10, 3);
        this.generateLayout();
        System.out.println(this.roomLayout);
    }

    char[][] layout0 = {
            {'T','T','T','T','T','T','T','T','T','T','T','T','T'},
            {'T','.','.','.','.','.','.','.','.','.','.','.','T'},
            {'T','.','Z','.','F','.','B','.','.','.','S','.','T'},
            {'T','.','.','.','.','.','.','.','.','.','.','.','T'},
            {'T','.','.','B','.','.','.','.','Z','.','.','.','T'},
            {'T','.','.','.','.','.','.','.','.','.','.','.','T'},
            {'T','T','T','T','T','T','T','T','T','T','T','T','T'}
    };

    private void generateLayout() {
        char[][] placeholderLayout = layout0;
        ArrayList<Point> rooms = new ArrayList<>();
        Random rand = new Random();

        rooms.add(new Point(0, 0));

        int i = 0;
        while (i < rooms.size()) {
            Point temp = rooms.get(i);
            boolean north = false, east = false, south = false, west = false;

            for (Point p : rooms) {
                if (p.y == temp.y + 1 && p.x == temp.x) north = true;
                else if (p.y == temp.y && p.x == temp.x + 1) east = true;
                else if (p.y == temp.y - 1 && p.x == temp.x) south = true;
                else if (p.y == temp.y && p.x == temp.x - 1) west = true;
            }

            Room r = new Room(placeholderLayout, north, east, south, west, tileSize);
            r.setPlayer((Player) hero);
            roomLayout.put(temp, r);
            i++;
        }

        currentRoom = roomLayout.get(new Point(0,0));

        List<Enemy> enemyTypes = List.of(
            new ZigZag(0, 0, hero),
            new Predictive1(0, 0, hero),
            new Suicide(0, 0, hero),
            new WalkingEnemy(0, 0, hero));

        for (int i1 = 0; i1 < 5; i1++) {
            int ex = rand.nextInt(tileSize * 13 - 100) + 50;
            int ey = rand.nextInt(tileSize * 7 - 100) + 50;

            Enemy template = enemyTypes.get(rand.nextInt(enemyTypes.size()));
            Enemy enemy;
            if (template instanceof ZigZag) {
                enemy = new ZigZag(ex, ey, hero);
            } else if (template instanceof Predictive1) {
                enemy = new Predictive1(ex, ey, hero);
            } else if (template instanceof Suicide) {
                enemy = new Suicide(ex, ey, hero);
            } else {
                enemy = new WalkingEnemy(ex, ey, hero);
            }

            currentRoom.addEnemy(enemy);
        }
    }

    public void updateObjects() {
        currentRoom.updateEntities();
    }

    public void drawScreen() {
        currentRoom.drawScreen();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
