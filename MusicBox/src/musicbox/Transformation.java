package musicbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Transformation {
  private static HashMap<String, Integer> voiceToCode;
  private static HashMap<Integer, String> codeToVoice;

  public Transformation() {
    voiceToCode = new HashMap<>();
    codeToVoice = new HashMap<>();
    setTables();
  }
  
  private void setTables() {
    try(
      Scanner sc = new Scanner(new File("voiceToCode.txt"));
    ) {
      while(sc.hasNextLine()) {
        String[] line = sc.nextLine().split(";");
        String voice = line[0];
        int code = Integer.parseInt(line[1]);
        voiceToCode.put(voice, code);
        codeToVoice.put(code, voice);
      }
    } catch (FileNotFoundException e) {System.err.println("File not found");}
  }

  public static String transform(String voice, int transformation) {
    return tCodeToVoice(tVoiceToCode(voice)+transformation);
  }
  
  public static int tVoiceToCode(String voice) {
    String[] v = voice.split("/");
    if(v.length > 1) {
      return voiceToCode.get(v[0]) + 12*Integer.parseInt(v[1]);
    }
    return voiceToCode.get(v[0]);
  }
  
  public static String tCodeToVoice(int code) {
    StringBuilder sb = new StringBuilder(codeToVoice.get(code+code%12));
    sb.append("/").append(code/12-6);
    return sb.toString();
  }
}
