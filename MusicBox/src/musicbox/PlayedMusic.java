package musicbox;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class PlayedMusic extends Thread implements AutoCloseable{
  boolean end;
  List<AtomicMusic> music;
  int idx;
  int tempo;
  int transfomation;
  Client client;
  Transformation trans;

    public PlayedMusic(List<AtomicMusic> music, int tempo, int transfomation, Transformation trans, Client c) {
      end = false;
      this.music = music;
      this.tempo = tempo;
      this.transfomation = transfomation;
      this.client = c;
      this.trans = trans;
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
        client.sendMsg(trans.transform(am.getVoice(),transfomation) + " " + am.getSyllable());
        TimeUnit.MILLISECONDS.sleep(am.getLength() * tempo);
      } catch (InterruptedException ex) {
        Logger.getLogger(PlayedMusic.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    if(!end) {
      mb.stop(idx);
    }
    cd.sendMsg("FIN");
    System.out.println("Thread number: " + Thread.getAllStackTraces().size());
  }
  
  public void stopPlaying() {
    end = true;
  }

  @Override
  public void close() throws Exception {
    end = true;
  }
}
