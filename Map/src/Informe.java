/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lab18412
 */
import java.util.Vector;
import javax.microedition.rms.*;
import java.io.*;

public class Informe {

    private static RecordStore record = null;
    static byte[] lectura;

    static public void Almacenar(Datos d) {
        try {
            record = RecordStore.openRecordStore("datos", true);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bout);
            dout.writeUTF(d.getNombre());
            dout.writeUTF(d.getComentarios());
            dout.flush();
            dout.close();
            byte[] data = bout.toByteArray();

            record.addRecord(data, 0, data.length);
            bout.reset();
            dout.close();

            bout.close();
        } catch (Exception e) {
            System.out.println("error 0" +e );
        }
    }

    static public Vector LeerEncuesta() {
        Vector persons = new Vector();
        try {
            Datos datos;
            record = RecordStore.openRecordStore("datos", true);
            RecordEnumeration re = record.enumerateRecords(null, null, true);
            int numRecs = re.numRecords();
            while (re.hasNextElement()) {
                byte[] b = re.nextRecord();
                ByteArrayInputStream bais = new ByteArrayInputStream(b);
                DataInputStream dis = new DataInputStream(bais);
                String nombre = dis.readUTF();
                String comentario = dis.readUTF();
                datos = new Datos(nombre, comentario);
                persons.addElement(datos);
                bais.close();
                dis.close();
            }
        } catch (Exception e) {
            System.out.println("error 1" +e );
        }
        return persons;
    }
}
