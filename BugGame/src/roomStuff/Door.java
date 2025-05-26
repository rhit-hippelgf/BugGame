package roomStuff;
/**
 * Class: Door, this class exists in every room on all sides but the boolean visable
 * allows for only doors that connect to other rooms to be visable. This door only sends
 * information to a higher class to tell it what is happening. It is also prints a skinny
 * rectangle at the base of each door creating effects for specific rooms. 
 * 
 * @author team 1
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Door {

	private Boolean visable;
	protected Boolean state;
	protected char dir;
	private int x1, y1, x2, y2;
	private Rectangle rect;
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
				this.rect = new Rectangle(x1-length/6,y1,4*length/3,10);
			} else if (dir == 'e') {
				this.x1 = 13*length - width;
				this.y1 = 3*length;
				this.x2 = x1 + width;
				this.y2 = y1 + length;
				this.rect = new Rectangle(x1+width-10,y1-length/12,10,7*length/6);
			} else if (dir == 's') {
				this.x1 = 6*length;
				this.y1 = 7*length - width;
				this.x2 = x1 + length;
				this.y2 = y1 + width;
				this.rect = new Rectangle(x1-length/6,y1+width-10,4*length/3,10);
			} else if (dir == 'w') {
				this.x1 = 0;
				this.y1 = 3*length;
				this.x2 = width;
				this.y2 = y1 + length;
				this.rect = new Rectangle(0,y1-length/12,10,7*length/6);
			}
		} else {
			this.rect = new Rectangle(0,0,0,0);
		}
	}
	
	public void openDoor() {
		if (this.visable) {
			this.state = true;
		}
	}
	
	
    public void draw(Graphics2D g) {
    	Color old = g.getBackground();
        g.setColor(color); // draw player sprite
        g.fill(rect);
//        g.fillRect(x1, y1, x2-x1, y2-y1);
        g.setColor(old);
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
