package handlingStuff;
import java.util.Random;

public class RngHandler {
	private Random r = new Random();
	
	public boolean handleCheck(int criteria) {
		boolean pass = false;
		int check = r.nextInt(100);
		
		if (criteria>= check) pass = true;
		return pass;
	}
}
