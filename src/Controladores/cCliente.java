package Controladores;
/**
 * Clase Modelo de Cliente
 * @author Valentina Robalino
 * @date 17/07/2024
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import Modelos.Cliente;
import Modelos.Libros;

public class cCliente {

    //
    ArrayList<Cliente> Lista = new ArrayList<>();

    //metodos
    /**
     * Retorna la cantidad de objetos del arreglo
     *
     * @return
     */
    public int Count() {
        return Lista.size(); //tamaño de arreglo
    }

    /**
     * Borrar todos los elementos del arreglo
     */
    public void Clear() {
        Lista.clear(); //borra todos los objetos de la lista
    }

    /**
     * Retornar un objeto del arreglo Lista
     *
     * @param pos es la posicion del objeto en el arreglo
     * @return objeto encontrado
     */
    public Cliente getCliente(int pos) {
        Cliente ob = null; //referencia a un objeto vacío
        if (pos >= 0 && pos < Count()) { //verifica si pos es válido
            ob = Lista.get(pos);
        }
        return ob;
    }

    /**
     * Algoritmo de busqueda secuencial en la Lista de objetos
     *
     * @param Cedula
     * @return posicion en el arreglo del objeto encontrado
     */
    public int localizar(String Cedula) {
        int pos = -1; //se retorna -1 si no se encuentra en el arreglo
        for (int i = 0; i < Lista.size(); i++) {
            Cliente e = getCliente(i);
            if (Cedula.equalsIgnoreCase(e.Cedula)) {
                pos = i; //posicion encontrada
                break; //finaliza el ciclo for si lo encuentra
            }
        }
        return pos;
    }

    /**
     * Registrar un nuevo objeto Cliente
     *
     * @param ob
     * @throws java.io.IOException
     */
    public void nuevo(Cliente ob) throws IOException {
        int pos = localizar(ob.Cedula); //buscar por el campo clave
        if (pos == -1) {//si el Cliente no esta registrado con Cedula
            Lista.add(ob); //añade el objeto a la lista
        } else {
            throw new RuntimeException("Ya se ha registrado un Cliente con la cedula: " + ob.Cedula);
        }
    }

    /**
     * Modificar datos de un objeto existente
     *
     * @param ob
     * @param cedula
     * @throws java.io.IOException
     */
    public void modificar(Cliente ob, String cedula) throws IOException {
        int pos = localizar(cedula);
        if (pos > -1) {//si el objeto esta registrado
            Lista.set(pos, ob); //Se modifica el objeto en la lista segun posición
        } else {
            throw new RuntimeException("No existe un Cliente registrado con la cedula: " + cedula);
        }
    }

    /**
     * Eliminar un objeto de la lista
     *
     * @param cedula
     * @throws java.io.IOException
     */
    public void eliminar(String cedula) throws IOException {
        int pos = localizar(cedula);
        if (pos > -1) {//si objeto esta registrado se elimina
            Lista.remove(pos); //eliminar el objeto
        } else {
            throw new RuntimeException("No existe el registro con la cedula: " + cedula);
        }

    }

    /**
     * Lista datos en un defaultablemodel para presentar en una tabla del
     * interfaz
     *
     * @return
     */
    public DefaultTableModel getTabla() { //mostrar los datos de una tabla
        //encabezados de columnas de la tabla   
        //Cedula.Nombre
        String[] columnName = {"Cedula", "Nombre", "Apellido", "Ciudad", "Direccion", "Email", "Telefono"};
        DefaultTableModel tabla = new DefaultTableModel(columnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //tabla no editable
            }
        };
        //"Código", "Nombre", "Descripcion", "Autor", "Categoria", "Precio", "Stock"
        for (Cliente ob : Lista) { //for each, recorre cada elemento de la lista //se puede para presentar en una tabla

            //for (int i = 0; i < Lista.size(); i++) {
            //  Cliente ob = getCliente(i);
            Object[] row = {ob.Cedula, ob.Nombre,ob.Apellido,ob.Ciudad,ob.Direccion,ob.Email,ob.Telefono};
            tabla.addRow(row);
        }
        return tabla;
    }

    public cCliente buscar_cedula(String cedula) throws IOException {
        cCliente ob = new cCliente();
        for (Cliente c : Lista) {
            if (c.Cedula.toLowerCase().contains(cedula.toLowerCase())) {
                ob.nuevo(c);
            }
        }
        return ob;
    }
    
    public cCliente buscar_nombre(String nombre) throws IOException {
        cCliente ob = new cCliente();
        for (int i = 0; i < Lista.size(); i++) {
            Cliente c = getCliente(i);
            if (c.Nombre.toLowerCase().startsWith(nombre)) {
                ob.nuevo(c);
            }
        }
        return ob;
    }

    public cCliente buscar_apellido(String apellido) throws IOException {
        cCliente ob = new cCliente();
        for (int i = 0; i < Lista.size(); i++) {
            Cliente c = getCliente(i);
            if (c.Apellido.toLowerCase().startsWith(apellido)) {
                ob.nuevo(c);
            }
        }
        return ob;
    }
    
    //Métodos para leer o guardar la lista de objetos utilizando un archivo de datos CSV
    
    public static final String SEPARADOR = ";";
    public static final String QUOTE = "\"";
    //nombre del archivo csv
    public String path = Global.getPath() + "\\Data\\dataCliente.csv";

    public void leer() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            System.out.println("Datos del archivo: ");
            String line = br.readLine();
            System.out.println(line);
            Clear(); //limpiar lista de objetos del arreglo
            line = br.readLine();
            while (line != null) {
                //la line a de texto del archivo lo separa un arreglo
                //Código, Nombre, Apellido, Ciudad, Direccion, Email, Telefono
                String[] row = line.split(SEPARADOR);
                removeTrailingQuotes(row);
                Cliente ob = new Cliente();
                ob.Cedula = row[0];
                ob.Nombre = row[1];
                ob.Apellido = row[2];
                ob.Ciudad = row[3];
                ob.Direccion = row[4];
                ob.Email = row[5];
                ob.Telefono = row[6];
      
                nuevo(ob);//agregar a la lista	           
                System.out.println(Arrays.toString(row));
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        } finally {
            if (null != br) {
                br.close();
            }
        }
    }

    //eliminar comillas
    private static String[] removeTrailingQuotes(String[] fields) {
        String result[] = new String[fields.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = fields[i].replaceAll("^" + QUOTE, "").replaceAll(QUOTE + "$", "");
        }
        return result;
    }

    //guarda la lista de objetos en el archivo CSV
    public void guardar() throws IOException {
        FileWriter file;
        try {
            file = new FileWriter(path);
            final String NEXT_LINE = "\n";
                //Código, Nombre, Apellido, Ciudad, Direccion, Email, Telefono
            file.append("Cedula").append(SEPARADOR);
            file.append("Nombre").append(SEPARADOR);
            file.append("Apellido").append(SEPARADOR);
            file.append("Ciudad").append(SEPARADOR);
            file.append("Direccion").append(SEPARADOR);
            file.append("Categoria").append(SEPARADOR);
            file.append("Email").append(SEPARADOR);
            file.append("Telefono").append(NEXT_LINE); //último tiene q ser next line
            for (Cliente ob : Lista) {
                file.append(ob.Cedula).append(SEPARADOR);
                file.append(ob.Nombre).append(SEPARADOR);
                file.append(ob.Apellido).append(SEPARADOR);
                file.append(ob.Ciudad).append(SEPARADOR);
                file.append(ob.Direccion).append(SEPARADOR);
                file.append(ob.Email).append(SEPARADOR);
                file.append(ob.Telefono ).append(NEXT_LINE);//convierte a texto

            }
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
