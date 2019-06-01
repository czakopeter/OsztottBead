package musicbox;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class PlayedMusic{
  private boolean end;
  private List<AtomicMusic> music;
  private int number;
  private int tempo;
  private int transfomation;
  private Client client;

    public PlayedMusic(int number, List<AtomicMusic> music, int tempo, int transfomation, Client c) {
      end = false;
      this.music = music;
      this.tempo = tempo;
      this.transfomation = transfomation;
      this.client = c;
    }

  public synchronized void setTempoAndTransfomation(int tempo, int transfomation) {
    this.tempo = tempo;
    this.transfomation = transfomation;
  }
  
  public void play() {
    Iterator it = music.listIterator();
    
    while(it.hasNext() && !end) {
      try {
        AtomicMusic am = (AtomicMusic)it.next();
        System.out.println(am);
        synchronized(am) {
          client.sendMsg(Transformation.transform(am.getVoice(),transfomation) + " " + am.getSyllable());
        }
        TimeUnit.MILLISECONDS.sleep(am.getLength() * tempo);
      } catch (InterruptedException ex) {
        Logger.getLogger(PlayedMusic.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    client.sendMsg("FIN");
  }
  
  public synchronized void stopPlaying() {
    end = true;
  }
}
