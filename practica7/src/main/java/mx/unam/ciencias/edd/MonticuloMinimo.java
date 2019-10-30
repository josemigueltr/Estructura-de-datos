package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
         return indice<elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
          if(indice>=elementos)
             throw new NoSuchElementException();
         T aux = get(indice);
         indice++;
         return  aux;
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            this.elemento=elemento;
            this.indice=-1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
          return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            this.indice=indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
                   return this.elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100); /* 100 es arbitrario. */
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
int aux=0;
arbol=nuevoArreglo(n);
elementos=n;
for(T actual :iterable){
  arbol[aux]=actual;
  actual.setIndice(aux++);
}
for(int i=elementos-1;i>=0;i--){
      T elemento = arbol[i];
         acomodoAbajo(elemento);}
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
       if(elementos==arbol.length){
           T[] arbol2 = nuevoArreglo(2*elementos);
              for (int i = 0;i<elementos ; i++) {
                 arbol2[i]= arbol[i];
              }
          arbol = arbol2;
     }
          elementos++;
          arbol[elementos-1] = elemento;
          elemento.setIndice(elementos-1);
          reordena(elemento);
  }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
      if(esVacia()){
             throw new IllegalStateException();
         }
         T elemento = arbol[0];
//se hace uso del metodo que elimina un elemento cualquiera del arbol
         elimina(elemento);
         return elemento;

    }
//Metodo privado que intercambia elementos en el arreglo
private void cambia(T elemento,T ultimo){
  int aux=d.getIndice();
    arbol[i.getIndice()]=d;
    arbol[d.getIndice()]=i;
    d.setIndice(i.getIndice());
    i.setIndice(aux);
  }


    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
      if(elemento.getIndice()<0 ||  elemento.getIndice()>=elementos)
         return;
      cambia(elemento,arbol[elementos-1]);
      //almaceno el indice de la pocision del elemento para reordenar al elemento que se
       //coloque en esa posicion.
       int indic = elemento.getIndice();
        elementos--;
        arbol[elementos]=null;
        elemento.setIndice(-1);
        reordena(arbol[indic]);
      }


    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        if(elemento.getIndice()<0 ||  elemento.getIndice()>=elementos)
           return false;

           return arbol[elemento.getIndice()].equals(elemento);
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacia() {
        return elementos>0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        elementos=0;
        for(int i=0; i< arbol.length;i++)
           arbol[i]=null;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
  if(elemento==null){
      return;
  }
  int p=(elemento.getIndice()-1);
  if(p!=-1){
      p/=2;
  }
  if( arbol[p].compareTo(elemento)<=0)
      acomodoAbajo(elemento);
  else{
      acomodoArriba(elemento);
  }
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
         return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        if(i<0 || i>= elementos)
         throw new NoSuchElementException();
         return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
      String s= "";
              for (int i=0;i<elementos;i++ ) {
                 s += arbol[i].toString()+", ";
             }
             return s;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
            if(elementos!=monticulo.elementos)
                return false;
            for(int i=0; i<elementos ;i++){
                if(!arbol[i].equals(monticulo.arbol[i])){
                   return false;
                 }
            }
         return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */

    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
      Lista<Adaptador<T>> lAdaptador = new Lista<Adaptador<T>>();
                  for (T aux : coleccion) {
                      lAdaptador.agrega(new Adaptador<T>(aux));
                  }
                  Lista<T> l = new Lista<T>();
                  MonticuloMinimo<Adaptador<T>> minimo = new MonticuloMinimo<Adaptador<T>>(lAdaptador);
                  while(!minimo.esVacia()) {
                   l.agrega(minimo.elimina().elemento);
               }
               return l;

    }


    private void acomodoArriba(T indexable){

           int padre = (int)Math.floor((indexable.getIndice()-1)/2);
           if(indexable.getIndice()<0 || indexable.getIndice()>=elementos || padre<0){
               return;
           }

           if(indexable.compareTo(arbol[padre])>=0){
               return;
           }
           if(indexable.compareTo(arbol[padre])<0){
               cambia(indexable,arbol[padre]);
           }
           acomodoArriba(indexable);
       }


       private void acomodoAbajo(T elemento){
         int izq, der;
   int min;
   if (elemento == null) {
       return;
   }
   izq = elemento.getIndice() * 2 + 1;
   der = elemento.getIndice() * 2 + 2;
   if (!this.indiceValido(izq) && !this.indiceValido(der)) {
       return;
   }

   min = der;
   if (this.indiceValido(izq)) {
       if (this.indiceValido(der)) {
           if (arbol[izq].compareTo(arbol[der]) < 0) {
               min = izq;
           }
       } else {
           min = izq;
       }
   }

   if (arbol[min].compareTo(elemento) < 0) {
       this.cambia(elemento, arbol[min]);
       this.acomodoAbajo(elemento);
}
           }

///___________________________________________
private boolean hayPadre(int indice){
   return indice!=0?true:false;
}
private int hDerecho(int indice){
   return (indice*2)+2;
}
private boolean hayHDerecho(int indice){
   return hDerecho(indice)<elementos?true:false;
}
private int hIzquierdo(int indice){
   return hDerecho(indice)-1;
}
private boolean hayHIzquierdo(int indice){
   return hIzquierdo(indice)<elementos?true:false;
}
private boolean indiceValido(int i){
        return !(i<0 || i>=this.arbol.length || arbol[i]==null);
    }
}
