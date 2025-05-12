package controllerStuff;

import javax.swing.*;

import creatureStuff.Player;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class Controller implements KeyListener, MouseListener {
    private final Player player;
    private final Set<Integer> kPress = new HashSet<>();
    private final JComponent component;
    

    public Controller(JComponent component, Player player) {
        this.player = player;
		this.component = component;
		
		component.addKeyListener(this);
		component.setFocusable(true);
		component.requestFocusInWindow();
	
//		
//        int scope = JComponent.WHEN_IN_FOCUSED_WINDOW;
//        InputMap im = component.getInputMap(scope);
//        ActionMap am = component.getActionMap();
//
//        bind(im, am, "W", Math.PI / 2);
//        bind(im, am, "A", Math.PI);
//        bind(im, am, "S", 3 * Math.PI / 2);
//        bind(im, am, "D", 0);
    }
    
    public void moveIfPress() {
    	boolean w = kPress.contains(KeyEvent.VK_W);
    	boolean a = kPress.contains(KeyEvent.VK_A);
    	boolean s = kPress.contains(KeyEvent.VK_S);
    	boolean d = kPress.contains(KeyEvent.VK_D);
    	
    	Double theta = null;
    	
    	if (w && d) theta = Math.PI / 4;
    	else if (w && a) theta = 3 * Math.PI/4; 
    	else if (s && d) theta = 7 * Math.PI/4;
    	else if (s && a) theta = 5 * Math.PI/4;
    	else if (w) theta = Math.PI/2;
    	else if (a) theta = Math.PI;
    	else if (s) theta = 3 * Math.PI/2;
    	else if (d) theta = 0.0;
    	
    	if (theta != null) {
    		System.out.println("check2");
    		player.move(theta);
    		component.repaint(); 
    	}
    }

//    private void bind(InputMap im, ActionMap am, String key, double theta) {
//        im.put(KeyStroke.getKeyStroke(key), key);
//        am.put(key, new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            	player.move(theta);
//            	System.out.println("Moved to: " + player.getX() + " , " + player.getY());
//                
//            }
//        });
//    }

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		kPress.add(e.getKeyCode());

		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	       kPress.remove(e.getKeyCode());

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		int playerX = player.getX() + 10;
		int playerY = player.getY() + 10;
		
		double dx = mouseX - playerX;
		double dy = mouseY - playerY;
		double angle = Math.atan2(dy,dx);
		
		System.out.println("Shooting at angle (rad): " + angle);
		
		player.shoot(angle);
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// Needed to make another push and another
}
