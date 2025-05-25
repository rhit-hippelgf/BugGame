package roomStuff;

import java.io.*;
import java.util.*;

public class HighScoreManager {

	private static final String FILE = "highscores.properties";
	private final Properties props = new Properties();
	private static final int MAX_SCORES = 5;
	
	public HighScoreManager() throws IOException {
		File f = new File(FILE);
		if (f.exists()) {
			try (java.io.FileReader reader = new java.io.FileReader(FILE)) {
				props.load(reader);
			}
		}
	}
	
	public List<Integer> getHighScores() {
		List<Integer> scores = new ArrayList<>();
		for (int i = 0; i < MAX_SCORES; i++) {
			String key = "highScore" + i;
			String val = props.getProperty(key, "0");
			scores.add(Integer.parseInt(val));
		}
		return scores;
	}
	
	public boolean addScore(int newScore) {
		List<Integer> scores = this.getHighScores();
		
		for (int i = 0; i < scores.size(); i++) {
			if (newScore > scores.get(i)) {
				scores.add(i, newScore);
				scores = scores.subList(0, MAX_SCORES);
				saveScores(scores);
				return true;
			}
		}
		if (scores.size() < MAX_SCORES) {
			scores.add(newScore);
			saveScores(scores);
			return true;
		}
		return false;
	}

	private void saveScores(List<Integer> scores) {
		for (int i = 0; i < scores.size(); i++) {
			props.setProperty("highScore" + i, Integer.toString(scores.get(i)));
		}
	}
	
	public void resetScores() {
		for (int i = 0; i < MAX_SCORES; i++) {
			props.setProperty("highScore" + i, "0");
		}
	}
	
	public void save() throws IOException {
		try (FileWriter writer = new FileWriter(FILE)) {
			props.store(writer, "Top " + MAX_SCORES + " High Scores");
		}
	}
	
//	This main method resets leaderboard planning to add way in game 
//	public static void main(String[] args) throws IOException {
//        HighScoreManager mgr = new HighScoreManager();
//        mgr.addScore(0);
//        mgr.save();
//        System.out.println("Current top scores: " + mgr.getHighScores());
//    }
}
