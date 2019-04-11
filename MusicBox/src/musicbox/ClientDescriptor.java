package musicbox;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientDescriptor implements AutoCloseable {
  Socket s;
  PrintWriter pw;

  public ClientDescriptor(Socket s) throws Exception{
    this.s = s;
    pw = new PrintWriter(s.getOutputStream());
  }
  
  
  @Override
  public void close() throws Exception {
    
  }
  
}