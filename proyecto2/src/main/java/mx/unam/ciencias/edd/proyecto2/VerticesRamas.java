package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/**
 * Clase para dibujar Dibujar vertices y Arboles
 */

public class VerticesRamas{
//Variable estatica almacena el xml final
static String xml="<?xml version='1.0' encoding='utf-8'?><svg width='%d' height='%d'>%s %s </svg>" ;
static String balance="<text x='%d' y='%d' font-size='15'  text-anchor='middle' fill='blue' > %s </text>";
/**
 *
   * Obtiene un arbol binario para graficar.
   * @param Tree tipo de arbol binario a graficar
   * @return Codigo SVG del arbol a graficar
   */
  public static String dibujaTree(ArbolBinario<Integer> Tree) {
      VerticeArbolBinario<Integer> raiz = Tree.raiz();
      int altura = Tree.altura() * 70;
      int anchura = Anchura(Tree);
      dibujaArbolBinario(raiz,0,0,anchura/2);
      return String.format(xml,anchura,altura,ArbolesSVG.Ramas,ArbolesSVG.arbol);
     }

     /**
        * Obtiene un la anchura maxima de un arbol
        * @param Tree arbol del cual se calculara su anchura
        * @return anchura del arbol
        */

   private static int Anchura (ArbolBinario<Integer> ab) {
    	 int numeroHojas = (int) Math.pow(2,ab.altura());
   		 return (numeroHojas+(numeroHojas/2)+2)*(15*2);
	   }

  /**
   *
     * Metodo auxiliar que dibuja los vertices y aristas de larbol recursivamente
     *
     * @param Raiz, raiz del arbol a graficar
     * @param cord, destancia ala que se dibujara el hijo
     * @param x, coordenada  horizontal de la raiz del arbol
     * @param y, coordenada  vertical de la raiz del arbol
     */
  private static void dibujaArbolBinario(VerticeArbolBinario<Integer> raiz, int cord, int y,int x) {
      ArbolesSVG.arbol += DibujaVertices(raiz,x,y+40);
      if (raiz.hayIzquierdo()) {
          int mi = (x - cord) / 2;
          ArbolesSVG.Ramas +=ArbolesSVG.utils.linea(x,y+50,x-mi,y+90);
          dibujaArbolBinario(raiz.izquierdo(),cord,y+50,x-mi);
       }
      if (raiz.hayDerecho()) {
          int md = (x - cord) / 2;
          ArbolesSVG.Ramas+= ArbolesSVG.utils.linea(x,y+50,x+md,y+90);
          dibujaArbolBinario(raiz.derecho(),x,y+50,x+md);
       }
    }

     /**
       * Metodo auxiliar que dibuja los vertices del arbol
       *
       * @param v, vertice a graficar
       * @param x, coordenada  horizontal del vertice
       * @param y, coordenada  vertical del vertice
       */
    private static String DibujaVertices(VerticeArbolBinario<Integer> v, int x,int y){
        String linea = v.toString();
        String color="white";
        String letracolor="black";
        String balances="";
        if(ArbolesSVG.vertice.equals("Rojinegro")){
           color = linea.contains("R") ? "red" : "black";
           letracolor="white";
        }
        else if(ArbolesSVG.vertice.equals("AVL")){
           String[] vBalance = linea.toString().split(" ");
           balances=String.format(balance,x,y-20,vBalance[1]);
        }
        linea = ArbolesSVG.utils.circuloConNumero(v.get(),x,y,color,letracolor)+ balances;
        return linea;
    }

}
