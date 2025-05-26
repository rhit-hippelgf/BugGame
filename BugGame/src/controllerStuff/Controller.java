package controllerStuff;
/**
 * Class: Controller, this class takes in the player instance and keyboard interactions.
 * It runs methods on the player instance depending on keyboard inputs.
 * 
 * @author team 1
 */
import javax.swing.*;
import creatureStuff.Player;
import java.awt.Point;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import roomStuff.Room;
import java.awt.Robot;
import java.awt.PointerInfo;
import java.awt.MouseInfo;



public class Controller implements MouseListener {
    private final Player player;
    private final Set<Integer> kPress = new HashSet<>();
    private final JComponent component;
    private Room room;



    public Controller(JComponent component, Player player) {
        this.player = player;
        this.component = component;

        component.setFocusable(true);
        component.requestFocusInWindow();
        setupKeyBindings();
        component.addMouseListener(this);

    }
    
    public void setRoom(Room room) {
        this.room = room;
    }


    private void setupKeyBindings() {
        int scope = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap im = component.getInputMap(scope);
        ActionMap am = component.getActionMap();

        bind(im, am, "pressed W", () -> kPress.add(KeyEvent.VK_W));
        bind(im, am, "released W", () -> kPress.remove(KeyEvent.VK_W));

        bind(im, am, "pressed A", () -> kPress.add(KeyEvent.VK_A));
        bind(im, am, "released A", () -> kPress.remove(KeyEvent.VK_A));

        bind(im, am, "pressed S", () -> kPress.add(KeyEvent.VK_S));
        bind(im, am, "released S", () -> kPress.remove(KeyEvent.VK_S));

        bind(im, am, "pressed D", () -> kPress.add(KeyEvent.VK_D));
        bind(im, am, "released D", () -> kPress.remove(KeyEvent.VK_D));
        
        bind(im, am, "pressed G", () -> kPress.add(KeyEvent.VK_G));
        bind(im, am, "released G", () -> kPress.remove(KeyEvent.VK_G));

    }

    private void bind(InputMap im, ActionMap am, String keyStroke, Runnable action) {
        im.put(KeyStroke.getKeyStroke(keyStroke), keyStroke);
        am.put(keyStroke, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    public void moveIfPress() {
    	if (kPress.contains(KeyEvent.VK_G)) {
    	    player.addCurrency(10);
    	    System.out.println("[DEBUG] G pressed → adding 10 gold.");
    	}

        boolean w = kPress.contains(KeyEvent.VK_W);
        boolean a = kPress.contains(KeyEvent.VK_A);
        boolean s = kPress.contains(KeyEvent.VK_S);
        boolean d = kPress.contains(KeyEvent.VK_D);

        Double dx = null;
        Double dy = null;

        if (w && d) {
        	dx = 0.7071;
        	dy = -0.7071;
        }
        else if (w && a) {
        	dx = -0.7071;
        	dy = -0.7071;
        }
        else if (s && d) {
        	dx = 0.7071;
        	dy = 0.7071;
        }
        else if (s && a) {
        	dx = -0.7071;
        	dy = 0.7071;
        }
        else if (w) {
        	dx = 0.0;
        	dy = -1.0;
        }
        else if (a) {
        	dx = -1.0;
        	dy = 0.0;
        }
        else if (s) {
        	dx = 0.0;
        	dy = 1.0;
        }
        else if (d) {
        	dx = 1.0;
        	dy = 0.0;
        }

        if (dx != null && dx != null) {
            player.calculateSpeeds(dx,dy);
            player.setIsWalking(true);
//            component.repaint();
        } else {
        	player.setIsWalking(false);
        }
    }

    // MouseListener methods
    @Override
    public void mousePressed(MouseEvent e) {
        Point mouse = e.getPoint(); // Already relative to Room

        double px = player.getX();
        double py = player.getY();
        
        double dx = mouse.x - player.getX();
        double dy = mouse.y - player.getY();
        double mag = Math.sqrt(dx*dx+dy*dy);
        
        dx = dx/mag;
        dy = dy/mag;

//        double angle = Math.atan2(mouse.y - player.getY(), mouse.x - player.getX());


//        System.out.printf("Mouse local: (%d, %d), Player center: (%.2f, %.2f), Angle: %.3f\n",
//                          mouse.x, mouse.y, px, py, dx, dy);

        player.shoot(dx, dy);
    }



//    public void mousePressed(MouseEvent e) {
//        System.out.println("Mouse pressed!");
//        if (room != null && room.isShowing()) {
//            Point roomPos = room.getLocationOnScreen();
//            Point mouse = e.getLocationOnScreen();
//
//            int relX = mouse.x - roomPos.x;
//            int relY = mouse.y - roomPos.y;
//
//            // ❗ Use getX() and getY() directly — they are already centered
//            double angle = Math.atan2(relY - player.getY(), relX - player.getX());
//            System.out.println("Angle to shoot: " + angle);
//            player.shoot(angle);
//        } else {
//            System.out.println("Room not ready to get screen location.");
//        }
//    }


    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
