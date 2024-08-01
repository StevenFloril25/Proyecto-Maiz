package vistas;

/**
 * Clase Prueba
 *
 * @author Valentina Robalino
 * @date 17/07/2024
 * @version 1.0
 */

import Controladores.*;
import java.io.IOException;
import Modelos.*;

public class Prueba_C {

    public static void main(String[] args) throws IOException {
        //System.out.print(Global.getPath());
        cCliente listC = new cCliente();
        Cliente C = new Cliente("0739939393", "Valentina", "Robalino", "Cuenca", "Lilia Iralda y calle B", "valrob@gmail.com", "0979753801");
        Cliente C1 = new Cliente("0706172988", "Ashley", "Malave", "Machala", "Barrio Amazonas", "amalave1@gmail.com", "0984191437");

        try {

            listC.nuevo(C);
            listC.nuevo(C1);
            listC.guardar();
            listC.leer();
            Cliente ob = listC.getCliente(0);
            System.out.println("Cantidad de objetos de la lista: " + listC.Count());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

           
        }
    }
}
