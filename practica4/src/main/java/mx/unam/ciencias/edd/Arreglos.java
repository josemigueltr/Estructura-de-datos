package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
      quickSort(arreglo,comparador, 0, arreglo.length-1);
    }
    private static <T>  void quickSort(T[] arreglo, Comparator <T> comparador, int ini, int fin) {
    if(fin <= ini)
     return;
    int i = ini + 1;
    int j = fin;
    while (i < j)
        if (comparador.compare(arreglo[i],arreglo[ini]) > 0 &&  comparador.compare(arreglo[j],arreglo[ini]) <= 0)
            intercambia(arreglo, i++, j--);
        else if (comparador.compare(arreglo[i],arreglo[ini]) <= 0)
            i++;
        else
            j--;
    if(comparador.compare(arreglo[i],arreglo[ini]) > 0)
    i--;
    intercambia(arreglo, i, ini);
    quickSort(arreglo,comparador, ini, i-1);   /*llamada recursivas*/
    quickSort( arreglo,comparador, i+1, fin);
}


    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
      int min = -1;
      for (int i= 0; i < arreglo.length-1; i++){
              min = i;
      for (int j = i+1; j < arreglo.length; j++)
          if (comparador.compare(arreglo[j], arreglo[min])<0)
           min = j;
        intercambia( arreglo,i,min);
      }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

private static <T> void intercambia( T[] arreglo, int i, int j) {
        T guarda = arreglo[j];
        arreglo[j] = arreglo[i];  /*metodo intercambia que se utiliza en todos los algoritmos de ordenacion que intercambia
                                               los elementos que corresponden alos indices que se le pasan*/
        arreglo[i] = guarda;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {

      int ini = 0;
      int fin = arreglo.length-1;
      int mitad = (int) (ini + (fin-ini)/2);  /*obtengo el indice que tomare como la mitad del arreglo*/

      while (ini < fin) {
          mitad = (int) (ini + (fin-ini)/2);
          int comp = comparador.compare(elemento, arreglo[mitad]);
          if (comp == 0)
              return mitad;
          else if (comp == 1)
              ini = mitad + 1;       /*tomo la parte derecha del arreglo*/
          else
              fin = mitad - 1;           /*tomo la parte izquierda del arreglo*/
      }

      if (ini == fin)
          if (comparador.compare(elemento, arreglo[ini]) == 0)
              return ini;

      return -1;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
