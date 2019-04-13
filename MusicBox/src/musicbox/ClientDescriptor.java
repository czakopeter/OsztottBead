package musicbox;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientDescriptor implements AutoCloseable {
  Socket s;
  PrintWriter pw;
  Scanner sc;
  
  public ClientDescriptor(ServerSocket ss) throws Exception{
    s = ss.accept();
    pw = new PrintWriter(s.getOutputStream());
    sc = new Scanner(s.getInputStream());
    
  }
  
  public String getLine() {
      return sc.nextLine();
  }
  
  public void sendMsg(String msg) {
    pw.println(msg);
    pw.flush();
  }
  
  @Override
  public void close() throws Exception {
    s.close();
  }
}
