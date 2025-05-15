package roomStuff;

import java.awt.Color;
import java.awt.Graphics;

public class Door {

	private Boolean visable;
	protected Boolean state;
	protected char dir;
	private int x1, y1, x2, y2;
	private int length, width;
	protected Color color;
	
	public Door(Boolean visable, char dir) {
		this.visable = visable;
		this.state = false;
		this.dir = dir;
		this.length = RoomLogic.getTileSize();
		this.width  = this.length/2 + 5;
		this.color = Color.black;
		if (visable) {
			if (dir == 'n') {
				this.x1 = 6*length;
				this.y1 = 0;
				this.x2 = x1 + length;
				this.y2 = width;
			} else if (dir == 'e') {
				this.x1 = 13*length - width;
				this.y1 = 3*length;
				this.x2 = x1 + width;
				this.y2 = y1 + length;
			} else if (dir == 's') {
				this.x1 = 6*length;
				this.y1 = 7*length - width;
				this.x2 = x1 + length;
				this.y2 = y1 + width;
			} else if (dir == 'w') {
				this.x1 = 0;
				this.y1 = 3*length;
				this.x2 = width;
				this.y2 = y1 + length;
			}
		}
	}
	
	public void openDoor() {
		if (this.visable) {
			this.state = true;
		}
	}
	
	
    public void draw(Graphics g) {
        g.setColor(color); // draw player sprite
        g.fillRect(x1, y1, x2-x1, y2-y1);
	}
    
    public boolean getVisable() {
    	return visable;
    }
    
    public char playerHitDoor(int x, int y) {
    	if (state == true) {
    		if (x1 < x && x < x2 && y1 < y && y < y2) {
//    			System.out.println("yes");
    			return this.dir;
    		} else {
    			return ' ';
    		}
    	} else {
    		return ' ';
    	}
    }
    
    public void setColor(Color color) {
    	this.color = color;
    }
}
