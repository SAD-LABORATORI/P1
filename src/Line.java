import java.io.*;
import java.util.*;



public class Line extends Observable {

    StringBuffer buffer;
    int cursor = 0;
    boolean state = true; //true es modo inserción (normal)
    //false es modo sobre-escritura

    public Line() {

        buffer = new StringBuffer(1000);
        cursor = 0;

    }

    public int getLength() {

        return buffer.length();

    }

    public void moveRight() {
        //System.out.print("DRETTAAAAA");
        if (this.cursor != buffer.length()) {
            this.cursor++;
            
            setChanged();
            //notifyObservers(new Console.Command(Console.Accion.RIGHT));
            notifyObservers(Key.RIGHT);
        }

    }

    public void moveLeft() {
        if (this.cursor != 0) {
            this.cursor--;
            setChanged();
            //notifyObservers(new Console.Command(Console.Accion.LEFT));
            notifyObservers(Key.LEFT);
        }

        //notifyObservers();
    }

    public void moveHome() {
        this.cursor = 0;
        setChanged();
        //notifyObservers(new Console.Command(Console.Accion.HOME));
        notifyObservers(Key.HOME);
    }

    public void moveEnd() {
        this.cursor = buffer.length();
        setChanged();
        //notifyObservers(new Console.Command(Console.Accion.END));
        notifyObservers(Key.END);
    }

    public void suprChar() {

        if (this.cursor == this.buffer.length()) {
            return;
        }
        this.buffer = buffer.deleteCharAt(cursor);
        setChanged();
        //notifyObservers(new Console.Command(Console.Accion.SUPR));
        notifyObservers(Key.SUPR);

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

        if (!this.state && (this.cursor != this.buffer.length())) {
            this.cursor++;
            this.delChar();

        }
        this.buffer = buffer.insert(cursor, c);
        this.cursor++;
        setChanged();
        //notifyObservers(new Console.Command(Console.Accion.ADD));
        notifyObservers((int) c);
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

}
