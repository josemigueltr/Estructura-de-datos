package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;


/**
 * <p>Clase que permite manejar las acciones sobre los Archivos.</p>
 *
 *
 */

public class Archivos {

    /*Lista que almacena las lineas*/
    public Lista < String > lista;

    /*constructor de la clase */
    public Archivos() {
        lista = new Lista < String > ();
    }

    /**
     * Carga linea a linea el texto desde la terminal y lo almacena en el atributo de clase lista.
     *@param String el nombre del archivo a leer.
     *@throw FileNotFoundException si no encuentra el archivo
     *@throw IOException si no logro cargar las lineas del archivo.
     **/
    public void EntradaEstandar() {
        String linea;
        try {
            BufferedReader lineas = new BufferedReader(new InputStreamReader(System.in));
            while ((linea = lineas.readLine()) != null) {
                lista.agrega(linea);
            }
            lineas.close();
        } catch (IOException ex) {
            System.err.println("No es posible leer las lineas");
            System.exit(1);
        }
    }

    /**
     * Carga linea a linea el texto desde un archivo y lo almacena en el atributo de clase lista.
     *@param String el nombre del archivo a leer.
     *@throw FileNotFoundException si no encuentra el archivo
     *@throw IOException si no logro cargar las lineas del archivo.
     **/
    public void leerArchivo(String archivo) throws FileNotFoundException, IOException {
        String lineas;
        if(archivo==null){
          throw new IOException("ESTO NO ES VALIDO");
        }
        try {
            FileReader linea = new FileReader(archivo);
            BufferedReader linea2 = new BufferedReader(linea);
            while ((lineas = linea2.readLine()) != null) {
                lista.agrega(lineas);
            }
            linea2.close();
        } catch (FileNotFoundException ex) {
            System.err.println(archivo + " no es un arcivo existente, por favor verifique" +
                " el nombre o la direccion del archivo");
            System.exit(1);
        }

    }
    /**
     * Escribe en un archivo las lineas orednadas que el usuario ingresa.
     *@param String el nombre del archivo a escribir.
     *@throw IOException si no logra guardar el archivo.
     **/
    public void Escribirarchivos(String nombreArchivo,Lista<String> lista) {
        File guarda = new File(nombreArchivo);
        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            OutputStreamWriter osOut = new OutputStreamWriter(fileOut);
            BufferedWriter out = new BufferedWriter(osOut);
            for (String s: lista) {
                out.write(s + "\n");
            }
            out.close();
        } catch (IOException ioe) {
            System.out.printf("No pude guardar en el archivo \"%s\".\n",
                nombreArchivo);
            System.exit(1);
        }


    }
}
