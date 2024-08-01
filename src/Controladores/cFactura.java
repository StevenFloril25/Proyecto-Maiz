package Controladores;

/**
 * Clase Controlador de Factura
 *
 * @author Ashley Malavé
 * @date 17/07/2024
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import Modelos.Factura;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

public class cFactura {

    //lista de objetos de Factura
    ArrayList<Factura> Lista = new ArrayList<>();

    //metodos
    /**
     * Retorna la cantidad de objetos del arreglo
     *
     * @return
     */
    public int Count() {
        return Lista.size(); //devuelve la cantidad de objetos del arreglo
    }

    /**
     * Borrar todos los elementos del arreglo
     */
    public void Clear() {
        Lista.clear();
    }

    /**
     * Retornar un objeto del arreglo Lista
     *
     * @param pos es la posicion del objeto en el arreglo
     * @return objeto encontrado
     */
    public Factura getFactura(int pos) {
        Factura ob = null;
        if (pos >= 0 && pos < Count()) {
            ob = Lista.get(pos);
        }
        return ob;
    }

    /**
     * Algoritmo de busqueda secuencial en la Lista de objetos
     *
     * @param N_fac
     * @return posicion del objeto en el arreglo
     */
    public int localizar(String N_fac) { //buscar por el campo clave
        int pos = -1; //se retorna -1 si no se encuentra en el arreglo
        for (int i = 0; i < Count(); i++) {
            Factura f = getFactura(i);
            if (N_fac.equalsIgnoreCase(f.N_fac)) {
                pos = i; //posicion encontrada
                break; //finaliza el ciclo for si lo encuentra
            }
        }
        return pos;
    }

    /**Registra una nueva Factura
     * @param ob
     * @throws java.io.IOException
     */
    public void nuevo(Factura ob) throws IOException {
        int pos = localizar(ob.N_fac); //buscar por el campo clave
        if (pos == -1) {//si el codigo no esta registrado se agrega el objeto a la lista
            Lista.add(ob); 
        } else {
            throw new RuntimeException("Ya se ha creado una factura con este numero: " + ob.N_fac);
        }
    }

    /**Eliminar una Factura
     * @param N_fac campo clave
     * @throws java.io.IOException
     */
    public void anular(String N_fac) throws IOException {
        int pos = localizar(N_fac);
        if (pos > -1) {//si la factura esta registrada se anula
            Lista.remove(pos);
        } else {
            throw new RuntimeException("No existe una Factura registrada con el numero: " + N_fac);
        }
    }
    
    /**
     * Lista de las Facturas en un defaultablemodel para presentar en una tabla del
     * interfaz
     *
     * @return
     */
    public DefaultTableModel getTabla() throws IOException { //mostrar los datos de una tabla
        //encabezados de columnas de la tabla   
        //N_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total
        String[] columnName = {"N_fac", "Fecha", "Cd_cliente", "Subtotal", "Descuento", "Iva", "Total"};
        DefaultTableModel tabla = new DefaultTableModel(columnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //tabla(filas) no editable
            }
        };
        
        
        for (Factura ob : Lista) { //for each, recorre cada elemento de la lista //se puede para presentar en una tabla
            
            Object[] row = {ob.N_fac, ob.Fecha, ob.Cd_cliente, ob.Subtotal,
                ob.Descuento, ob.Iva, ob.Total};
            tabla.addRow(row);
        }
        return tabla;
    }

    /**
     * Busqueda parcial por el numero de la factura
     *
     * @param N_fac
     * @return cFactura
     * @throws IOException
     */
    public cFactura buscar_N_fac(String N_fac) throws IOException {
        cFactura ob = new cFactura(); //instanciar un objeto de controlador Factura
        for (Factura f : Lista) {
            if (f.N_fac.toLowerCase().contains(N_fac.toLowerCase())) {
                ob.nuevo(f);//añade a lista temporal el objeto encontrado
            }
        }
        return ob;
    }
    
    public cFactura buscar_cedula(String cedula) throws IOException {
        cFactura ob = new cFactura();
        for (int i = 0; i < Lista.size(); i++) {
            Factura e = getFactura(i);
            if (e.Cd_cliente.toLowerCase().startsWith(cedula)) {
                ob.nuevo(e);
            }
        }
        return ob;
    }
    
    
    //metodos para leer y guardar datos en un archivo de texto con formato csv
    
    public static final String SEPARADOR = ";";
    public static final String QUOTE = "\"";
    //ruta y nombre del archivo csv
    public String path = Global.getPath() + "\\Data\\dataFacturas.csv";
 
    //leer datos del archivo
    public void leer() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            System.out.println("Datos del archivo: ");
            String line = br.readLine();
            System.out.println(line); //etiquetas del archivo
            Clear(); //limpiar lista de objetos del arreglo
            line = br.readLine();
            while (line != null) { //verifica si tiene datos
                String[] row = line.split(SEPARADOR);
                removeTrailingQuotes(row);
                Factura ob = new Factura();
                //N_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total
                ob.N_fac = row[0];
                ob.Fecha = row[1];
                ob.Cd_cliente = row[2];
                ob.Subtotal = Double.valueOf(row[3]);
                ob.Descuento = Double.valueOf(row[4]);
                ob.Iva = Double.valueOf(row[5]);
                ob.Total = Double.valueOf(row[6]);
                nuevo(ob);//agregar a la lista	           
                System.out.println(Arrays.toString(row));
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        } finally { //de todas maneras cerrar el archivos, tenga errores o no
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
    
    /**
     * Modificar datos de un objeto existente
     *
     * @param ob
     * @param N_fac
     * @throws java.io.IOException
     */
    public void modificar(Factura ob, String N_fac) throws IOException {
        int pos = localizar(N_fac);
        if (pos > -1) {//si el objeto esta registrado
            Lista.set(pos, ob); //Se modifica el objeto en la lista segun posición
        } else {
            throw new RuntimeException("No existe una factura registrado con la cedula: " + N_fac);
        }
    }

    //guardar datos en el archivo
    public void guardar() throws IOException {
        FileWriter file;
        try {
            file = new FileWriter(path);
            final String NEXT_LINE = "\n";
            //primera fila del archivo, corresponde a los nombres de los datos
            //N_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total
            file.append("N_fac").append(SEPARADOR);
            file.append("Fecha").append(SEPARADOR);
            file.append("Cd_cliente").append(SEPARADOR);
            file.append("Subtotal").append(SEPARADOR);
            file.append("Descuento").append(SEPARADOR);
            file.append("Iva").append(SEPARADOR);
            file.append("Total").append(NEXT_LINE); //ultimo importante NEXT_LINE  

            for (Factura ob : Lista) {

                file.append(ob.N_fac).append(SEPARADOR);
                file.append(ob.Fecha).append(SEPARADOR);
                file.append(ob.Cd_cliente).append(SEPARADOR);
                file.append(ob.Subtotal + "").append(SEPARADOR);
                file.append(ob.Descuento + "").append(SEPARADOR);
                file.append(ob.Iva + "").append(SEPARADOR);
                file.append(ob.Total + "").append(NEXT_LINE); //ultimo importante NEXT_LINE               
            }
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
