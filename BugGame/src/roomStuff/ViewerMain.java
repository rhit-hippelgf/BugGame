package roomStuff;
/**
 * Class: ViewerMain, this class is the main class ran, it creates the instance of RoomLogic and the Jframe.
 * If we were to do this in the future maybe better OOD principles could have been used for this class and
 * RoomLogic, as they are very similar and this passes much of the information to RoomLogic. 
 * 
 * @author team 1
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class ViewerMain {
    public static final int DELAY = 12;
    

    public static void createGUI() {
    	System.setProperty("sun.java2d.uiScale", "1.0");
    	System.setProperty("sun.java2d.dpiaware", "true");

        // the actual screen size Java sees
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int SCREEN_WIDTH = screenSize.width;
        final int SCREEN_HEIGHT = screenSize.height;

        // keep 13 x 7 grid — scale square tiles to fit within 90% of both width and height
        final int NUM_COLS = 13;
        final int NUM_ROWS = 7;

        final int maxTileWidth = (int)(SCREEN_WIDTH * 0.8 / NUM_COLS);
        final int maxTileHeight = (int)(SCREEN_HEIGHT * 0.9 / NUM_ROWS);
        final int TILE_SIZE = Math.min(maxTileWidth, maxTileHeight); // ensure square

        final int ROOM_WIDTH = TILE_SIZE * NUM_COLS;
        final int ROOM_HEIGHT = TILE_SIZE * NUM_ROWS;

        final int ROOM_X = (SCREEN_WIDTH - ROOM_WIDTH) / 2;
        final int ROOM_Y = (SCREEN_HEIGHT - ROOM_HEIGHT) / 2;

        // Debug info
        System.out.println("Toolkit screen: " + SCREEN_WIDTH + " x " + SCREEN_HEIGHT);
        System.out.println("Tile size: " + TILE_SIZE + " x " + TILE_SIZE);
        System.out.println("Room bounds: " + ROOM_X + ", " + ROOM_Y + ", " + ROOM_WIDTH + " x " + ROOM_HEIGHT);

        // Set up window
        JFrame frame = new JFrame("Viewer"); 
        frame.setUndecorated(false);
        frame.setLayout(null);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Build RoomLogic with square tile size
        RoomLogic logic = new RoomLogic(TILE_SIZE, ROOM_WIDTH, ROOM_HEIGHT, ROOM_X, ROOM_Y, frame, SCREEN_WIDTH, SCREEN_HEIGHT);
//        Room room = logic.getCurrentRoom();
//        room.setBounds(ROOM_X, ROOM_Y, ROOM_WIDTH, ROOM_HEIGHT);
//        frame.add(room);

        // Game loop
        GameAdvanceListner listener = new GameAdvanceListner(logic);
        Timer timer = new Timer(DELAY, listener);
        timer.start();
        
        frame.requestFocusInWindow();
//        room.requestFocusInWindow();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        AffineTransform t = gc.getDefaultTransform();

        System.out.println("DPI Scale X: " + t.getScaleX());
        System.out.println("DPI Scale Y: " + t.getScaleY());
        System.setProperty("sun.java2d.uiScale", "1.0");
        SwingUtilities.invokeLater(ViewerMain::createGUI);
    }
    

}