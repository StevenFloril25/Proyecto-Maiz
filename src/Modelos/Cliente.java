package Modelos;

/**
 * Clase Modelo de Cliente
 *
 * @author Valentina Robalino
 * @date 17/07/2024
 * @version 1.0
 */
public class Cliente {

    //datos
    public String Cedula;
    public String Nombre;
    public String Apellido;
    public String Ciudad;
    public String Direccion;
    public String Email;
    public String Telefono;

    //metodos constructivos
    public Cliente() {
    } //vacío
    //constructor con parametros

    public Cliente(String Cedula, String Nombre, String Apellido, String Ciudad, String Direccion, String Email, String Telefono) {
        this.Cedula = Cedula;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Ciudad = Ciudad;
        this.Direccion = Direccion;
        this.Email = Email;
        this.Telefono = Telefono;
    }
    
    
    //constructor con parametros
    
    public String Ver() {
        String datos = Cedula + ",";
        datos += Nombre + ",";
        datos += Apellido + ",";
        datos += Ciudad + ",";
        datos += Direccion + ",";
        datos += Email + ",";
        datos += Telefono + ",";
        return datos;
    }
}
