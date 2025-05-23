package roomStuff;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.nio.file.Path;

/**
 * @author carioltl
 * This file reads from all of the text files from the file called RoomTextFiles 
 * The text files contain room layouts and this class picks a random layout
 * out of possible layouts. It also handles conditions like if the room is special
 * This should be created in each individual room not as a field but a parameter 
 * created in the constructor of Room, then use the getLayout method to get the
 * char[][] layout that the room reads to add objects to itself
 */

public class FileReader {
	
	private static final int ROWS = 7;
	private static final int COLS = 13;
	private char[][] pickedLayout;
	ArrayList<char[][]> layouts = new ArrayList<>();
	private boolean north, east, south, west;
	private int level;
	
	public FileReader(boolean north, boolean east, boolean south, boolean west, boolean bossRoom, boolean itemRoom, boolean shopRoom, boolean TutorialRoom, boolean BlankRoom, int level) {
		this.north = north;
		this.east = south;
		this.south = south;
		this.west = west;
		this.level = level;
		Random rand = new Random();
		if (bossRoom) {
			try {
				this.loadLayouts("BossLayouts");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (itemRoom) {
			try {
				this.loadLayouts("ItemLayouts");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (shopRoom) {
			try {
				this.loadLayouts("ShopLayouts");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (TutorialRoom) {
			try {
				this.loadLayouts("TutorialLayout");
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} else if (BlankRoom) {
			try {
				this.loadLayouts("BlankLayout");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.loadLayouts("GenericLayouts");
			} catch (IOException e) {
				e.printStackTrace();
//			}
//			if (!north) {
//				try {
//					this.loadLayouts("NotNorthLayouts");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (!east) {
//				try {
//					this.loadLayouts("NotEastLayouts");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (!south) {
//				try {
//					this.loadLayouts("NotSouthLayouts");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (!west) {
//				try {
//					this.loadLayouts("NotWestLayouts");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (north && !east && !south && !west) {
//				try {
//					this.loadLayouts("OnlyNorthLayouts");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (!north && east && !south && !west) {
//				try {
//					this.loadLayouts("OnlyEastLayouts");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (!north && !east && south && !west) {
//				try {
//					this.loadLayouts("OnlySouthLayouts");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (!north && !east && !south && west) {
//				try {
//					this.loadLayouts("OnlyWestLayouts");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}
		int choice = rand.nextInt(layouts.size());
		pickedLayout = layouts.get(choice);
	}
	
	private void loadLayouts(String filename) throws IOException {
		List<String> allLines = Files.readAllLines(Path.of("src","RoomTextFiles",filename + ".txt"));
		
		ArrayList<String> individualLayout = new ArrayList<>();
//		ArrayList<char[][]> charLayouts = new ArrayList<>();
		
		for (String line : allLines) {
			if (line.isEmpty()) {
				if (!individualLayout.isEmpty()) {
					layouts.add(layoutToGrid(individualLayout));
					individualLayout.clear();
				}
			} else {
				individualLayout.add(line);
			}
		}
		
		if (!individualLayout.isEmpty()) {
			layouts.add(layoutToGrid(individualLayout));
		}
		
	}

	private char[][] layoutToGrid(ArrayList<String> layout) {
		// TODO Auto-generated method stub
		int rows = layout.size();
		if (rows != 7) {
			throw new IllegalArgumentException(
					"Wrong number of rows expected 7 but got " + rows);
		}
		int cols = layout.get(0).length();
		if (cols != 13) {
			throw new IllegalArgumentException(
					"Wrong number of cols expected 13 but got " + cols);
		}
		char[][] grid = new char[rows][cols];
		for (int i = 0; i < rows; i++) {
			String line = layout.get(i);
			if (line.length() != cols) {
				throw new IllegalArgumentException(
						"Inconsistent line length expected 13 but got " + line.length());
			}
			grid[i] = line.toCharArray();
		}
		return grid;
	}

	public char[][] getLayout() {
		return this.pickedLayout;
	}
	
	
}
