package musicbox;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player {
  HashMap<Integer, PlayedMusic> playedMusicList;
  int number;
  Transformation t;

  public Player(Transformation t) {
    playedMusicList =  new HashMap<>();
    number = 0;
    this.t = t;
  }
  
  
  
  public void play(List<AtomicMusic> music, int tempo, int transportation, Client c, Transformation trans) {
    if(music != null) {
      c.sendMsg(String.valueOf(number));
      PlayedMusic pm = new PlayedMusic(music, tempo, transportation, trans, c);
      playedMusicList.put(number, pm);
      numberIncrease();
      pm.start();
      try {
        pm.join();
      } catch (InterruptedException ex) {
        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      c.sendMsg("FIN");
    }
  }
  
  void change(int number, int tempo, int transformation) {
    
  }
  
  void stop(int number) {
    playedMusicList.get(number).stopPlaying();
  }
  
  private void numberIncrease() {
    number++;
  }
}
