package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * Clase abtracta para estructuras lineales restringidas a operaciones
 * mete/saca/mira.
 */
public abstract class MeteSaca<T> {

    /**
     * Clase interna protegida para nodos.
     */
    protected class Nodo {
        /** El elemento del nodo. */
        public T elemento;
        /** El siguiente nodo. */
        public Nodo siguiente;

        /**
         * Construye un nodo con un elemento.
         * @param elemento el elemento del nodo.
         */
        public Nodo(T elemento) {
            this.elemento=elemento;
        }
    }

    /** La cabeza de la estructura. */
    protected Nodo cabeza;
    /** El rabo de la estructura. */
    protected Nodo rabo;

    /**
     * Agrega un elemento al extremo de la estructura.
     * @param elemento el elemento a agregar.
     */
    public abstract void mete(T elemento);

    /**
     * Elimina el elemento en un extremo de la estructura y lo regresa.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T saca() {
        if(cabeza==null){
          throw new NoSuchElementException();
        }
      T e =cabeza.elemento;
    if(cabeza==rabo)
    cabeza=rabo=null;            /*si solo hay un elemento  las lista quedara vacia*/
    else{
      cabeza = cabeza.siguiente;  /*De lo contrario se anexa el elemento en la posicion siguiente*/
    }

        return e;

    }

    /**
     * Nos permite ver el elemento en un extremo de la estructura, sin sacarlo
     * de la misma.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T mira() {
      if(cabeza==null)
      throw new NoSuchElementException();
      return cabeza.elemento;
    }

    /**
     * Nos dice si la estructura está vacía.
     * @return <tt>true</tt> si la estructura no tiene elementos,
     *         <tt>false</tt> en otro caso.
     */
    public boolean esVacia() {
        return cabeza==null;
    }

    /**
     * Compara la estructura con un objeto.
     * @param object el objeto con el que queremos comparar la estructura.
     * @return <code>true</code> si el objeto recibido es una instancia de la
     *         misma clase que la estructura, y sus elementos son iguales en el
     *         mismo orden; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass())
            return false;
        @SuppressWarnings("unchecked") MeteSaca<T> m = (MeteSaca<T>)object;
        Nodo n1 = m.cabeza;
  Nodo n2 = cabeza;
  while (n1 != null && n2 != null) {
      if (n1.elemento.equals(n2.elemento)){
      n1 = n1.siguiente;       /*compara si los elementos de las estructuras son iguales*/
      n2 = n2.siguiente;
    }else{
      return false;
    }
  }

 if( n1 == null && n2 == null)
  return true;   /*si  n1 y n2 son iguales a null eso quires decir que tienen la misma cantidad de elementos*/
  else
  return false;


    }
}
