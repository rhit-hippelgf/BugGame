package itemStuff;

import creatureStuff.Player;

public class Item {
	protected int x, y;
	protected String statCategory;
	protected int statBoost;
	
	public Item(int startX, int startY, String statCategory, int statBoost) {
		this.x=startX;
		this.y=startY;
		this.statCategory=statCategory;
		this.statBoost=statBoost;
	}
	public String getStatCat() {
		return statCategory;
	}
	public int getStatBoost() {
		return statBoost;
	}
	
	
}
