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
 * <p>Clase que permite manejar las manera en uq se representaran las lineas en los archivos y en consola</p>
 *
 *
 */

public class ManejoArchivos {


    /**
     * Ordena las lineas del archivo y las imprime en pantalla
     *@param Archivo el archivo en le que se  almacenaron las lineas leidas
     *@throw FileNotFoundException si no encuentra el archivo
     **/
    static void imprimir(Archivos archivo) {

        if (archivo != null) {
            Comparador a = new Comparador();
            Lista < String > b = archivo.lista.mergeSort(a);
            for (String s: b) {
                System.out.println(s);
            }
        } else
            System.out.println("El archivo no puede ser vacio");
    }

    /**
     * Ordena las lineas del archivo
     *@param Archivo el archivo en le que se  almacenaron las lineas leidas
     *@return Lista  ordenada
     **/
static Lista<String> ordena (Archivos archivo){
  if (archivo != null){
    Comparador a = new Comparador();
    Lista < String > b = archivo.lista.mergeSort(a);
  }
  return b;

}

    /**archivo.Escribirarchivos(nombreArchivo);
     * Ordena las lineas del archivo y las imprime en  orden inverso pantalla
     *@param Archivo el archivo en le que se  almacenaron las lineas leidas
     *@throw FileNotFoundException si no encuentra el archivo
     **/
    static void reversa2(Archivos archivo) {
        if (archivo != null) {
          if(args.length==1){
          archivo.EntradaEstandar();
            Comparador a = new Comparador();
            Lista < String > b = archivo.lista.mergeSort(a);
            b = b.reversa();
            for (String s: b) {
                System.out.println(s);
            }
        }

    }
  static void impresion(Archivos archivo,boolean BanderaR,boolean BanderaO,String nombreArchivo)){
    Lista <String> l=ordena(archivo);
    if(BanderaR){
      l = l.reversa();
    }
    if(BanderaO){
      archivo.Escribirarchivos(nombreArchivo,l);
    }




  }

}
