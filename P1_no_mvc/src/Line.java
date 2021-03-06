import java.io.*;
import java.util.*;



public class Line extends Observable {

    private static final String SUPR_LINE = "\033[P";
    private static final String LEFT_MOVE = "\033[D";
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
            System.out.print("\033[C");
        }

    }

    public void moveLeft() {
        if (this.cursor != 0) {
            this.cursor--;
            System.out.print(LEFT_MOVE);
        }
    }

    public void moveHome() {
        for (int i = 0; i < this.cursor; i++) {
            System.out.print(LEFT_MOVE);
        }
        this.cursor = 0;
    }

    public void moveEnd() {
        for (int i = 0; i < (this.getLength() - this.getCursor()); i++) {
            System.out.print("\033[C");
        }
        this.cursor = buffer.length();
    }

    public void suprChar() {

        if (this.cursor == this.buffer.length()) {
            return;
        }
        this.buffer = buffer.deleteCharAt(cursor);
        System.out.print(SUPR_LINE);

    }

    public void insChar() {

        this.state = !state;

        if (this.state) {
            System.out.print("\033[4h");
        } else {
            System.out.print("\033[4l");
        }
    }

    public void delChar() {

        if (this.buffer.length() == 0) {
            return;
        }
        this.buffer = buffer.deleteCharAt(cursor - 1);
        this.cursor--;
        if (this.state) {
            System.out.print(LEFT_MOVE);
        }
        System.out.print(SUPR_LINE);
    }

    public void addChar(char c) {
        if (!this.state && (this.cursor != this.buffer.length())) {
            this.cursor++;
            this.delChar();

        }
        this.buffer = buffer.insert(cursor, c);
        this.cursor++;
        System.out.print("\033[@");
        System.out.print((char) ((int) c));
        if (!this.state) {
            System.out.print(SUPR_LINE);
        }
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
