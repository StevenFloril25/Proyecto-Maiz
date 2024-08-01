package Controladores;

import java.io.File;

/**
 *
 * @author LAB02_HS_MAQ01
 */
public class Global {
    
    public static String getPath(){
    //obtiene ruta del codigo fuente del Proyecto
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        //eliminar los dos caracteres del final del path
        path=path.substring(0,path.length()-2);
        path+="\\src"; //incluir otras carpetas
        System.out.println("Path del Proyecto " + path);
        return path;
    }
}
