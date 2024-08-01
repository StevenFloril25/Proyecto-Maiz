package Pruebas;

/**
 * Clase Prueba
 *
 * @author Ashley Malavé
 * @date 17/07/2024
 * @version 1.0
 */

import Modelos.Factura;
import Controladores.cFactura;
import Controladores.Global;
import java.io.IOException;

public class Prueba_F {

    public static void main(String[] args) throws IOException {
        //Global.getPath();
        cFactura listF = new cFactura();
        //N_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total
        Factura f = new Factura("001", "18/07/2024", "0706172988", 125, 15, 18.75, 128.75);
        Factura f2 = new Factura("002", "04/06/2024", "0739939393", 80, 10, 20, 91);
        //System.out.println(f.Ver());
        try {
            listF.nuevo(f);
            listF.nuevo(f2);
            listF.guardar();
            listF.leer();
            Factura ob = listF.getFactura(0);
            System.out.println("Cantidad de objetos de la lista: " + listF.Count());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
