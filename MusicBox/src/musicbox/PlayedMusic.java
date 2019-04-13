package musicbox;

import java.io.IOException;
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

  public PlayedMusic(Socket s, int tempo, int transfomation) throws IOException {
    this.s = s;
    pw = new PrintWriter(s.getOutputStream());
    this.tempo = tempo;
    this.transfomation = transfomation;
  }

    public PlayedMusic(ArrayList<AtomicMusic> music, int tempo, int transfomation, MB mb) {
      this.music = new ArrayList<>(music);
      this.tempo = tempo;
      this.transfomation = transfomation;
      idx = 0;
      stop = false;
      this.mb = mb;
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
//      pw.print(am.getVoice() + " " + am.getSyllable());
//      pw.flush();
        System.out.println(it.next());
        TimeUnit.MILLISECONDS.sleep(tempo);
      } catch (InterruptedException ex) {
        Logger.getLogger(PlayedMusic.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    mb.stop(idx);
    System.out.println("FIN");
  }
  
  public void stopPlaying() {
    stop = true;
  }

  @Override
  public void close() throws Exception {
    s.close();
  }
}
