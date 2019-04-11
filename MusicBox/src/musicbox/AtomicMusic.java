package musicbox;

class AtomicMusic {
  private int voice;
  private int length;
  private String syllable;

  public AtomicMusic(int voice, int length) {
    this.voice = voice;
    this.length = length;
    if(voice != -1) {
      this.syllable = "???";
    }
  }

  public int getVoice() {
    return voice;
  }

  public int getLength() {
    return length;
  }
  
  public String getSyllable() {
    return syllable;
  }

  public void setVoiceAndLength(int voice, int length) {
    this.voice = voice;
    this.length = length;
  }

  
  public void setSyllable(String syllable) {
    this.syllable = syllable;
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
