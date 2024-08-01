package Pruebas;

/**
 * Clase Prueba
 *
 * @author Steven Valdiviezo
 * @date 16/07/2024
 * @version 1.0
 */

import Modelos.Libros;
import Controladores.cLibros; //Importa todo lo que tenga el controlador
import java.io.IOException;

public class Prueba_L {

    public static void main(String[] args) throws IOException {
        Libros p = new Libros("1", "Divina Comedia", "Relato biblico sobre la entrada al infierno", "Homero", "Biblico", 90, 2);
        Libros p1 = new Libros("2", "Mein Kampf", "Relato donde el autor creo los cuartos de inceneracion", "Adof Hitler", "Biblico", 100, 4);
        cLibros list = new cLibros();
        try{
            list.nuevo(p);
            list.nuevo(p1);
            list.guardar();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
         
        list.leer();
        Libros ob = list.getLibros(0);
        System.out.println("Cantidad de libros:  " + list.Count());
    }
}