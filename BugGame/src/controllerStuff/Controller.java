package controllerStuff;

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
        boolean w = kPress.contains(KeyEvent.VK_W);
        boolean a = kPress.contains(KeyEvent.VK_A);
        boolean s = kPress.contains(KeyEvent.VK_S);
        boolean d = kPress.contains(KeyEvent.VK_D);

        Double theta = null;

        if (w && d) theta = 7 * Math.PI / 4;
        else if (w && a) theta = 5 * Math.PI / 4;
        else if (s && d) theta = Math.PI / 4;
        else if (s && a) theta = 3 * Math.PI / 4;
        else if (w) theta = 3 * Math.PI / 2;
        else if (a) theta = Math.PI;
        else if (s) theta = Math.PI / 2;
        else if (d) theta = 0.0;

        if (theta != null) {
            player.calculateSpeeds(theta);
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

        double angle = Math.atan2(mouse.y - player.getY(), mouse.x - player.getX());


        System.out.printf("Mouse local: (%d, %d), Player center: (%.2f, %.2f), Angle: %.3f\n",
                          mouse.x, mouse.y, px, py, angle);

        player.shoot(angle);
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
