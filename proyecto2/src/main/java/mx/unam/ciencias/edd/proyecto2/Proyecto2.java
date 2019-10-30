package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import mx.unam.ciencias.edd.*;

public class Proyecto2{


	public static void main(String[] args) throws Exception {
		BufferedReader br = null;
		GraficaLQP LQP=new GraficaLQP();
    ArbolesSVG ARB=new ArbolesSVG();
		Grafica Gra=new Grafica();

 if(args.length==0){
     br = new BufferedReader(new InputStreamReader(System.in));
  }
	else{
			try{
	        br = new BufferedReader(
                 new InputStreamReader(
                 new FileInputStream(args[0]) ) );
      }
			catch(IOException e) {
	         System.out.println("No se encontro el archivo");
  	       return;
       }
     }


 Archivo.lectura(br);
 EstructurasDeDatos estructuraE=Archivo.estructuraE;
 Lista<Integer> lnum=Archivo.lnum;
		try {
			switch (estructuraE) {
        case MonticuloArreglo:
				case Lista:
					System.out.println(LQP.lista(lnum,estructuraE));
					break;
				case Pila:
				case Cola:
					System.out.println(LQP.pilacola(estructuraE,lnum));
					break;
				case MonticuloMinimo:
				case ArbolBinarioCompleto:
			  case ArbolRojinegro:
			  case ArbolAVL:
				case ArbolBinarioOrdenado:
			   System.out.println(ARB.getTree(lnum,estructuraE));
				 break;
				 case Grafica:
				break;
			}
		} catch (NumberFormatException e) {
			System.out.println("Se deben introducir numeros enteros.");
		}

	}
}
