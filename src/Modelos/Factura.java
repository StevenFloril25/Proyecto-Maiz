package Modelos;

/**
 * Clase Modelo de Factura
 *
 * @author Ashley Malavé
 * @date 16/07/2024
 * @version 1.0
 */
public class Factura {

    //datos
    //N_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total
    public String N_fac;
    public String Fecha;
    public String Cd_cliente;
    public double Subtotal;
    public double Descuento;
    public double Iva;
    public double Total;

    //metodos constructores
    public Factura() {
    } //constructor vacio

    //constructor con parametros
    public Factura(String N_fac, String Fecha, String Cd_cliente, double Subtotal,
            double Descuento, double Iva, double Total) {
        this.N_fac = N_fac;
        this.Fecha = Fecha;
        this.Cd_cliente = Cd_cliente;
        this.Subtotal = Subtotal;
        this.Descuento = Descuento;
        this.Iva = Iva;
        this.Total = Total;
    }

    //Devuelve un String con los datos del objeto
    public String Ver() {
        String datos = N_fac + ",";
        datos += Fecha + ",";
        datos += Cd_cliente + ",";
        datos += Subtotal + ",";
        datos += Descuento + ",";
        datos += Iva + ",";
        datos += Total;
        return datos;
    }

}
