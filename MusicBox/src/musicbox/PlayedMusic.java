package musicbox;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class PlayedMusic extends Thread implements AutoCloseable{
  Socket s;
  PrintWriter pw;
  ArrayList<AtomicMusic> music;
  int idx;
  int tempo;
  int transfomation;
  boolean stop;
  MB mb;
  ClientDescriptor cd;

    public PlayedMusic(ArrayList<AtomicMusic> music, int tempo, int transfomation, int idx, MB mb, ClientDescriptor cd) {
      this.music = music;
      this.tempo = tempo;
      this.transfomation = transfomation;
      this.idx = idx;
      stop = false;
      this.mb = mb;
      this.cd = cd;
    }

  public void setTempoAndTransfomation(int tempo, int transfomation) {
    this.tempo = tempo;
    this.transfomation = transfomation;
  }
  
  @Override
  public void run() {
    Iterator it = music.listIterator();
    while(it.hasNext() && !stop) {
      try {
        AtomicMusic am = (AtomicMusic)it.next();
        System.out.println(transfomation);
        cd.sendMsg((am.getVoice()+transfomation) + " " + am.getSyllable());
        TimeUnit.MILLISECONDS.sleep(am.getLength() * tempo);
      } catch (InterruptedException ex) {
        Logger.getLogger(PlayedMusic.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    if(!stop) {
      mb.stop(idx);
    }
    cd.sendMsg("FIN");
  }
  
  public void stopPlaying() {
    stop = true;
  }

  @Override
  public void close() throws Exception {
    s.close();
  }
}
