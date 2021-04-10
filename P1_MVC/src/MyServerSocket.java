import java.net.*;
import java.io.*;


public class MyServerSocket {
    
    ServerSocket socket;

    public MyServerSocket(int port){
        try{
            socket = new ServerSocket(port);
        } catch (IOException e){            
        }
    }

    public MySocket accept(){
        try{
            Socket cs = socket.accept();
            return new MySocket(cs);
        } catch (IOException e){
        return null;        
        }        
    } 
}
