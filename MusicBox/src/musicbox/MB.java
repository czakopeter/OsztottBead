package musicbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MB {
  HashMap<String, ArrayList<AtomicMusic> > musicList;
  HashMap<Integer, PlayedMusic> playedMusic;
  
  static HashMap<String,Integer> voiceToCode = new HashMap<>();;

  public MB() {
    musicList = new HashMap<>();
    playedMusic = new HashMap<>();
    setTransTable();
  }

  private void addMusic(String title, String data) {
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
      if("REP".equals(s[i-1])) {
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
  
  private void stop(String tilte) {
    
  }
  
  public void stop(int number) {
    
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
}
