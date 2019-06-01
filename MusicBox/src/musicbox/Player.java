package musicbox;

import java.util.HashMap;
import java.util.List;

public class Player {
  private final HashMap<Integer, PlayedMusic> playedMusicList;
  private int number;

  public Player() {
    playedMusicList =  new HashMap<>();
    number = 0;
  }
  
  public void play(List<AtomicMusic> music, int tempo, int transportation, Client c) {
    if(music != null) {
      int nr = getNextNumber();
      c.sendMsg("playing " + nr);
      PlayedMusic pm = new PlayedMusic(nr, music, tempo, transportation, c);
      synchronized(playedMusicList) {
        playedMusicList.put(nr, pm);
      }
      pm.play();
      synchronized(playedMusicList) {
        playedMusicList.remove(nr);
      }
    } else {
      c.sendMsg("FIN");
    }
  }
  
  synchronized void change(int number, int tempo, int transformation) {
    if(playedMusicList.containsKey(number)){
      System.out.println("tempo: " + tempo + ", trans: " + transformation);
      playedMusicList.get(number).setTempoAndTransfomation(tempo, transformation);
    }
  }
  
  void stop(int number) {
    if(playedMusicList.containsKey(number)) {
      playedMusicList.get(number).stopPlaying();
    }
  }
  
  private synchronized int getNextNumber() {
    number++;
    return number;
  }
}
