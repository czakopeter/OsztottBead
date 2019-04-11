package musicbox;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

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

  public void setTempoAndTransfomation(int tempo, int transfomation) {
    this.tempo = tempo;
    this.transfomation = transfomation;
  }
  
  public void run() {
    while(it.hasNext()) {
      AtomicMusic am = (AtomicMusic)it.next();
      pw.print(am.getVoice() + " " + am.getSyllable());
      pw.flush();
    }
  }

  @Override
  public void close() throws Exception {
    s.close();
  }
}
