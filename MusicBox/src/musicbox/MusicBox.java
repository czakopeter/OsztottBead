package musicbox;

public class MusicBox {
  
  public static void main(String[] args) {
    MB musicBox = new MB();
    musicBox.addMusic("Boci", "C/1 4 B 4 R 4 A 4 G 4 C/1 4 B 4 R 4 A 4 G 4");
    musicBox.playMusic("Boci");
    for(int i=0; i<4000; i++) {
      System.out.println(i);
    }
    musicBox.addLyrics("Boci", "Bo ci ka");
  }
}
