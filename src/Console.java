import java.util.*;
import java.io.*;


public class Console implements Observer {

    private static final String RIGHT = "\033[P";
    private static final String LEFT = "\033[D";

    public Console() {
        System.out.print("\033[4h");
    }

    @Override
    public void update(Observable o, Object arg) {

        switch ((int) arg) {
            case Key.HOME:
                for (int i = 0; i < (((Line) o).getCursor()); i++) {
                    System.out.print(LEFT);
                }
                break;

            case Key.INS:
                System.out.print("\033[4h");		// Modo insert

                break;

            case Key.OVERWRITE:
                System.out.print("\033[4l");		// Modo overwrite

                break;

            case Key.SUPR:
                System.out.print(RIGHT);
                break;

            case Key.END:
                for (int i = 0; i < (((Line) o).getLength() - ((Line) o).getCursor()); i++) {
                    System.out.print("\033[C");
                }
                break;

            case Key.RIGHT:
                System.out.print("\033[C");
                break;

            case Key.LEFT:
                System.out.print(LEFT);
                break;

            case Key.DEL:
                if (((Line) o).getState()) {
                    System.out.print(LEFT);
                }
                System.out.print(RIGHT);
                break;

            default:
                System.out.print("\033[@");
                System.out.print((char) ((int) arg));
                if (((Line) o).getState()) {
                    System.out.print(RIGHT);
                }
                break;

        }

    }

}
