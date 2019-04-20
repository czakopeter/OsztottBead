package musicbox;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class PlayedMusic extends Thread implements AutoCloseable{
  boolean end;
  List<AtomicMusic> music;
  int number;
  int tempo;
  int transfomation;
  Client client;
  Player player;

    public PlayedMusic(int number, List<AtomicMusic> music, int tempo, int transfomation, Client c, Player player) {
      end = false;
      this.music = music;
      this.tempo = tempo;
      this.transfomation = transfomation;
      this.client = c;
      this.player = player;
    }

  public void setTempoAndTransfomation(int tempo, int transfomation) {
    this.tempo = tempo;
    this.transfomation = transfomation;
  }
  
  @Override
  public void run() {
    Iterator it = music.listIterator();
    
    while(it.hasNext() && !end) {
      try {
        AtomicMusic am = (AtomicMusic)it.next();
        System.out.println(am);
        client.sendMsg(Transformation.transform(am.getVoice(),transfomation) + " " + am.getSyllable());
        TimeUnit.MILLISECONDS.sleep(am.getLength() * tempo);
      } catch (InterruptedException ex) {
        Logger.getLogger(PlayedMusic.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    client.sendMsg("FIN");
    if(!end) {
      player.stop(number);
    }
  }
  
  public void stopPlaying() {
    end = true;
  }

  @Override
  public void close() throws Exception {
    end = true;
  }
}
