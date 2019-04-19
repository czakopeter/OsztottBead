package musicbox;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MusicBox {
  private static final int PORT = 40000;
  
  public static void main(String[] args) throws Exception {
    MB musicBox = new MB();
    musicBox.addMusic("Wish", "G 4 C 4 C 4 D 4 C 4 B 4 A 4 A 4 A 4 D 4 D 4 E 4 D 4 C 4 B 4 G 4");
    musicBox.addMusic("Boci", "C 4 E 4 C 4 E 4 G 8 G 8 REP 6;1 C/1 4 B 4 A 4 G 4 F 8 A 8 G 4 F 4 E 4 D 4 C 8 C 8");
    musicBox.addMusic("Pelda", "D 1 D 3 D/1 1 D/1 3 C/1 1 C/1 3 C/1 2 C/1 2 D/1 1 D/1 3 C/1 1 Bb 3 A 4 A 2 R 2 REP 15;1 Bb 4 A 2 G 2 F 1 F 3 E 2 D 2 G 2 G 2 C/1 2 Bb 2 A 4 D/1 2 R 2 C/1 1 Bb 3 A 2 G 2 G 1 A 3 G 2 F 2 A 1 G 3 F# 2 Eb 2 D 4 D 2 R 2");
    musicBox.addMusic("Fairy", "D 2 R 2 E 2 D 2 C 2 R 2 A/-1 2 R 2 G/-1 2 R 2 A/-1 2 C 2 D 2 R 2 C 2 R 2 D 2 R 2 E 2 D 2 C 2 A 2 R 2 G 2 R 2 A/-1 2 C 2 F 2 R 2 E 2 R 2 D 2 R 2 E 2 D 2 C");
    musicBox.addMusic("JOJO", "C/1 1 D#/1 1 G/1 1 F#/1 4 F/1 2 F/1 2 C/1 1 D#/1 2 F/1 2 REP 9;5");
    try(
      ServerSocket ss = new ServerSocket(PORT);
    ) {
      System.out.println("SERVER: START");
      while(true) {
        ClientDescriptor client = new ClientDescriptor(ss);
        System.out.println("SERVER: NEW CLIENT");
        String line = client.getLine();
        System.out.println(line);
        String[] d = line.split(" ");
        
        switch(d[0]) {
          case "add":
            musicBox.addMusic(d[1], client.getLine());
            break;
          case "addLyrics":
            musicBox.addLyrics(d[1], client.getLine());
            break;
          case "play":
            musicBox.playMusic(d[1],Integer.parseInt(d[2]),Integer.parseInt(d[3]),client);
            break;
          case "change":
            musicBox.change(Integer.parseInt(d[1]), Integer.parseInt(d[2]), Integer.parseInt(d[3]));
            break;
          case "stop":
            musicBox.stop(Integer.parseInt(d[1]));
            break;
          default:
            //Never happened
        }
        if(!"play".equals(d[0])) {
          client.close();
        }
      }
    } catch (IOException ex) {
      Logger.getLogger(MusicBox.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
