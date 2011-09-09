/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.Vector;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.wireless.messaging.*;

/**
 * @author lab18412
 */
public class Principal extends MIDlet implements CommandListener{

    private Command Salir;
     private Command Salir1;
    private Command Guardar;
    private Command Ver;
    private Command atras;
    private Form form;
    private List lista;
    private TextField nombre,  comentario;
    Datos datos;
    private TextBox txtBoxResultados;
    private Vector dato;
    private Datos persona = new Datos("", "");
    public Principal() {
        datos = new Datos(null, null);
        form = new Form("Informacion Mapa");
        nombre = new TextField("Nombre:", "", 20, TextField.ANY);
        comentario = new TextField("Comentarios:", "", 30, TextField.ANY);
        txtBoxResultados = new TextBox("Resultado ", "", 300, TextField.UNEDITABLE);
        Salir = new Command("Salir", Command.EXIT, 1);
        Salir1 = new Command("Salir", Command.EXIT, 1);
        Guardar = new Command("Guardar", Command.OK, 1);
        Ver = new Command("Ver", Command.OK, 1);
        atras=new Command("Atras",Command.OK,1);
        lista = new List("Favoritos", List.IMPLICIT);
        txtBoxResultados.addCommand(atras);
        txtBoxResultados.setCommandListener(this);
        lista.addCommand(Salir);
        lista.setCommandListener(this);
        form.append(nombre);
        form.append(comentario);
        form.addCommand(Guardar);
        form.addCommand(Ver);
        form.addCommand(Salir1);
        form.setCommandListener(this);


    }

    public void startApp() {
        Display.getDisplay(this).setCurrent(form);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

//    public void notifyIncomingMessage(MessageConnection arg0) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
    public void commandAction(Command c, Displayable d) {
        if (c == Guardar) {
            String name = nombre.getString();
            String comments = comentario.getString();
            datos.setNombre(name);
            datos.setComentarios(comments);
            Informe.Almacenar(datos);
        } else if (c == Ver) {
            Llenar();

        } else if (c == List.SELECT_COMMAND) {
            if (lista.size() > 0) {
                System.out.println("Entre aqui");
                mostrarResultado();
            } else {
                Alert alerta=new Alert("No hay favoritos");
                Display.getDisplay(this).setCurrent(alerta);
            }
        }else if(c==atras){
            Display.getDisplay(this).setCurrent(lista);
        }else if(c==Salir){
            Display.getDisplay(this).setCurrent(form);
        }else if(c==Salir1){
                destroyApp(true);
                notifyDestroyed();
        }
    }

    public void mostrarResultado() {

        try {
            txtBoxResultados.setString("");
            String p = lista.getString(lista.getSelectedIndex());
            Vector v = Informe.LeerEncuesta();
            persona = (Datos) v.elementAt(lista.getSelectedIndex());
            String nom = "NOMBRE: " + persona.getNombre() + "\n";
            String Com = "COMENTARIOS: " + persona.getComentarios()+ "\n";

            txtBoxResultados.insert(nom, 0);
            txtBoxResultados.insert(Com,    txtBoxResultados.size() );

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

//    public void run() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
}
