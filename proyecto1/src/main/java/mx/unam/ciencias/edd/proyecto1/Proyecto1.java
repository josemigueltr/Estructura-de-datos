package mx.unam.ciencias.edd.proyecto1;

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
 * Clase que contiene el Main.
 *
 **/

public class Proyecto1 {
    //variable booleana,indica si fue ingresada la bandera -r
    static boolean BanderaR = false;
    //variable booleana,indica si fue ingresada la bandera -o
    static boolean BanderaO = false;
    //variable tipo String,almacenara el nombre de larchivo de salida si es  ingresada la bandera -o
    static String nombreArchivo = null;

    static void uso() {
        System.out.println("Uso: java -jar proyecto1.jar [-r|-o] <archivo>");
        System.exit(1);
    }


    public static void main(String[] args) throws IOException, FileNotFoundException {
        Archivos archivo = new Archivos();
//si no hay argumentos, entonces se leeran las lineas que el usuario ingrese
        if (args.length == 0) {
            archivo.EntradaEstandar();
            ManejoArchivos.imprimir(archivo);
        } else {
            Argumentos(args,archivo);
          ManejoArchivos.impresion(archivo,BanderaR,BanderaO,nombreArchivo);
    }


		/**
		  *Metodo privado
		 * recorre el arreglo args para indicar que proceso se va a realizar
		 *@param String args arreglo que contiene los datos aque se ingresaron por la entrada EntradaEstandar
		 *@param Archivos el archivo que leerra todos los archivos ingresados en la esntrada EntradaEstandar

		 	 **/

    private static void Argumentos(String[] args, Archivos archivo)
    throws IOException, FileNotFoundException {
        for (int i = 0; i < args.length; i++) {
            if ((!args[i].equals("-r")) && (!args[i].equals("-o")))
                archivo.leerArchivo(args[i]);
            if (args[i].equals("-r")) {
                BanderaR = true;
            }
            if (args[i].equals("-o")) {
                BanderaO = true;
                i += 1;
                if (i < args.length)
                    nombreArchivo = args[i];
                else {
                    System.out.println("La bandera -o  debe de ir seguida de un archivo");
                    System.exit(1);
                }
            }
        }

    }

}
