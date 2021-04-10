import java.net.*;
import java.io.*;
import java.util.*;

public class MySocket{
    public Socket socket;
    BufferedReader in;
    PrintWriter out;

    public MySocket(String host, int port){
        try{
            socket = new Socket(host, port);
            init_streams();
        } catch (IOException e){            
        }
    }

    MySocket(Socket s){
        socket = s;
        init_streams();
    }

    private void init_streams(){
        try{
            in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()
            ));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e){            
        }
    }

    public String read_str(){
        try{
            return in.readLine();
        } catch(IOException e){
            //util.abort(e);
            return null;
        }
    }

    public int read_int(){
        return Integer.parseInt(read_str());
    }

    public boolean read_boolean(){
        return Boolean.parseBoolean(read_str());
    }

    public char read_char(){
        return read_str().charAt(0);
    }

    public void write_str(String s){
        out.println(s);
    }

    public void write_int(int i){
        write_str(Integer.toString(i));
    }

    public void write_boolean(boolean b){
        write_str(Boolean.toString(b));
    }

    public void write_char(char c){
        write_str(new Character(c).toString());
    }

    public void close(){
        try{
            in.close();
            out.close();
            socket.close();
        } catch (IOException e){            
        }
    }
}