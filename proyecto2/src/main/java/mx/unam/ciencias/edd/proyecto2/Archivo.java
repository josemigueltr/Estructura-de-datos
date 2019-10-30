package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
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
 * <p>Clase  para leer los datos de entrada estandar</p>
 *  o archivos que nos pase el usuario
 *
 */

public class Archivo {

static EstructurasDeDatos estructuraE = null;
/*Lista que almacena los elementos*/
static Lista<Integer> lnum=new Lista<Integer>();

/**
 * Carga linea a linea el texto desde la terminal y lo almacena en la lista.
 *@param BufferedReader  con el tipo de entrada a   leer.
 *@throw IllegalArgumentException si no se ingreso una clase a graficar
 *@throw IOException si no logro cargar las lineas del archivo o de la entrada estandar.
 **/
public static void lectura(BufferedReader br) {
    boolean c=false;
    String input;
    String[] elementos=null;
        try{
             while ((input = br.readLine()) != null) {
                  String[] linea=input.trim().split("#");
                  if(linea.length==0)
                      continue;
                      String datos=linea[0];
                      if(datos.isEmpty())
                          continue;
                      elementos = datos.trim().split(" ");
                      for(String i:elementos) {
                          if(c==false) {
                              try {
                                    estructuraE= EstructurasDeDatos.valueOf(i);
                                   }catch (IllegalArgumentException e) {
                                        System.out.println("Se ingreso una clase no valida");
                                        System.exit(1);
                                   }
                              c=true;
                            }
                        else{
                              try{
                                   lnum.agrega(Integer.parseInt(i));
                                  } catch (IllegalArgumentException e) {
                                          System.out.println("Se ingreso un elemento no valido ,Debes ingresar solo numeros enteros");
                                          System.exit(1);
                                  }

                            }

                      }
              }
         }catch (IOException e) {
              System.out.println("Error al introducir los datos");
              System.exit(1);
        }
}

}
