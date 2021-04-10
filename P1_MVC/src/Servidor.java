import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class Servidor{
    /*MyServerSocket servidor = null;
    MySocket s = null;
    InputStreamReader print = null;
    BufferedReader brinput = null;*/

    public static void main(String[] args){        
        MyServerSocket ss = new MyServerSocket(/*PORT*/Integer.parseInt(args[0]));
        MySocket sockClient = ss.accept();
        //Input
        new Thread(){
            public void run(){
                BufferedReader buffIn = sockClient.in;
                String line;
                try{
                    while((line = buffIn.readLine()) != null){
                        System.out.println("Client: " + line);
                    }
                    sockClient.close();
                } catch (IOException e){                    
                }
            }
        }.start();

        //Output
        new Thread(){
            public void run(){
                BufferedReader buffOut = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while((line = sockClient.read_str()) != null){
                    sockClient.write_str(line);
                }
            }
        }.start();
    }
}
