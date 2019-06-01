package musicbox;

class AtomicMusic {
  private String voice;
  private int length;
  private String syllable;

  public AtomicMusic(String voice, int length) {
    this.voice = voice;
    this.length = length;
    if(!"R".equals(voice)) {
      this.syllable = "???";
    }
  }

  public String getVoice() {
    return voice;
  }

  public int getLength() {
    return length;
  }
  
  public String getSyllable() {
    return syllable;
  }
  
  public synchronized boolean setSyllable(String syllable) {
    if("R".equals(voice)) {
      return false;
    }
    this.syllable = syllable;
    return true;
  }
  
  public AtomicMusic copy() {
    AtomicMusic c = new AtomicMusic(voice, length);
    c.setSyllable(syllable);
    return c;
  }

  @Override
  public String toString() {
    return "AtomicMusic{" + "voice=" + voice + ", length=" + length + ", syllable=" + syllable + '}';
  }
}