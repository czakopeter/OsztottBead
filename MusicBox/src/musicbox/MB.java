package musicbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MB {
  private final HashMap<String, ArrayList<AtomicMusic> > musicList;
  private final HashMap<Integer, PlayedMusic> playedMusicList;
  
  private final static HashMap<String,Integer> VOICE_TO_CODE = new HashMap<>();;
  int number;

  public MB() {
    musicList = new HashMap<>();
    playedMusicList = new HashMap<>();
    setTransTable();
    number = 0;
  }

  public void addMusic(String title, String data) {
    ArrayList<AtomicMusic> music = new ArrayList<>();
    setMusic(music, data);
    musicList.put(title, music);
  }
  
  private void setMusic(ArrayList<AtomicMusic> music, String data) {
    String[] s = data.split(" "); 
    for(int i=1; i<s.length; i+=2) {
      switch (s[i-1]) {
        case "R":
          music.add(new AtomicMusic(-1, Integer.parseInt(s[i])));
          break;
        case "REP":
          String[] rep = s[i].split(";");
          music.addAll(
                  createRepeate(
                          createCopyPart(music,music.size()-Integer.parseInt(rep[0]),music.size()),
                          Integer.parseInt(rep[1])));
          break;
        default:
          music.add(new AtomicMusic(transform(s[i-1]), Integer.parseInt(s[i])));
          break;
      }
    }
  }

  private List<AtomicMusic> createCopyPart(ArrayList<AtomicMusic> music, int from, int to) {
    List<AtomicMusic> copy = new ArrayList<>();
    music.subList(from, to).forEach((m) -> {
      copy.add(m.copy());
    });
    
    return copy;
  }
  
  private List<AtomicMusic> createRepeate(List<AtomicMusic> part, int repeatNumber) {
    if(repeatNumber != 1) {
      List<AtomicMusic> result = new ArrayList<>();
      for(int r=0; r<repeatNumber; r++) {
        part.forEach((m)-> {
          result.add(m.copy());
        });
      }
      return result;
    }
    return part;
  }
  
  public void addLyrics(String title, String lyrics) {
    if(musicList.get(title) != null) {
      int i = 0;
      String[] syls = lyrics.split(" ");
      boolean hasSyl = i<syls.length;
      for(AtomicMusic m : musicList.get(title)) {
        if(hasSyl && m.getVoice() != -1) {
          m.setSyllable(syls[i]);
          i++;
          hasSyl = i<syls.length;
        } else if(!hasSyl && m.getVoice() != -1) {
          m.setSyllable("???");
        }
      }
    }
  }
  
  public void playMusic(String title, int tempo, int trans, ClientDescriptor cd) throws Exception {
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
