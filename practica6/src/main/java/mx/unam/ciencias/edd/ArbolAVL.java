package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
            this.altura =this.altura();
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
          if(this==null)
              return -1;
              else
              return super.altura();
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            if(this==null)
                return " "+"-1/0";
            return this.elemento.toString()+" "+this.altura+ "/"+ this.balance();

        }
        /**
         * Regresael balance de un vértice AVL.
         * @return una el balance de un vértice AVL.
         */
     private int balance(){
         int izq = this.izquierdo!=null ? this.izquierdo.altura():-1;
         int der = this.derecho!=null ? this.derecho.altura():-1;
         return izq -der;
         }
        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
                return(altura==vertice.altura && super.equals(vertice));
        }

    }

    /* Convierte el vértice a VerticeAVL */
    private VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice) {
        return (VerticeAVL)vertice;
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
         return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeAVL agregado = verticeAVL(ultimoAgregado);
         VerticeAVL padre = verticeAVL(agregado.padre);
        rebalanceo(agregado);

    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
      if(elemento==null)
         return;
      VerticeAVL vertice=verticeAVL(super.busca(elemento));
      if (vertice == null)
          return;
      elementos--;
      if(vertice.izquierdo!=null && vertice.derecho!=null)
          vertice = verticeAVL(intercambiaEliminable(vertice));
      if(elementos==0){
           raiz=null;
           return;
       }
       VerticeAVL padre = verticeAVL(vertice.padre);
       super.eliminaVertice(vertice);
       rebalanceo(padre);
      }

      /**
       * Rebalancea el  árbol AVL.Despues de que se le haya agregado o eliminado un vertice
       *   girando a el árbol como sea necesario para rebalancearlo.
       * @param vertice el padre del vertice eliminado o agregado.
       */

      private void rebalanceo(VerticeAVL vertice){
          if(vertice==null)
                return;
          vertice.altura = vertice.altura();
          VerticeAVL verticeIzq = verticeAVL(vertice.izquierdo);
          VerticeAVL verticeDere = verticeAVL(vertice.derecho);
          if(vertice.balance()==-2){
              if(verticeDere.balance()==1){
                    super.giraDerecha(verticeDere);
              }
              super.giraIzquierda(vertice);
          }
          if(vertice.balance()==2){
              if(verticeIzq.balance()==-1){
                  super.giraIzquierda(verticeIzq);
              }
              super.giraDerecha(vertice);
          }
         VerticeAVL padre = verticeAVL(vertice.padre);
         vertice.altura = vertice.altura();
         rebalanceo(padre);
    }


    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
