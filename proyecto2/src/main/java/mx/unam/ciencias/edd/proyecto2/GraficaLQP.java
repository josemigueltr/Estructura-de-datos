package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
/**
 * Clase para dibujar Listas,Colas y Pilas.
 */
public class GraficaLQP {

//Variable de tipo utils que nos ayudara a obtener los elementos necesarios
 private Herramientas utils;
 //Variable tipo String Almacenara el codigo SVG de la estructura
 static String xml="<?xml version='1.0' encoding='utf-8'?><svg width='%d' height='%d'>%s</svg>";


//Constructor inicializa la variable utils
  public GraficaLQP () {
		utils = new Herramientas();
	}

  	/**
  	 *
       * Obtiene elementos de un lista para meterlos en una cola<.
       *
       * @param l Lista de Integers donde se sacaran los elementos.
       * @param lnum Lista de los elementos.
       * @return cola creada apartir de los elementos de la lista
       * @throws NumberFormatException si alguno de los elementos no es entero.
       */

  public static Cola<Integer> obtenerElementosCola (Lista<Integer> lnum) throws NumberFormatException {
     Cola<Integer> p=new Cola<Integer>();
     for (int i: lnum) {
        p.mete(i);
     }
     return p;
	}

  	/**
  	 *
       * Obtiene elementos de un lista para meterlos en una Pila.
       *
       * @param l Lista de Integers donde se sacaran los elementos.
       * @param lnum Lista de los elementos.
       * @return Pila creada apartir de los elementos de la lista
       * @throws NumberFormatException si alguno de los elementos no es entero.
       */

  public static Pila<Integer> obtenerElementosPila (Lista<Integer> lnum) throws NumberFormatException {
      Pila<Integer> p=new Pila<Integer>();
      for (int i: lnum) {
         p.mete(i);
      }
      return p;
  }

  /**
     * Obtiene elementos de un lista para construir un MonticuloArreglo.
     * @param lista Lista de Integers donde se sacaran los elementos.
     * @return MonticuloArreglo creado apartir de los elementos de la lista
     */

  public static MonticuloMinimo<ValorIndexable<Integer>>  Monticulo(Lista<Integer> lista){
     Lista<ValorIndexable<Integer>> li  = new Lista<>();
     for (int i:lista) {
         li.agrega(new ValorIndexable<Integer>(i, i));
       }
      MonticuloMinimo<ValorIndexable<Integer>> monticulo = new MonticuloMinimo<ValorIndexable<Integer>>(li);
          return monticulo;
       }


  /**
	 * Realiza el svg de una Lista y u MonticuloArreglo
   * @param l Lista de Integers
   * @param estructuraE Estructura de datos que se graficara
	 * @return codifo de lista MonticuloArreglo en svg
	 */
	public String lista (Lista<Integer> l,EstructurasDeDatos estructuraE) {
  		String lista = "";
	  	int width=45, altura= 25;
		  int  largoSVG = 25;
      int i=l.getLongitud();
      int x=25;//variable que determina la posicion de rectangulo
      switch(estructuraE){
          case Lista:
             for(int elemento: l){
                lista += utils.rectanguloConNumero(elemento,x,40,width, altura,"black");
                if(i>1){
                   lista += utils.dobleFlecha(x+45, 58);
                   i--;
                 }
                x+=65;
              }
           break;
        case MonticuloArreglo:
            MonticuloMinimo<ValorIndexable<Integer>> monticulo=Monticulo(l);
            for (ValorIndexable<Integer> elemento:monticulo) {
            lista +=utils.rectanguloConNumero(elemento.getElemento(),x,40,width, altura,"black");
            x+=45;
            }
            break;
          }

               largoSVG +=x;
		return String.format(xml,largoSVG,100,lista);
	}

  	/**
  	 * Realiza el svg de una pila o cola
  	 * @param ms EstructurasDeDatos, lista de enteros
  	 * @return el MeteSaca en svg
  	 */
  	public String pilacola (EstructurasDeDatos estructuraE, Lista<Integer> lnum) {
  	  	String mss = "";
        int width=45, altura= 25;
        int  largoSVG = 25;
        int x=25;
        int e;
        MeteSaca<Integer> pilacola=null;
        switch (estructuraE) {
           case Pila:
              pilacola = obtenerElementosPila(lnum);
              break;
           case Cola:
              pilacola=obtenerElementosCola(lnum);
        }
  	   while (!pilacola.esVacia()) {
  			   e = pilacola.saca();
  			   mss += utils.rectanguloConNumero(e,x,40,width, altura,"black");
  			   if (!pilacola.esVacia()) {
  				     mss += utils.flechaDerecha(x+45, 58);
  			   }
  		    x+=65;
  	   }
  	 largoSVG += x;
    	return String.format(xml,largoSVG,100, mss);
  }

}
