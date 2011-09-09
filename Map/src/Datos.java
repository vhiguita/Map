/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lab18412
 */
public class Datos {
    String nombre;
    String comentarios;
    public Datos(String nombre,String comentarios){
        this.nombre=nombre;
        this.comentarios=comentarios;

    }
    public String getNombre(){
        return nombre;
    }
    public String getComentarios(){
        return comentarios;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setComentarios(String comentarios){
        this.comentarios=comentarios;
    }
}
