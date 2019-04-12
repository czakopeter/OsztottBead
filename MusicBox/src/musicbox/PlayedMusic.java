package musicbox;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class PlayedMusic extends Thread implements AutoCloseable{
  Socket s;
  PrintWriter pw;
  Iterator it;
  int tempo;
  int transfomation;

  public PlayedMusic(Socket s, Iterator it, int tempo, int transfomation) throws IOException {
    this.s = s;
    pw = new PrintWriter(s.getOutputStream());
    this.it = it;
    this.tempo = tempo;
    this.transfomation = transfomation;
  }

    public PlayedMusic(Iterator it, int tempo, int transfomation) {
        this.it = it;
        this.tempo = tempo;
        this.transfomation = transfomation;
    }

  public void setTempoAndTransfomation(int tempo, int transfomation) {
    this.tempo = tempo;
    this.transfomation = transfomation;
  }
  
  public void run() {
    while(it.hasNext()) {
        try {
          AtomicMusic am = (AtomicMusic)it.next();
//      pw.print(am.getVoice() + " " + am.getSyllable());
//      pw.flush();
          System.out.println(am);
          TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException ex) {
          Logger.getLogger(PlayedMusic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  }

  @Override
  public void close() throws Exception {
    s.close();
  }
}
