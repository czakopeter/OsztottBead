package musicbox;

public class MusicBox {
  
  public static void main(String[] args) {
    MB musicBox = new MB();
    musicBox.addMusic("Boci", "C 4 C 4 C 4 C 4 C 4 C 4 C 4 C 4 C 4 C 4");
    musicBox.playMusic("Boci");
    musicBox.addLyrics("Boci", "Bo ci Bo ci tar ka");
    for(int i=0; i<5000; i++) {
    }
    System.out.println("new voices start");
    musicBox.addMusic("Boci", "C# 4 C# 4 C# 4 C# 4 C# 4 C# 4 C# 4 C# 4 C# 4 C# 4");
    System.out.println("new voices end");
    musicBox.playMusic("Boci");
  }
}
