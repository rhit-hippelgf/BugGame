package roomStuff;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class musicPlayer {
	public Clip shop;
	public Clip bgm;
	int volumeInt = 0;
	public Clip boss;
	public musicPlayer() {
		
		try {
         AudioInputStream a = AudioSystem.getAudioInputStream(
             new File("assets/sounds/tunnels.wav"));
         bgm = AudioSystem.getClip();
         bgm.open(a);
         AudioInputStream a2 = AudioSystem.getAudioInputStream(
        		 new File("assets/sounds/accordion.wav"));
         shop = AudioSystem.getClip();
         shop.open(a2);
         AudioInputStream a3 = AudioSystem.getAudioInputStream(
        		 new File("assets/sounds/8m.wav"));
         boss = AudioSystem.getClip();
         boss.open(a3);
         FloatControl bgmVolume = (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
         bgmVolume.setValue(-1 * volumeInt );
         FloatControl shopVolume = (FloatControl) shop.getControl(FloatControl.Type.MASTER_GAIN);
         shopVolume.setValue(-1 * volumeInt);
         FloatControl bossVolume = (FloatControl) boss.getControl(FloatControl.Type.MASTER_GAIN);
         bossVolume.setValue(-1 * volumeInt -10);

         
       } catch (Exception e) {
         System.err.println("Error playing sound: " + e.getMessage());
       }
	}
  public void playBgm() {
	  bgm.start();
  	  bgm.loop(bgm.LOOP_CONTINUOUSLY);
        
  }
  public void pauseBgm() {
      bgm.stop();
  }
  public void playShop() {
	  shop.start();
      shop.loop(bgm.LOOP_CONTINUOUSLY);
  }
public void pauseShop() {
	shop.stop();
	
}
	public void playBoss() {
	boss.start();
    boss.loop(bgm.LOOP_CONTINUOUSLY);
}
	public void pauseBoss() {
	boss.stop();
}
}
