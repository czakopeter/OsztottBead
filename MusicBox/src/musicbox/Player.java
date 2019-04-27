package musicbox;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player {
  HashMap<Integer, PlayedMusic> playedMusicList;
  int number;

  public Player() {
    playedMusicList =  new HashMap<>();
    number = 0;
  }
  
  public void play(List<AtomicMusic> music, int tempo, int transportation, Client c) {
    if(music != null) {
      c.sendMsg("playing " + number);
      PlayedMusic pm = new PlayedMusic(number, music, tempo, transportation, c, this);
      playedMusicList.put(number, pm);
      numberIncrease();
      pm.start();
    } else {
      c.sendMsg("FIN");
    }
  }
  
  void change(int number, int tempo, int transformation) {
    if(playedMusicList.containsKey(number)){
      System.out.println("tempo: " + tempo + ", trans: " + transformation);
      playedMusicList.get(number).setTempoAndTransfomation(tempo, transformation);
    }
  }
  
  void stop(int number) {
    if(playedMusicList.containsKey(number)) {
      playedMusicList.get(number).stopPlaying();
      playedMusicList.remove(number);
    }
  }
  
  private void numberIncrease() {
    number++;
  }
}
