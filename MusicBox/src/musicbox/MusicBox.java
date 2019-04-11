package musicbox;

public class MusicBox {
  
  public static void main(String[] args) {
    MB musicBox = new MB();
    musicBox.addMusic("Boci", "C/1 4 B 4 R 4 A 4 G 4");
    musicBox.addLyrics("Boci", "Bo ci ka");
    musicBox.printMusic("Boci");
  }
}
