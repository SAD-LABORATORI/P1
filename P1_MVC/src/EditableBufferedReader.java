import java.util.*;
import java.io.*;


class Key{
	public static final int HOME = Integer.MIN_VALUE +1;
	public static final int INS = Integer.MIN_VALUE +2;
	public static final int SUPR = Integer.MIN_VALUE +3;
	public static final int END = Integer.MIN_VALUE +4;
	public static final int RIGHT = Integer.MIN_VALUE +5;
	public static final int LEFT = Integer.MIN_VALUE +6;
	public static final int OVERWRITE = Integer.MIN_VALUE +7;
	public static final int DEL = 127;

}

public class EditableBufferedReader extends BufferedReader {
    
    public EditableBufferedReader(Reader reader) {
        super(reader);
    }
    
    
    protected void setRaw(){
       try{
            String[] cmd = {"/bin/sh","-c", "stty -echo raw </dev/tty"};
            Runtime.getRuntime().exec(cmd);
            //Runtime.getRuntime().exec("stty -echo raw");
       } catch(IOException e){
           System.out.println("Error setraw");
       }
    }
    
    public void unsetRaw() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            System.out.println("Error unsetRaw");
        }
    }
    
    @Override
    public int read(){
        int num=0;
	//Retorna el número del caracter llegit o en cas d'arribar al final retorna -1
	try{
		num = super.read();
		if (num!=27){
			return num;
		}
						// Caracter especial
			num = super.read();
			//if ( num == 91 || num == 79) 
			if ( num == 'O' || num == '[') {			// ->, <-, suprimir,insertar, inicio o fin
				num = super.read();
				switch (num) {
                    case 'C':	// 67		// ->
                        //System.out.print("(DRETA)");
                        num = Key.RIGHT;
                    break; 
                    case 'D':	// 68			// <-
                        num = Key.LEFT;
                    break;
                    case '2':	// 51
                        if ((num = super.read()) != '~'){
                            break;
                        }
                        num = Key.INS;		// insertar
                    break;
                    case 'H':
                    case '1':	// 72			// inicio
                        num = Key.HOME;
                    break;
                    case 'F':	// 70			// fin
                    case '4':
                        num = Key.END;
                    break;
                    case '3':
                        if ((num = super.read()) != '~'){
                            break;
                        }
                        num = Key.SUPR;			//suprimir
                    break;
                    default:
				} 
		}

	}catch(IOException e) {
            	System.out.println("Error RUN");
       	 }	
	return num;
    }
    
    @Override
    public String readLine() {
        String str = null;

        this.setRaw();

        int num = this.read();
        //int i = 0, j = 0;	// Cursor en la línea
        //int length = 0;
        //char[] str = new char[100];
        //String final;
        Line linea = new Line();
        Console con=new Console(linea);
        linea.addObserver(con);

        while (num != 13) { 			// Enter
            switch (num) {						// Falta pensar caso i=0
                case Key.RIGHT:		// ->
                    //System.out.print("(DRETA2)");
                    linea.moveRight();
                    break;

                case Key.LEFT:		// <-
                    linea.moveLeft();
                    break;

                case Key.INS:		// suprimir
                    linea.insChar();
                    break;

                case Key.HOME:		// inicio
                    linea.moveHome();
                    break;

                case Key.END:		// fin
                    linea.moveEnd();
                    break;

                case Key.DEL:		// delete
                    linea.delChar();
                    break;

                case Key.SUPR:
                    linea.suprChar(); 	//suprimir
                    break;

                default:				// No es un caracter especial, lo guardamos
                    
                    linea.addChar((char) num);
                    break;
            }

            num = this.read();
        }
        str = linea.getBuffer();

        this.unsetRaw();

        return str;
    }
}
