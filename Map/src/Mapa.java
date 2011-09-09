
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

public class Mapa extends MIDlet implements CommandListener {

    public Command exitCommand, exitCommand1;
    public Command Salir;
    private Command Salir1;
    public Command Guardar;
    public Command Ver;
    public Command Ver2;
    private Command atras;
    public Form form;
    private List lista;
    private TextField nombre, comentario;
    Datos datos;
    private TextBox txtBoxResultados;
    private Vector dato;
    private Datos persona = new Datos("", "");
    Displayable nd;
    Displayable nd2;
    Image image = null;
    public Vector vCordX;
    public Vector vCordY;
    public int id = 0;
    private int cordX;
    private int cordY;

    public Mapa() {
        try {
            image = Image.createImage("/plano2.jpg");
        } catch (Exception e) {
        }

        Display display = Display.getDisplay(this);

        nd = new NavegarCanvas(image, this);

        exitCommand = new Command("Salir", Command.EXIT, 1);


        nd.addCommand(exitCommand);

        nd.setCommandListener(this);
        display.setCurrent(nd);

        datos = new Datos(null, null);
        form = new Form("Informacion Mapa");
        nombre = new TextField("Nombre:", "", 20, TextField.ANY);
        comentario = new TextField("Comentarios:", "", 30, TextField.ANY);
        txtBoxResultados = new TextBox("Resultado ", "", 300, TextField.UNEDITABLE);
        Salir = new Command("Atras", Command.EXIT, 1);
        Salir1 = new Command("Atras", Command.EXIT, 1);
        Guardar = new Command("Guardar", Command.OK, 1);
        Ver = new Command("Ver", Command.OK, 1);
        Ver2 = new Command("Ver Favoritos", Command.OK, 1);
        atras = new Command("Atras", Command.OK, 1);
        lista = new List("Favoritos", List.IMPLICIT);
        txtBoxResultados.addCommand(atras);
        txtBoxResultados.setCommandListener(this);
        lista.addCommand(Salir);
        lista.setCommandListener(this);
        form.append(nombre);
        form.append(comentario);
        form.addCommand(Guardar);
//        form.addCommand(Ver);
        form.addCommand(Ver2);
        form.addCommand(Salir1);
        form.setCommandListener(this);
        vCordX = new Vector();
        vCordY = new Vector();

    }

    public void startApp() {
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == Guardar) {
            String name = nombre.getString();
            String comments = comentario.getString();
            datos.setNombre(name);
            datos.setComentarios(comments);
            vCordX.addElement(String.valueOf(cordX));
            vCordY.addElement(String.valueOf(cordY));
            Informe.Almacenar(datos);
            Alert alerta = new Alert("Guardo Exitosamente");
            Display.getDisplay(this).setCurrent(alerta);
        } else if (c == exitCommand1) {
            Display.getDisplay(this).setCurrent(lista);
        } //        else if (c == Ver) {
        //            int x = Integer.parseInt((String) vCordX.elementAt(0));
        //            int y = Integer.parseInt((String) vCordY.elementAt(0));
        //
        //            System.out.println("xdkajlfsd " + x);
        //            System.out.println(" aksjdflj" + y);
        //            Display display = Display.getDisplay(this);
        //            nd2 = new NavegarCanvas2(image, this, x, y);
        //
        //
        //            display.setCurrent(nd2);
        ////            Llenar();
        //
        //        }
        else if (c == Ver2) {
            Llenar();
        } else if (c == List.SELECT_COMMAND) {
            if (lista.size() > 0) {
                System.out.println("Entre aqui");
                id = lista.getSelectedIndex();
                int x = Integer.parseInt((String) vCordX.elementAt(id));
                int y = Integer.parseInt((String) vCordY.elementAt(id));

                nd2 = new NavegarCanvas2(image, this, x, y);
                exitCommand1 = new Command("Atras", Command.OK, 1);
                nd2.addCommand(exitCommand1);
                nd2.setCommandListener(this);

                Display.getDisplay(this).setCurrent(nd2);
//                mostrarResultado();
            } else {
                Alert alerta = new Alert("No hay favoritos");
                Display.getDisplay(this).setCurrent(alerta);
            }
        } else if (c == atras) {
            Display.getDisplay(this).setCurrent(lista);
        } else if (c == Salir) {
            Display.getDisplay(this).setCurrent(nd);
        } else if (c == Salir1) {
            Display.getDisplay(this).setCurrent(nd);
//            destroyApp(true);
//            notifyDestroyed();
        }
    }

    public void mostrarResultado(int i) {

        try {



            txtBoxResultados.setString("");
            String p = lista.getString(lista.getSelectedIndex());
            Vector v = Informe.LeerEncuesta();
            persona = (Datos) v.elementAt(i);
//            persona = (Datos) v.elementAt(lista.getSelectedIndex());
            String nom = "NOMBRE: " + persona.getNombre() + "\n";
            String Com = "COMENTARIOS: " + persona.getComentarios() + "\n";

            txtBoxResultados.insert(nom, 0);
            txtBoxResultados.insert(Com, txtBoxResultados.size());

            Display.getDisplay(this).setCurrent(txtBoxResultados);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Llenar() {
        lista.deleteAll();
        try {
            dato = Informe.LeerEncuesta();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dato.isEmpty()) {
            Alert alerta = new Alert("No hay datos");
            Display.getDisplay(this).setCurrent(alerta);
        } else {
            System.out.println("tamaÃ±o" + dato.size());
            for (int i = 0; i < dato.size(); i++) {
                datos = (Datos) dato.elementAt(i);
                System.out.println("nombre" + i);
                lista.append(datos.getNombre(), null);
            }
        }
        Display.getDisplay(this).setCurrent(lista);
    }

    public void setCords(int cordX, int cordY) {
        this.cordX = cordX;
        this.cordY = cordY;
    }
}