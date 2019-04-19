package musicbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MB {
  
  
  
  private final static HashMap<String,Integer> VOICE_TO_CODE = new HashMap<>();;
  int number;

  public MB() {
    musicList = new HashMap<>();
    playedMusicList = new HashMap<>();
    setTransTable();
    number = 0;
  }
  
  

  
  
  
  
  
  public void playMusic(String title, int tempo, int trans, Client cd) throws Exception {
    if(musicList.containsKey(title)) {
      cd.sendMsg(String.valueOf(number));
      PlayedMusic pm = new PlayedMusic(musicList.get(title), tempo, trans, number, this, cd);
      pm.start();
      playedMusicList.put(number, pm);
      numberIncrease();
    } else {
      cd.sendMsg("FIN");
      cd.close();
    }
  }
  
  public void change(int number, int tempo, int transfomation) {
    if(playedMusicList.containsKey(number)){
      System.out.println("tempo: " + tempo + ", trans: " + transfomation);
      playedMusicList.get(number).setTempoAndTransfomation(tempo, transfomation);
    }
  }
  
  public void change(int number, int tempo) {
    change(number, tempo, 0);
  }
  
  public void stop(int number) {
    if(playedMusicList.containsKey(number)) {
      playedMusicList.get(number).stopPlaying();
      playedMusicList.remove(number);
    }
  }

  private static void setTransTable() {
    try(
      Scanner sc = new Scanner(new File("voiceToCode.txt"));
    ) {
      while(sc.hasNextLine()) {
        String[] line = sc.nextLine().split(";");
        VOICE_TO_CODE.put(line[0], Integer.parseInt(line[1]));
      }
    } catch (FileNotFoundException e) {System.err.println("File not found");}
  }
  
  private static int transform(String voice) {
    String[] v = voice.split("/");
    if(v.length > 1) {
      return VOICE_TO_CODE.get(v[0]) + 12*Integer.parseInt(v[1]);
    }
    return VOICE_TO_CODE.get(v[0]);
  }
  
  void numberIncrease() {
    number++;
  }
}
