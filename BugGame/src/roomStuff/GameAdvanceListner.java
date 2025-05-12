package roomStuff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameAdvanceListner implements ActionListener {

	private RoomLogic logic;

	public GameAdvanceListner(RoomLogic logic) {
		this.logic = logic;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
	}

	public void advanceOneTick() {
		this.logic.updateObjects();
		this.logic.drawScreen();
	}
}
