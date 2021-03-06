package musicbox;

import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MusicBox {
  private static final int PORT = 40000;
  
  public static void main(String[] args) throws Exception {
    Transformation trans = new Transformation();
    MusicStorage musicStorage = new MusicStorage();
    musicStorage.addMusic("Boci", "C 4 E 4 C 4 E 4 G 8 G 8 REP 6;1 C/1 4 B 4 A 4 G 4 F 8 A 8 G 4 F 4 E 4 D 4 C 8 C 8");
    Player player = new Player();
    try(
      ServerSocket ss = new ServerSocket(PORT);
    ) {
      System.out.println("SERVER: START");
      while(true) {
        Client client = new Client(ss);
        new Thread(() -> {
          while(client.hasNextLine()) {
            String line = client.nextLine();
            String[] ps = line.split(" ");
            switch(ps[0]) {
              case "add":
                musicStorage.addMusic(ps[1], client.nextLine());
                break;
              case "addLyrics":
                musicStorage.addLyrics(ps[1], client.nextLine());
                break;
              case "play":
                player.play(musicStorage.getMusic(ps[1]),Integer.parseInt(ps[2]),Integer.parseInt(ps[3]),client);
                break;
              case "change":
                if(ps.length == 4) {
                  player.change(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]), Integer.parseInt(ps[3]));
                } else {
                  player.change(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]), 0);
                }
                break;
              case "stop":
                player.stop(Integer.parseInt(ps[1]));
                break;
              default:
                //Never happened
            }
          }
          
          try {
            client.close();
          } catch (Exception ex) {
            Logger.getLogger(MusicBox.class.getName()).log(Level.SEVERE, null, ex);
          }
        }).start();
      }
    }
  }
}
