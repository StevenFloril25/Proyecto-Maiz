/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/** clase modelo de Libros
 *
 * @author Steven Paul Valdiviezo Suquilanda
 * @date 2024-07-16
 * @version 1.0
 */
public class Libros {
    //datos
    //Codigo, nombre, Descdripcion, Autor, Categoria, Precio, Stock
    public String Codigo;
    public String Nombre;
    public String Descripcion;
    public String Autor;
    public String Categoria;
    public double Precio;
    public int Stock;
    //Métodos Constructores
    public Libros(){} //Vacio
    //Constructor con parametros
    public Libros(String Codigo, String Nombre, String Descripcion,
            String Autor, String Categoria, double Precio, int Stock) {
        this.Codigo = Codigo;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Autor = Autor;
        this.Categoria = Categoria;
        this.Precio = Precio;
        this.Stock = Stock;
    }
    public String Ver(){
        String datos =  Codigo+",";
        datos+=Nombre+",";
        datos+=Descripcion+",";
        datos+=Autor+",";
        datos+=Categoria+",";
        datos+=Precio+",";
        datos+=Stock;
        return datos;
    }
}
