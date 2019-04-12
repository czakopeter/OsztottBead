package musicbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MB {
  private final HashMap<String, ArrayList<AtomicMusic> > musicList;
  private final HashMap<Integer, PlayedMusic> playedMusic;
  
  private final static HashMap<String,Integer> voiceToCode = new HashMap<>();;

  public MB() {
    musicList = new HashMap<>();
    playedMusic = new HashMap<>();
    setTransTable();
  }

  public void addMusic(String title, String data) {
    ArrayList<AtomicMusic> music = new ArrayList<>();
    if(musicList.containsKey(title)) {
      stop(title);
    }
    setMusic(music, data);
    musicList.put(title, music);
  }
  
  private void setMusic(ArrayList<AtomicMusic> music, String data) {
    String[] s = data.split(" "); 
    for(int i=1; i<s.length; i+=2) {
      if("R".equals(s[i-1])) {
        music.add(new AtomicMusic(-1, Integer.parseInt(s[i])));
      } else if("REP".equals(s[i-1])) {
        String[] rep = s[i].split(";");
        music.addAll(
                createRepeate(
                        createCopyPart(music,Integer.parseInt(rep[0]),music.size()),
                        Integer.parseInt(rep[1])));
      } else {
        music.add(new AtomicMusic(transform(s[i-1]), Integer.parseInt(s[i])));
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
  
  public void playMusic(String title) {
    PlayedMusic pm = new PlayedMusic(musicList.get(title).listIterator(), 0, 0);
    pm.start();
  }
  
  private void stop(String tilte) {
    System.out.println("STOP BY TITLE");
  }
  
  public void stop(int number) {
    System.out.println("STOP BY NUMBER");
  }

  private static void setTransTable() {
    try(
      Scanner sc = new Scanner(new File("voiceToCode.txt"));
    ) {
      while(sc.hasNextLine()) {
        String[] line = sc.nextLine().split(";");
        voiceToCode.put(line[0], Integer.parseInt(line[1]));
      }
    } catch (FileNotFoundException e) {System.err.println("File not found");}
  }
  
  private static int transform(String voice) {
    String[] v = voice.split("/");
    if(v.length > 1) {
      return voiceToCode.get(v[0]) + 12*Integer.parseInt(v[1]);
    }
    return voiceToCode.get(v[0]);
  }
  
  public void printMusic(String title) {
    System.out.println(title);
    musicList.get(title).forEach((m) -> {
      System.out.println("\t" + m.toString());
    });
  }
}
