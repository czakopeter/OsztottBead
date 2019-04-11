package musicbox;

public class MusicBox {
  
  public static void main(String[] args) {
    MB musicBox = new MB();
    musicBox.addMusic("Boci", "C 4 E 4");
    musicBox.printMusic("Boci");
    musicBox.addMusic("Boci", "C/1 4 B 4 A 4 G 4");
    musicBox.printMusic("Boci");
  }
  
  
}
