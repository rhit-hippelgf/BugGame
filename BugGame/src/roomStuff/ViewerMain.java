package roomStuff;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ViewerMain {
	
	public static void createGUI() {
		JFrame frame = new JFrame("Viewer");
		
		final int SCREEN_WIDTH = 1900;
		final int SCREEN_HEIGHT = 1080;
		// Uncomment for Lab part 2
//		frame.setLayout(new java.awt.BorderLayout());
//		MyCanvas canvas = new MyCanvas();
//		frame.add(canvas, BorderLayout.CENTER);
//		ControlPanel control = new ControlPanel(canvas, frame);
//		frame.add(control, BorderLayout.SOUTH);


		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setLocationRelativeTo(null); // 2) center on screen
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.requestFocusInWindow(); // 3) allow frame-level listener see the keys
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> createGUI());
	}

}
