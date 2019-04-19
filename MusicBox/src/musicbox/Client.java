package musicbox;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client implements AutoCloseable {
  private Socket s;
  private PrintWriter pw;
  private Scanner sc;
  
  public Client(ServerSocket ss) throws Exception{
    s = ss.accept();
    pw = new PrintWriter(s.getOutputStream());
    sc = new Scanner(s.getInputStream());
    
  }
  
  public String nextLine() {
    return sc.nextLine();
  }
  
  public boolean hasNextLine() {
    return sc.hasNextLine();
  }
  
  public void sendMsg(String msg) {
    pw.println(msg);
    pw.flush();
  }
  
  @Override
  public void close() throws Exception {
    if(s == null) {
      return;
    }
    s.close();
  }
}
