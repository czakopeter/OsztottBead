package musicbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicStorage {
  private final Map<String, ArrayList<AtomicMusic> > musicList;

  public MusicStorage(Transformation t) {
    musicList = new HashMap<>();
  }
  
  public void addMusic(String title, String voices) {
    ArrayList<AtomicMusic> music = generateMusic(voices.split(" "));
    musicList.put(title, music);
  }
  
  public void addLyrics(String title, String lyrics) {
    if(musicList.get(title) != null) {
      int i = 0;
      String[] syls = lyrics.split(" ");
      boolean sylEnd = i<syls.length;
      for(AtomicMusic m : musicList.get(title)) {
        if(sylEnd && m.setSyllable(syls[i])) {
            i++;
            sylEnd = i<syls.length;
        } else if(!sylEnd) {
          m.setSyllable("???");
        }
      }
    }
  }
  
  public ArrayList<AtomicMusic> getMusic(String title) {
    return musicList.get(title);
  }

  private ArrayList<AtomicMusic> generateMusic(String[] voices) {
    ArrayList<AtomicMusic> result = new ArrayList<>();
    
    for(int i=1; i<voices.length; i+=2) {
      switch (voices[i-1]) {
        case "REP":
          String[] rep = voices[i].split(";");
          result.addAll(
                  createRepeate(
                          createCopy(
                                  result,
                                  result.size()-Integer.parseInt(rep[0]),
                                  result.size()),
                          Integer.parseInt(rep[1])));
          break;
        default:
          result.add(new AtomicMusic(voices[i-1], Integer.parseInt(voices[i])));
          break;
      }
    }
    return result;
  }
  
  private List<AtomicMusic> createRepeate(List<AtomicMusic> part, int repeatNr) {
    if(repeatNr > 1) {
      List<AtomicMusic> result = new ArrayList<>();
      for(int r=0; r<repeatNr; r++) {
        part.forEach((m)-> {
          result.add(m.copy());
        });
      }
      return result;
    }
    return part;
  }
  
  private List<AtomicMusic> createCopy(List<AtomicMusic> music, int from, int to) {
    List<AtomicMusic> copy = new ArrayList<>();
    music.subList(from, to).forEach((m) -> {
      copy.add(m.copy());
    });
    return copy;
  }
}
