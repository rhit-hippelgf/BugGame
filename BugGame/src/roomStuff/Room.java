package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class Room extends JPanel{

	public boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	char[][][] layouts = {
			{
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
			}
	};
	
	public Room(boolean north, boolean east, boolean south, boolean west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
	}
	
	 @Override
	    protected void paintComponent(Graphics g) {
	      super.paintComponent(g);       
	      Graphics2D g2 = (Graphics2D) g;                 
	    }
	
	
	
	
}
