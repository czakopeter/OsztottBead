package musicbox;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientDescriptor implements AutoCloseable {
  Socket s;
  PrintWriter pw;

  public ClientDescriptor() throws Exception{
  }
  
  public ClientDescriptor(Socket s) throws Exception{
    this.s = s;
    pw = new PrintWriter(s.getOutputStream());
  }
  
  public void send(AtomicMusic am) {
      System.out.println(am);
//    pw.print(am.getVoice() + " " + am.getSyllable());
//    pw.flush();
  }
  
  @Override
  public void close() throws Exception {
    s.close();
  }
}
