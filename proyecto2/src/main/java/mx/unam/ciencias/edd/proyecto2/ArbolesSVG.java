package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
/**
 * Clase para dibujar Arboles binarios de todo tipo.
 */
public class ArbolesSVG {

 //Variable tipo String Almacenara el codigo SVG de la estructura

  //Vertices del arbol
   static String arbol="";
  //Tipo de vertice del arbol, Rojinegro,AVL
   static String vertice="VACIO";
  // variable estatica almacena las aristas del arbol
   static String Ramas="";
  // variable  estatica
   static Herramientas utils;

   public ArbolesSVG () {
      utils = new Herramientas();
   }

   /**
      * Obtiene elementos de un lista para construir un MonticuloMinimo.
      * @param lista Lista de Integers donde se sacaran los elementos.
      * @return ArbolBinarioCompleto creado apartir de los elementos de la lista
      */

  public static ArbolBinarioCompleto<Integer> Monticulo(Lista<Integer> lista){
      Lista<ValorIndexable<Integer>> li  = new Lista<>();
      for (int i:lista) {
          li.agrega(new ValorIndexable<Integer>(i, i));
       }
      MonticuloMinimo<ValorIndexable<Integer>> monticulo = new MonticuloMinimo<ValorIndexable<Integer>>(li);
      ArbolBinarioCompleto<Integer> abc = new ArbolBinarioCompleto<Integer>();
		  for (ValorIndexable<Integer> i:monticulo) {
			    abc.agrega(i.getElemento());
	     }
		 return abc;
   }
    	/**
         * Obtiene elementos de un lista para construir ArbolBinarioCompleto.
         *
         * @param lista Lista de Integers donde se sacaran los elementos.
         * @return ArbolBinarioCompleto creado apartir de los elementos de la lista
         */
  public static ArbolBinarioCompleto<Integer> creaArbol (Lista<Integer> lista){
       ArbolBinarioCompleto<Integer> arbolC = new ArbolBinarioCompleto<Integer>(lista);
       return arbolC;
  }
  /**
   *
     * Obtiene elementos de un lista para construir ArbolBinarioRojinegro
     * @param lista Lista de Integers donde se sacaran los elementos.
     * @return ArbolBinarioRojinegro creado apartir de los elementos de la lista
     */

  public static ArbolRojinegro<Integer> creaArbolRJN(Lista<Integer> lista){
      ArbolRojinegro<Integer> arbolRJN = new ArbolRojinegro<Integer>(lista);
      return arbolRJN;
  }
  /**
   *
     * Obtiene elementos de un lista para construir ArbolBinarioAVL
     * @param lista Lista de Integers donde se sacaran los elementos.
     * @return ArbolAVL creado apartir de los elementos de la lista
     */
  public static ArbolAVL<Integer> creaArbolAVL (Lista<Integer> lista) {
        ArbolAVL<Integer> AVL = new ArbolAVL<Integer>(lista);
      	return AVL;
    	}

      /**
       *
         * Obtiene elementos de un lista para construir ArbolBinarioOrdenado
         * @param lista Lista de Integers donde se sacaran los elementos.
         * @return ArbolBinarioOrdenado creado apartir de los elementos de la lista
         */
 public static ArbolBinarioOrdenado<Integer> creaArbolOrd (Lista<Integer> lista) {
        ArbolBinarioOrdenado<Integer> ORD = new ArbolBinarioOrdenado<Integer>(lista);
        return ORD;
      }


  /**
	 * Realiza el codigo SVG de una Arbol
	 * @param Lista Lista de Integers
   *, @param estructura estructura a graficar
	 * @return  Codigo de la lista en SVG
	 */
	public String getTree (Lista<Integer> lista,EstructurasDeDatos estructura) {
     ArbolBinario<Integer> Tree=null;
     switch(estructura){
        case MonticuloMinimo:
           Tree=Monticulo(lista);
           break;
        case ArbolBinarioCompleto:
 				   	Tree = creaArbol(lista);
            break;
        case ArbolRojinegro:
            Tree=creaArbolRJN(lista);
            vertice="Rojinegro";
            break;
        case  ArbolAVL:
             Tree=creaArbolAVL(lista);
             vertice="AVL";
             break;
        case  ArbolBinarioOrdenado:
               Tree=creaArbolOrd(lista);
               break;

       	}
      String m=VerticesRamas.dibujaTree(Tree);
      return m;
      }

}
