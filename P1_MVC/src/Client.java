import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Client{

    public static void main(String[] args){
        MySocket s = new MySocket(args[0], Integer.parseInt(args[1]));
        //Input thread
        new Thread(){
            public void run(){
                String line;
                BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
                try{
                    while((line = kbd.readLine()) != null)
                        s.write_str(line);
                    s.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
                
            }
        }.start();

        //Output thread
        new Thread(){
            public void run(){
                String line;
                while((line = s.read_str()) != null)
                    s.write_str(line);
            }
        }.start();
    }
}
