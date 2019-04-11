package musicbox;

import java.util.Iterator;

class PlayedMusic {
  Iterator it;
  int tempo;
  int transfomation;

  public PlayedMusic(Iterator it, int tempo, int transfomation) {
    this.it = it;
    this.tempo = tempo;
    this.transfomation = transfomation;
  }

  public void setTempoAndTransfomation(int tempo, int transfomation) {
    this.tempo = tempo;
    this.transfomation = transfomation;
  }
}
