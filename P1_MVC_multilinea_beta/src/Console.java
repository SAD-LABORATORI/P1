import java.util.*;


public class Console implements Observer {

    private static final String SUPR = "\033[P";
    private static final String LEFT = "\033[D";

    public Console(Line in) {
        System.out.print("\033[4h");
        Line editor=in;
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ( (int) arg) {
            case Key.HOME:
                for (int i = 0; i < ((Line) o).posicions[1][0]; i++) {
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
                System.out.print(SUPR);
                break;

            case Key.END:
                for (int i = 0; i < ( ((Line) o).TERMINAL- ((Line) o).posicions[1][0]); i++) {
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
                System.out.print(SUPR);
                break;
            case Key.NOVA_LINEA:
                System.out.print("\033[E");
                break;
            case Key.SUPR_LINEA:
                System.out.print("\033[F");
                break;
            default:
                System.out.print("\033[@");
                System.out.print((char) ((int) arg));
                if (((Line) o).getState()) {
                    System.out.print(SUPR);
                }
                break;

        }

    }

}
