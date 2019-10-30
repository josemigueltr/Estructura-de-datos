package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus''''
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
          pila = new Pila<Vertice>();
            Vertice v = raiz;
            while (v != null) {
                pila.mete(v);
                v = v.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
          return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
          Vertice v = pila.saca();
             Vertice vi;
             if (v.hayDerecho()) {
                 vi = v.derecho;
                 while (vi != null) {
                     pila.mete(vi);
                     vi = vi.izquierdo;
                 }
             }
             return v.elemento;
        }

    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
      if (elemento == null)
           throw new IllegalArgumentException();
           Vertice v=nuevoVertice(elemento);
        elementos++;
        ultimoAgregado=v;
        if(raiz==null){
            raiz=v;
            return;
        }
        agrega(raiz,v);
        }

        private void agrega(Vertice a, Vertice b){

            if(b.elemento.compareTo(a.elemento)<=0){
               if(a.izquierdo==null){
                   a.izquierdo=b;
                   b.padre=a;
                   return;
               }
               agrega(a.izquierdo,b);
           }else{
            if(a.derecho==null){
               a.derecho=b;
               b.padre=a;
               return;
           }
           agrega(a.derecho,b);
       }
      }



    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
      if(elemento==null){
           return;
       }
       Vertice v = vertice(busca(elemento));
       if(v==null){
           return;
         }
           elementos--;
           if(elementos==0){
              raiz=null;
              return;
          }
          if(v.hayIzquierdo() && v.hayDerecho()){
           v=intercambiaEliminable(v);     //si el vertice a leiminar tiene 2 hijos
       }
       eliminaVertice(v); //con este metodo se manda a eliminar el vertice ,sin importar si tiene hijo izquierdo , o derecho.
     }


//metodo que me regresa el primer vertice con hijo derecho==null en la rama izquierda del vertice a eliminar.
    private Vertice maximoEnSubArbol(Vertice v){
       if(v.derecho ==null){
          return v;
      }
      return maximoEnSubArbol(v.derecho);//me regresa un vertice con un valor para poder intercambiar el valor de lverrtice a eliminar
                                           //para no afectar el orden del arbol.
 }
    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
      Vertice v = maximoEnSubArbol(vertice.izquierdo);
      T t = v.elemento;
      v.elemento=vertice.elemento;   //me Intercambia el valor del vertice a eliminar con el primero que enciente que tenga alo mas 1 hijos
      vertice.elemento = t;             //no sabemos cual hijo sera si izq o der
return v;

    }



    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
protected void eliminaVertice(Vertice vertice) {
  if(vertice.padre!=null){ // verificamos si el vertice a liminar tiene padre
            if(vertice.padre.izquierdo == vertice){
               if(vertice.izquierdo!=null){
                  vertice.padre.izquierdo=vertice.izquierdo;
                  vertice.izquierdo.padre=vertice.padre;
                  return;
              }
              if(vertice.derecho!=null){                      //reviso si el vertice a eliminar es hijo izquierdo o derecho
                  vertice.padre.izquierdo=vertice.derecho;
                  vertice.derecho.padre=vertice.padre;
                  return;
              }
              vertice.padre.izquierdo=null;
          }

          else{
           if(vertice.izquierdo!=null){
              vertice.padre.derecho=vertice.izquierdo;  //si no es izquierdo entonces es derecho
              vertice.izquierdo.padre=vertice.padre;
              return;
          }
          if(vertice.derecho!=null){
              vertice.padre.derecho=vertice.derecho;
              vertice.derecho.padre=vertice.padre;
              return;
          }
          vertice.padre.derecho=null;
      }


  }else{   // en este caso el vertice a leminar no tiene padre
    if(vertice.izquierdo!=null){
       raiz=vertice.izquierdo;
       vertice.izquierdo.padre=null;
       return;
   }
   if(vertice.derecho!=null){
       raiz=vertice.derecho;
       vertice.derecho.padre=null;
       return;
   }
  }
      }



    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <tt>null</tt> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
      return busca(raiz,elemento);
    }

   private Vertice busca(Vertice vertice, T elemento) {
            if (vertice == null)
                return null;
            if (elemento.equals(vertice.elemento))
                return vertice;
            return elemento.compareTo(vertice.elemento) <= 0 ?  // si el elemento es menor la recursion se hace ala izquierda , si no se hace la recursion ala derecha
                    busca(vertice.izquierdo,elemento) :
                    busca(vertice.derecho,elemento);
        }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {

      if ( !vertice.hayIzquierdo())
           return;

       Vertice v = vertice(vertice);
       Vertice vizq = v.izquierdo;

       vizq.padre = v.padre;
       if( v != raiz){

           if(v.padre.izquierdo == v){
               v.padre.izquierdo = vizq;
             }
           else                             //si el vertice sobre el que se va a girar no es la raiz
               {
                 v.padre.derecho = vizq;
               }

             }
       else
           {
             raiz = vizq;
           }

       v.izquierdo = vizq.derecho;
      if(vizq.hayDerecho())   //si hay derecho entonces se le pega al vertice sobre le uqe se giro
           vizq.derecho.padre = v;
       vizq.derecho = v;
v.padre = vizq;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */




  public void giraIzquierda(VerticeArbolBinario<T> vertice) {
    if ( !vertice.hayDerecho())    ///lo mismo que con gira derecha solo cambian las referencias
                  return;

          Vertice v = vertice(vertice);
          Vertice vdir = v.derecho;

          vdir.padre = v.padre;
          if( v != raiz) {

                  if(v.padre.izquierdo== v) {
                          v.padre.izquierdo = vdir;
                  }
                  else
                  {
                    v.padre.derecho = vdir;
                  }

          }
          else
          {
                  raiz = vdir;
          }

          v.derecho = vdir.izquierdo;
          if(vdir.hayIzquierdo())
            vdir.izquierdo.padre = v;
          vdir.izquierdo = v;
          v.padre = vdir;


}

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
            dfsPreOrder(accion,raiz);
    }

    private void dfsPreOrder(AccionVerticeArbolBinario<T> accion, Vertice v){
      if(raiz==null || v==null){
          return;
      }
      accion.actua(v);
      dfsPreOrder(accion,v.izquierdo);
      dfsPreOrder(accion,v.derecho);
  }
    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
         dfsInOrder(accion,raiz);
    }

    private void dfsInOrder(AccionVerticeArbolBinario<T> accion, Vertice v){
          if(raiz==null || v==null){
              return;
          }

          dfsInOrder(accion,v.izquierdo);
          accion.actua(v);
          dfsInOrder(accion,v.derecho);
      }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
       dfsPostOrder(accion,raiz);
    }

    private void dfsPostOrder(AccionVerticeArbolBinario<T> accion, Vertice v){
          if(raiz==null || v==null){
              return;
          }

          dfsPostOrder(accion,v.izquierdo);
          dfsPostOrder(accion,v.derecho);
            accion.actua(v);
      }


    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
