package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T> >
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
        return aux;
}
}

/* Clase estática privada para adaptadores. */
private static class Adaptador<T extends Comparable<T> >
        implements ComparableIndexable<Adaptador<T> > {

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
      this(coleccion,coleccion.getElementos());
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
   arbol=nuevoArreglo(n);

   for (T elemento : iterable) {
                agrega(elemento);
                acomodoAbajo(elemento);
}
}

/**
 * Agrega un nuevo elemento en el montículo.
 * @param elemento el elemento a agregar en el montículo.
 */
@Override public void agrega(T elemento) {
        if(elementos==arbol.length) {
                T[] arbol2 = nuevoArreglo(2*elementos);
                for (int i = 0; i<elementos; i++) {
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
        if(esVacia()) {
                throw new IllegalStateException();
        }
        T elemento = arbol[0];
//se hace uso del metodo que elimina un elemento cualquiera del arbol
        elimina(elemento);
        return elemento;

}
//Metodo privado que intercambia elementos en el arreglo


/**
 * Elimina un elemento del montículo.
 * @param elemento a eliminar del montículo.
 */
@Override public void elimina(T elemento) {
        if(elemento.getIndice()<0)
                return;
        int indice = elemento.getIndice();
        cambia(elemento,arbol[elementos-1]);
        elementos--;
        arbol[elementos]=null;
        elemento.setIndice(-1);
        reordena(arbol[indice]);
}

//Metodo auxiliar me intercambia 2 elementos del arreglo
private void cambia(T a,T b){
        arbol[a.getIndice()]=b;
        arbol[b.getIndice()]=a;
        int w=b.getIndice();
        b.setIndice(a.getIndice());
        a.setIndice(w);
}

/**
 * Nos dice si un elemento está contenido en el montículo.
 * @param elemento el elemento que queremos saber si está contenido.
 * @return <code>true</code> si el elemento está contenido,
 *         <code>false</code> en otro caso.
 */
@Override public boolean contiene(T elemento) {
        if(elemento.getIndice()<0)
                return false;
        return arbol[elemento.getIndice()].equals(elemento);
}

/**
 * Nos dice si el montículo es vacío.
 * @return <tt>true</tt> si ya no hay elementos en el montículo,
 *         <tt>false</tt> en otro caso.
 */
@Override public boolean esVacia() {
        return elementos==0;
}

/**
 * Limpia el montículo de elementos, dejándolo vacío.
 */
@Override public void limpia() {
        for (int i=0; i<arbol.length; i++ ) {
                this.arbol[i]=null;
        }
        this.elementos=0;
}

/**
 * Reordena un elemento en el árbol.
 * @param elemento el elemento que hay que reordenar.
 */
@Override public void reordena(T elemento) {
        if(elemento==null) {
                return;
        }

        int p=(elemento.getIndice()-1); //variable que obtendra su padre
                  p/=2;

        if(!indiceValido(p) | arbol[p].compareTo(elemento)<=0)
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
        for (int i=0; i<elementos; i++ ) {
                s += arbol[i]+", ";
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
        for(int i=0; i<elementos; i++) {
                if(!arbol[i].equals(monticulo.arbol[i])) {
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
        Lista<Adaptador<T> > l1= new Lista<Adaptador<T> >();
        for (T aux : coleccion) {
                l1.agrega(new Adaptador<T>(aux));
        }
        Lista<T> l2 = new Lista<T>();
        MonticuloMinimo<Adaptador<T> > minimo = new MonticuloMinimo<Adaptador<T> >(l1);
        while(!minimo.esVacia()) {
            T elemento=minimo.elimina().elemento;
                l2.agrega(elemento);
        }
        return l2;

}

///Auxiliaressssssssss

//Me devuelve el indice del hijo derecho del elemento
private int hijodere(int indice){//formulazo que nos dio canek
        return 2*indice+2;
}
//Me devuelve el indice del hijo izquierdo del elemento
private int hijoizq(int indice){//formulazo que nos dio canek
        return 2*indice+1;
}


private void acomodoArriba(T elemento){
         int padre=(elemento.getIndice()-1); //variable que obtendra su padre
            padre/=2;

        if(elemento.compareTo(arbol[padre])>=0) {
                return;
        }
        cambia(elemento,arbol[padre]);
        acomodoArriba(elemento);
}



private void acomodoAbajo(T elemento){
        int izq = hijoizq(elemento.getIndice());
        int der = hijodere(elemento.getIndice());
        if(izq>elementos || der >elementos) {
                return;
        }
      int  min=izq;//asignamos a algun hijo  como minimmo arbitrariamente en este caso lo hice con hijo izquierdo
        if(der<elementos) { //jala tambien si lo hago con derecho
                if(izq<elementos) {
                        if(arbol[der].compareTo(arbol[izq])<0) {
                                min=der;
                        }
                }else{//si el hijo derecho no era valido entonces el minimo es el izquierdo
                        min=der;
                }
        }

        if(arbol[min].compareTo(elemento)<0) {
                cambia(elemento,arbol[min]);
                acomodoAbajo(elemento);
        }
}

//revisa que un indice valido
private boolean indiceValido(int i){
       return !(i<0 || i>=this.arbol.length || arbol[i]==null);
   }
}
