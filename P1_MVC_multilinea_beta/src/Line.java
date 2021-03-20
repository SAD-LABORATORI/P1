import java.io.*;
import java.util.*;


public class Line extends Observable {

    public static final int TERMINAL = 80;

    StringBuffer buffer;
    public int [][] posicions={{0,0},{0,0},{0,0}};
    int cursor;
    boolean state = true; //true es modo inserción (normal)
    //false es modo sobre-escritura

    public Line() {
        buffer = new StringBuffer(1000);
    }

    public int getLength() {
        return buffer.length();
    }

    public void moveRight(){
        if (this.posicions[1][0]!=this.TERMINAL){
            this.posicions[1][0]++;
            setChanged();
            notifyObservers(Key.RIGHT);
        }else if(this.posicions[1][0]==this.TERMINAL){
            this.posicions[1][1]++;
            this.novalinea();
        }
    }

    public void moveLeft() {
        if (this.posicions[1][0] > 0) {
            this.posicions[1][0]--;
            setChanged();
            notifyObservers(Key.LEFT);
        }else{
            if(this.posicions[1][1]!=0) {
                this.suprimirlinea();    
            }
        }
    }

    public void moveHome() {
        setChanged();
        notifyObservers(Key.HOME);
        this.posicions[1][0] = 0;
    }

    public void moveEnd() {
        setChanged();
        notifyObservers(Key.END);
        this.posicions[1][0]=this.TERMINAL;
    }

    public void suprChar() {
        /*
        if (this.posicions[1][0] == longitud linea) {
            return;
        }
        this.buffer = buffer.deleteCharAt(this.posicions[1][0]);
        setChanged();
        notifyObservers(Key.SUPR);
        */
    }

    public void insChar() {

        this.state = !state;

        if (this.state) {
            setChanged();
            notifyObservers(Key.INS);

        } else {
            setChanged();
            notifyObservers(Key.OVERWRITE);
        }
        //setChanged();
        //notifyObservers(new Console.Command(Console.Accion.INS));
        //notifyObservers(Key.INS);
    }

    public void delChar() {

        if (this.buffer.length() == 0) {
            return;
        }
        this.buffer = buffer.deleteCharAt(cursor - 1);
        this.cursor--;
        setChanged();
        //notifyObservers(new Console.Command(Console.Accion.DEL));
        notifyObservers(Key.DEL);
    }

    public void addChar(char c) {
        if(this.posicions[1][0]==this.TERMINAL){
            this.novalinea();
        }else if (!this.state && (this.posicions[1][0] != this.buffer.length())) {
            this.posicions[1][0]++; 
            this.delChar();
        }
        this.buffer = buffer.insert((this.posicions[1][1]*80+this.posicions[1][0]), c);
        this.posicions[1][0]++;
        setChanged();
        notifyObservers((int) c);
    }
    public void novalinea(){
        setChanged();
        notifyObservers(Key.NOVA_LINEA);
        this.moveHome();
        this.posicions[1][1]++;
        
    }

    public void suprimirlinea(){
        setChanged();
        notifyObservers(Key.SUPR_LINEA);
        this.moveEnd();
        this.posicions[1][1]--;
        
    }
    public void baixarlinea(){
        setChanged();
        notifyObservers(Key.NOVA_LINEA);
        this.posicions[1][0]=0;
        this.posicions[1][1]++;
    }
    public void pujarlinea(){
        setChanged();
        notifyObservers(Key.SUPR_LINEA);
        this.posicions[1][0]=0;
        this.posicions[1][1]--;
        
    }
    
    public String getBuffer() {
        return this.buffer.toString();

    }

    public int getCursor() {
        return this.cursor;
    }

    public boolean getState() {
        return this.state;
    }
    /*public int getTermlength() throws IOException{
        Runtime.getRuntime().exec("stty size");
        String s=this.readLine();
        System.out.println(s);
        return 1;
    }*/
}
