/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author PERSONAL
 */
public class Detalle_Factura {
     //Datos
    public String id_detalle; 
    public String N_fac; 
    public String  Codigo; 
    public String Cantidad; 
    public double Precio; 
    public double Importe;
    
    public Detalle_Factura(){}

    public Detalle_Factura(String id_detalle, String N_fac, String Codigo, String Cantidad, double Precio, double Importe) {
        this.id_detalle = id_detalle;
        this.N_fac = N_fac;
        this.Codigo = Codigo;
        this.Cantidad = Cantidad;
        this.Precio = Precio;
        this.Importe = Importe;
    }

    //id_detalle N_fac Codigo Cantidad Precio Importe
    public String Ver(){
        String datos =  id_detalle+",";
        datos+=N_fac+",";
        datos+=Codigo+",";
        datos+=Cantidad+",";
        datos+=Precio+",";
        datos+=Importe;
        return datos;
    }
}
