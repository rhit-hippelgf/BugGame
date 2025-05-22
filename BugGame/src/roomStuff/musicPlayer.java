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
	int volume_int = 0;
	public musicPlayer() {
		try {
         AudioInputStream a = AudioSystem.getAudioInputStream(
             new File("assets/sounds/tunnels.wav"));
         bgm = AudioSystem.getClip();
         bgm.open(a);
         FloatControl volume = (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
         
         AudioInputStream a2 = AudioSystem.getAudioInputStream(
        		 new File("assets/sounds/accordion.wav"));
         shop = AudioSystem.getClip();
         shop.open(a2);
         volume.setValue(-1 * volume_int );
         
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
}
