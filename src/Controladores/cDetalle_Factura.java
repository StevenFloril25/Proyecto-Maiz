
package Controladores;

import Modelos.Detalle_Factura;
import Modelos.Libros;
import Controladores.cLibros;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PERSONAL
 */
//id_detalle, N_fac, Codigo, Cantidad, Precio, Importe

public class cDetalle_Factura {
    //Datos lista de objetops de tipo Libros 
    ArrayList<Detalle_Factura> Lista= new ArrayList<>();
    
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
    public Detalle_Factura getFactura(int pos) {
        Detalle_Factura ob = null;//Objeto vacio
        if (pos >= 0 && pos < Lista.size()) {
            ob = Lista.get(pos);
        }
        return ob;
    }
    
     /**
     * Algoritmo de busqueda secuencial en el arreglo Lista
     *
     * @param id_detalle
     * @return posicion en el arreglo del estudiante encontrado
     */
    public int localizar(String id_detalle) {
        int pos = -1; //se retorna -1 si no se encuentra en el arreglo
        for (int i = 0; i < Lista.size(); i++) {
            Detalle_Factura  e = getFactura(i);
            if (id_detalle.equalsIgnoreCase(e.id_detalle)) {
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
    public void nuevo(Detalle_Factura ob) throws IOException {
        int pos = localizar(ob.id_detalle);
        if (pos == -1) {//si cedula no esta registrada, se agrega nuevo estudiante
            Lista.add(ob);
        } else {
            throw new RuntimeException("!Ya se ha registrado una factrua con el siguiente id: "+ ob.id_detalle);
        }
    }
    /**
     * Modificar datos de un estudiante existente
     * @param ob
     * @param Cod
     * @throws java.io.IOException
     */
   

    /**
     * Eliminar una factura
     *
     * @param id_detalle
     * @throws java.io.IOException
     */
    public void eliminar(String id_detalle) throws IOException {
        int pos = localizar(id_detalle);
        if (pos > -1) {//si estudiante esta registrado se elimina
            Lista.remove(pos);
        } else {
            throw new RuntimeException("No existe una factura registrado con la cedula ingresada");
        }
    }

    /**
     * Lista datos en un defaultablemodel para presentar en una tabla
     *
     * @return
     */
    public DefaultTableModel getTabla() throws IOException {
        //encabezados de columnas de la tabla
//id_detalle, N_fac, Codigo, Cantidad, Precio, Importe
        String[] columnName = {"id_detalle", "N_fac", "Codigo", "Cantidad", "Precio", "Importe"};
        DefaultTableModel tabla = new DefaultTableModel(columnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cLibros ListL = new cLibros();
        ListL.leer(); //carga los datos
        for (Detalle_Factura ob : Lista) {
            //extrae datos de un libro
            int pos1 = ListL.localizar(ob.Codigo);
            Libros c = ListL.getLibros(pos1);
            Object[] row = {
                ob.id_detalle, ob.N_fac, ob.Codigo,
                ob.Cantidad, ob.Precio, ob.Importe};
            tabla.addRow(row);
        }
        return tabla;
    }

    /**
     * Algoritmo de busqueda secuencial mediante criterio apellido parcial en el
     * arreglo Lista
     *
     * @param id_detalle
     * @return cEstudiante
     * @throws IOException
     */
    public cDetalle_Factura buscar_Codigo(String id_detalle) throws IOException {
        cDetalle_Factura ob = new cDetalle_Factura();
        for (Detalle_Factura p : Lista) {
            if (p.id_detalle.toLowerCase().contains(id_detalle.toLowerCase())) {
                ob.nuevo(p);
            }
        }
        return ob;
    }

    public static final String SEPARADOR = ";";
    public static final String QUOTE = "\"";
    //nombre del archivo csv
    public String path = Global.getPath() + "\\Data\\dataDetalle_Factura.csv";

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
                Detalle_Factura ob = new Detalle_Factura();
                //id_detalle, N_fac, Codigo, Cantidad, Precio, Importe
                ob.id_detalle = row[0];
                ob.N_fac = row[1];
                ob.Codigo = row[2];
                ob.Cantidad = row[3];
                ob.Precio = Double.valueOf(row[4]);
                ob.Importe = Double.valueOf(row[5]);
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
            //id_detalle, N_fac, Codigo, Cantidad, Precio, Importe
            file.append("id_detalle").append(SEPARADOR);
            file.append("N_fac").append(SEPARADOR);
            file.append("Codigo").append(SEPARADOR);
            file.append("Cantidad").append(SEPARADOR);
            file.append("Precio").append(SEPARADOR);
            file.append("Importe").append(NEXT_LINE); //último tiene q ser next line
            for (Detalle_Factura ob : Lista) {
                file.append(ob.id_detalle).append(SEPARADOR);
                file.append(ob.N_fac).append(SEPARADOR);
                file.append(ob.Codigo).append(SEPARADOR);
                file.append(ob.Cantidad).append(SEPARADOR);
                file.append(ob.Precio+"").append(SEPARADOR);
                file.append(ob.Importe + "").append(NEXT_LINE);//convierte a texto
            }
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
    

    
    
