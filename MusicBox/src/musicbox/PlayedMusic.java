package musicbox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class PlayedMusic extends Thread implements AutoCloseable{
  boolean stop;
  ArrayList<AtomicMusic> music;
  int idx;
  int tempo;
  int transfomation;
  MB mb;
  ClientDescriptor cd;

    public PlayedMusic(ArrayList<AtomicMusic> music, int tempo, int transfomation, int idx, MB mb, ClientDescriptor cd) {
      stop = false;
      this.music = music;
      this.tempo = tempo;
      this.transfomation = transfomation;
      this.idx = idx;
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
    
    Thread t = new Thread(() -> {
      boolean end = false;
      while(!end) {
        try{
          String[] d = cd.getLine().split(" ");
          switch(d[0]) {
            case "change":
              if(d.length == 4) {
                mb.change(Integer.parseInt(d[1]), Integer.parseInt(d[2]), Integer.parseInt(d[3]));
              }
              else {
                mb.change(Integer.parseInt(d[1]), Integer.parseInt(d[2]));
              }
              break;
            case "stop":
              mb.stop(Integer.parseInt(d[1]));
              break;
          }
        } catch(NoSuchElementException ex) {
          end = true;
        }
      }
      System.out.println("END");
    });
    t.start();
    while(it.hasNext() && !stop) {
      try {
        AtomicMusic am = (AtomicMusic)it.next();
        System.out.println(am);
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
    System.out.println("Thread number: " + Thread.getAllStackTraces().size());
  }
  
  public void stopPlaying() {
    stop = true;
  }

  @Override
  public void close() throws Exception {
    System.out.println("STOP");
    cd.close();
  }
}
