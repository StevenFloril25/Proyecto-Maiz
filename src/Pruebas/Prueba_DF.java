
package Pruebas;

/**
 * Clase Prueba
 *
 * @author William Miranda
 * @date 17/07/2024
 * @version 1.0
 */

import Controladores.*; //Importa todo lo que tenga el controlador
import Modelos.*;
import java.io.IOException;

public class Prueba_DF {
    
    public static void main(String[] args) throws IOException {
        
        cDetalle_Factura list = new cDetalle_Factura();
        //id_detalle, N_fac, Codigo, Cantidad, Precio, Importe
        Detalle_Factura p = new Detalle_Factura("WMFCT1", "001", "02001934", "3", 34, 90);
        Detalle_Factura p1 = new Detalle_Factura("WMFCT2", "002", "03480023", "2", 43, 45);
        
        try{
            list.nuevo(p);
            list.nuevo(p1);
            list.guardar();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
         
       list.leer();
       Detalle_Factura ob = list.getFactura(0);
        System.out.println("Cantidad de objetos de la lista: "+list.Count());
    }
}
