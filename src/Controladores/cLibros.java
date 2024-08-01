
package Controladores;

/** clase controladores de Libros
 *
 * @author Steven Paul Valdiviezo Suquilanda
 * @date 2024-07-16
 * @version 1.0
 */

import Modelos.Libros;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
public class cLibros {
    //Datos lista de objetops de tipo Libros 
    ArrayList<Libros> Lista= new ArrayList<>();
    
    //Metodos 
    /**
     * Retorna la cantidad de objetos del arreglo
     *
     * @return
     */
    public int Count() {
        return Lista.size();//Tamaño de la lista 
    }

    /**
     * Borrar todos los elementos del arreglo
     */
    public void Clear() {
        Lista.clear();  //Borra tods los objetos de la lista
    }
/**
     * Retornar un objeto del arreglo Lista
     *
     * @param pos es la posicion del objeto en el arreglo
     * @return objeto encontrado
     */
    public Libros getLibros(int pos) {
        Libros ob = null;//Objeto vacio
        if (pos >= 0 && pos < Lista.size()) {
            ob = Lista.get(pos);
        }
        return ob;
    }
    /**
     * Algoritmo de busqueda secuencial en el arreglo Lista
     *
     * @param codigo
     * @return posicion en el arreglo del estudiante encontrado
     */
    public int localizar(String codigo) {
        int pos = -1; //se retorna -1 si no se encuentra en el arreglo
        for (int i = 0; i < Lista.size(); i++) {
            Libros  e = getLibros(i);
            if (codigo.equalsIgnoreCase(e.Codigo)) {
                pos = i; //posicion encontrada
                break; //finaliza el ciclo for
            }
        }
        return pos;
    }
     /**
     * Registra un nuevo estudiante
     * @param ob
     * @throws java.io.IOException
     */
    public void nuevo(Libros ob) throws IOException {
        int pos = localizar(ob.Codigo);
        if (pos == -1) {//si cedula no esta registrada, se agrega nuevo estudiante
            Lista.add(ob);
        } else {
            throw new RuntimeException("!Ya se ha registrado un Libro con el codigo: "+ ob.Codigo);
        }
    }

    /**
     * Modificar datos de un estudiante existente
     * @param ob
     * @param Codigo
     * @throws java.io.IOException
     */
    public void modificar(Libros ob, String codigo) throws IOException {
        int pos = localizar(codigo);
        if (pos > -1) {//si estudiante esta registrado se modifica
            Lista.set(pos, ob);
        } else {
            throw new RuntimeException("No existe un estudiante registrado con la cedula ingresada");
        }
    }

    /**
     * Eliminar un estudiante
     *
     * @param cedula
     * @throws java.io.IOException
     */
    public void eliminar(String cedula) throws IOException {
        int pos = localizar(cedula);
        if (pos > -1) {//si estudiante esta registrado se elimina
            Lista.remove(pos);
        } else {
            throw new RuntimeException("No existe un estudiante registrado con la cedula ingresada");
        }
    }

    /**
     * Lista datos en un defaultablemodel para presentar en una tabla
     *
     * @return
     */
    public DefaultTableModel getTabla() {
        //encabezados de columnas de la tabla

        String[] columnName = {"Codigo", "Nombre", "Descripcion", "Autor", "Categoria", "Precio", "Stock"};
        DefaultTableModel tabla = new DefaultTableModel(columnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Libros ob : Lista) {

            Object[] row = {
                ob.Codigo, ob.Nombre, ob.Descripcion, ob.Autor,
                ob.Categoria, ob.Precio, ob.Stock};
            tabla.addRow(row);
        }
        return tabla;
    }

    /**
     * Algoritmo de busqueda secuencial mediante criterio apellido parcial en el
     * arreglo Lista
     *
     * @param codigo
     * @return cEstudiante
     * @throws IOException
     */
    public cLibros buscar_Codigo(String codigo) throws IOException {
        cLibros ob = new cLibros();
        for (Libros p : Lista) {
            if (p.Codigo.toLowerCase().contains(codigo.toLowerCase())) {
                ob.nuevo(p);
            }
        }
        return ob;
    }
    /**
     * Algoritmo de busqueda secuencial mediante criterio apellido parcial en el
     * arreglo Lista
     *
     * @param Nombre
     * @return cEstudiante
     * @throws IOException
     */
    public cLibros buscar_Nombre(String Nombre) throws IOException {
        cLibros ob = new cLibros();
        for (int i = 0; i < Lista.size(); i++) {
            Libros e = getLibros(i);
            if (e.Nombre.toLowerCase().startsWith(Nombre)) {
                ob.nuevo(e);
            }
        }
        return ob;
    }

    public static final String SEPARADOR = ";";
    public static final String QUOTE = "\"";
    //nombre del archivo csv
    public String path = Global.getPath() + "\\Data\\dataLibros.csv";

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
                String[] row = line.split(SEPARADOR);
                removeTrailingQuotes(row);
                Libros ob = new Libros();
                //Codigo, nombre, Descripcion, Autor, Categoria, Precio, Stock
                ob.Codigo = row[0];
                ob.Nombre = row[1];
                ob.Descripcion = row[2];
                ob.Autor = row[3];
                ob.Categoria = row[4];
                ob.Precio = Double.valueOf(row[5]);
                ob.Stock = Integer.valueOf(row[6]);
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

    //Guarda la lista de Libros en el archivo CSV
    public void guardar() throws IOException {
        FileWriter file;
        try {
            file = new FileWriter(path);
            final String NEXT_LINE = "\n";
            //Codigo, nombre, Descripcion, Autor, Categoria, Precio, Stock
            file.append("Codigo").append(SEPARADOR);
            file.append("Nombre").append(SEPARADOR);
            file.append("Descripcion").append(SEPARADOR);
            file.append("Autor").append(SEPARADOR);
            file.append("Categoria").append(SEPARADOR);
            file.append("Precio").append(SEPARADOR);
            file.append("Stock").append(NEXT_LINE);

            for (Libros ob : Lista) {
                //Codigo, nombre, Descripcion, Autor, Categoria, Precio, Stock
                file.append(ob.Codigo).append(SEPARADOR);
                file.append(ob.Nombre).append(SEPARADOR);
                file.append(ob.Descripcion).append(SEPARADOR);
                file.append(ob.Autor).append(SEPARADOR);
                file.append(ob.Categoria).append(SEPARADOR);
                file.append(ob.Precio+"").append(SEPARADOR);
                file.append(ob.Stock+"").append(NEXT_LINE);
            }
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
