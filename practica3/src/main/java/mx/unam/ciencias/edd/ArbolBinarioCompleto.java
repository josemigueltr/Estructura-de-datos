package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

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

        /* Constructor que recibe la raíz del árbol. */
        public Iterador() {
            cola = new Cola<vertice>();
            if(raiz!=null)
            cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return cola.mira()!=null;
        }

        /* Regresa el siguiente elemento en orden BFS. */
      @Override public T next() {
        if(cola.mira()=null)
                throw NoSuchElementException();
        Vertice v=cola.saca();
        if(v.izquierdo!=null)
                cola.mete(v.izquierdo);
        if(v.derecho!=null)
                cola.mete(v.derecho);
        return v.elemento;


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
        if (elemento == null)
        throw new IllegalArgumentException();
     Vertice v = nuevoVertice(elemento);
      elementos++;
     if (raiz==null){
      raiz = v;
      return;
    }
  else{
      cola = new Cola<vertice>();
      cola.mete(raiz);
      while(!cola.esVacia())
      {
      Vertice a = cola.saca();
      if(a.izquierdo==null){
         a.izquierdo=v;
         v.padre=a;
         return;
       }
         if(a.derecho==null){
         a.derecho=v;
         v.padre=a;
         return;
             }
      cola.mete(v.izquierdo);
      cola.mete(v.derecho);
}
       }

     }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = Vertice(this.busca(elemento))
        if (vertice ==null)
        return;
      this.elementos--;
      if(elementos==0){
          this.raiz=null;
         return;
        }
        cola = new Cola<vertice>();
          cola.mete(raiz);
          while(!cola.esVacia()){
         Vertice elimina = cola.saca();
          if(elimina.hayIzquierdo()){
            cola.mete(elimina.izquierdo);
          }
          if(elimina.hayDerecho()){
            cola.mete(elimina.derecho);
          }
        }

        vertice.elemento=elimina.elemento;
        if(elimina.padre.hayDerecho)
        elimina.padre.derecho=null;
        else
        elimina.padre.izquierdo=null;
          elimina.elemento=null;
        elimina.padre==null;

    }



    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
  return Math.floor((Math.log10(elementos)/Math.log10(2));
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
                cola=new Cola<Vertice>();
                while(!cola.esVacia()){
                  Vertice v= cola.saca()
                  actua(v);
                  if(v.hayIzquierdo)
             cola.mete(v.izquierdo);
             if(v.hayDerecho)
             cola.mete(v.derecho)}
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
