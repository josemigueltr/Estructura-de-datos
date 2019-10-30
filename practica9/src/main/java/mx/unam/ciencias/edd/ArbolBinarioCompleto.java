package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        public Iterador() {
          cola = new Cola<>();
          if(raiz != null)
              cola.mete(raiz);;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
     return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
         Vertice v=cola.saca();
         if(v.hayIzquierdo())
                 cola.mete(v.izquierdo);
         if(v.hayDerecho())
                 cola.mete(v.derecho);
         return v.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {

      if(elemento==null)
             throw new IllegalArgumentException();
         Vertice a = nuevoVertice(elemento);
         elementos++;
         if(raiz==null)
             raiz = a;
         else{
             Vertice b = verticenulo();// obtengo el ultimo vertice que no tenga hijo izquierdo o derecho por bfs

             if(!b.hayIzquierdo()){
                 b.izquierdo = a;     //si no tieen izquierdo ahi se agrega el nuevo elemento
                 a.padre = b;
                 return;
             }
             if (!b.hayDerecho()) {
                 b.derecho = a;  // analogo a izquierdo
                 a.padre = b;
             }
         }
   }
   //metodo auxiliar que me regresa el primer vertice sin hijo izquierdo o derecho para ahi anexar el nuevo vertice
   private Vertice verticenulo(){
        if (this.esVacia()) {
            return null;
        }
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);
        while(cola.cabeza!=null){
            Vertice b= cola.saca();
            if(b.hayIzquierdo())
                cola.mete(b.izquierdo);
            if(b.hayDerecho())
                cola.mete(b.derecho);
            if(!b.hayIzquierdo() || !b.hayDerecho())
                return b;
        }
        return null;
    }


    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
      if(elemento==null)
                return;
            Vertice vertice = (Vertice)busca(elemento);//busca si el elemento a eliminar esta en el arbol
            if (vertice==null)
                return;
            elementos--;
            if(this.elementos==0)
                this.raiz=null;
            else{
                Vertice elimina = elimina();// metodo que me regresa el ultimo vertice del arbol(hoja)
                if(elimina.padre.izquierdo.equals(elimina)){ // si el vertice es hijo izquierdo entonces se  intercambian valores y se elimina
                  vertice.elemento = elimina.elemento;
                    elimina.padre.izquierdo = null;
                    elimina.padre = null;
                }else{
                    vertice.elemento = elimina.elemento; // en caso contrario es el hijo derecho, mismo procedimiento
                    elimina.padre.derecho = null;
                    elimina.padre = null;
                }
            }
    }
    //me regresa el ultimo vertice(hoja)
    private Vertice elimina(){
       Cola<Vertice> cola = new Cola<Vertice>();
       cola.mete(raiz);
       Vertice vertice = null;
       while(cola.cabeza != null){
           vertice = cola.saca();
               if(vertice.hayIzquierdo())
                   cola.mete(vertice.izquierdo);
               if(vertice.hayDerecho())
                   cola.mete(vertice.derecho);
       }
       return vertice;
       }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
      if(raiz==null)
      return -1;
  int p = (int) Math.floor((Math.log(elementos))/(Math.log(2)));
  return p;
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
      if (raiz==null) {
                return;
              }
          Cola<Vertice> cola = new Cola<Vertice>();
              while(!cola.esVacia()){
                Vertice v = cola.saca();
                accion.actua(v);
                if(v.hayIzquierdo())
           cola.mete(v.izquierdo);
           if(v.hayDerecho())
           cola.mete(v.derecho);
         }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
