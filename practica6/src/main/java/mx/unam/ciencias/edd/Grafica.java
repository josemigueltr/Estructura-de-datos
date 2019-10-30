package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
           iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
          return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
        return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
           this.elemento=elemento;
           color=Color.NINGUNO;
           vecinos = new Lista<Vertice>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
           return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices=new Lista<Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
         //si el elemento es null o ya esta en la grafica
         if(elemento == null || contiene(elemento)){
             throw new IllegalArgumentException("El elemento ya está en la gráfica");
         }
         Vertice v = new Vertice(elemento);
         vertices.agrega(v);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
//Variables temporales almacenaran en un vertice los eleementos a conectar
        Vertice u =busca(a);
        Vertice v =busca(b);
//si no estan en la grafia alguno de los 2 elementos lanzamos un error
        if(u==null || v==null)
            throw new NoSuchElementException();
  //si son vecinos o un elemento es igual a otro(no se permiten lazos) mandamos un error
        if(sonVecinos(a,b) || a.equals(b))
             throw new IllegalArgumentException();
          u.vecinos.agrega(v);
          v.vecinos.agrega(u);
          aristas++;
    }

//Metodo auxiliar recibe un elemento y regresa el vertice en el que esta contenido el elemento
    private Vertice busca(T elemento){
               for(Vertice v : vertices){
                   if(v.elemento.equals(elemento))
                   return v;
               }
            return null;
           }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
      Vertice v1 = busca(a);
      Vertice v2 = busca(b);
      if(v1==null && v2==null)
           throw new NoSuchElementException();
     if(!sonVecinos(a,b))
         throw new IllegalArgumentException();
              v1.vecinos.elimina(v2);
              v2.vecinos.elimina(v1);
              aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for (Vertice v : vertices){
            if (v.elemento.equals(elemento))
               return true;
            }
        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
      //buscamos al  elemento  a eliminar  en la grafica
      Vertice v1 = buscaVertice(elemento);
          if(v1 == null)
              throw new NoSuchElementException("el elemento no esta contenido en la gráfica");
          for(Vertice vertice : v1.vecinos){
            //eliminamos al vertice de cada una de las listas de adycencias de sus vecinos
              vertice.vecinos.elimina(v1);
              aristas--;
           }
         vertices.elimina(v1);
          }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
           Vertice v1 = busca(a);
           Vertice  v2=busca(b);
           if (v1==null || v2==null)
            throw new NoSuchElementException();
            return v1.vecinos.contiene(v2) && v2.vecinos.contiene(v1);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
      if (!this.contiene(elemento))
          throw new NoSuchElementException();
          Vertice v1=busca(elemento);
        return (v1==null)?null:v1;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || vertice.getClass() != Vertice.class)
            throw new IllegalArgumentException("Vértice inválido");
        Vertice v = (Vertice)vertice;
         v.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
          Cola<Vertice> cola = new Cola<Vertice>();
          for(Vertice a : vertices){
          a.color = Color.NEGRO;
          }
          Vertice v =vertices.getPrimero();
          v.color = Color.ROJO;
          cola.mete(v);
          while(!cola.esVacia()){
          Vertice aux = cola.saca();
          for(Vertice u : aux.vecinos){
              if(u.color == Color.NEGRO){
              u.color = Color.ROJO;
              cola.mete(u);
              }
          }
          }

      for(Vertice u : vertices){
          if(u.color ==Color.NEGRO)
          return false;
      }
      return true;
}


    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
      for(Vertice v : vertices){
              accion.actua(v);
          }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
      Vertice v = buscaVertice(elemento);
      if(v == null){
          throw new NoSuchElementException("El elemento no está en la gráfica");
      }else{
          Cola<Vertice> cola = new Cola<Vertice>();
          for(Vertice a : vertices){
          a.color = Color.ROJO;
          }
          v.color = Color.NEGRO;
          cola.mete(v);
          while(!cola.esVacia()){
          Vertice aux = cola.saca();
          accion.actua(aux);
          for(Vertice u : aux.vecinos){
              if(u.color == Color.ROJO){
              u.color = Color.NEGRO;
              cola.mete(u);
              }
          }
          }
      }
      for(Vertice u : vertices){
          u.color = Color.NINGUNO;
      }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
      Vertice v = buscaVertice(elemento);
      if(v == null){
          throw new NoSuchElementException("El elemento no está en la gráfica");
      }else{
          Pila<Vertice> pila = new Pila<Vertice>();
          for(Vertice a : vertices){
          a.color = Color.ROJO;
          }
          v.color = Color.NEGRO;
          pila.mete(v);
          while(!pila.esVacia()){
          Vertice aux = pila.saca();
          accion.actua(aux);
          for(Vertice u : aux.vecinos){
              if(u.color == Color.ROJO){
              u.color = Color.NEGRO;
              pila.mete(u);
              }
          }
          }
      }
      for(Vertice u : vertices){
          u.color = Color.NINGUNO;
      }
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
      if(vertices.esVacia())
        return true;
    return false;
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
      aristas = 0;
         vertices.limpia();
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
     @Override public String toString() {

              String s = "{";
        for(Vertice v : vertices)
        s += String.format(v.elemento.toString() + ", ");
        s += "}, {";
        for(Vertice v : vertices){
              for(Vertice v1 : v.vecinos){
              if( v1.color != Color.ROJO){
                  s += "(" + v.elemento.toString() + ", " + v1.elemento.toString() + "), ";
              }
              }
           v.color=Color.ROJO;
          }
          s += "}";
          return s;
          }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la gráfica es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        if(getElementos() == grafica.getElementos()){
            if(getAristas() == grafica.getAristas()){
            for(Vertice v : vertices){
                if(!grafica.contiene(v.elemento))
                return false;
                for(Vertice vecino : v.vecinos){
                if(!grafica.sonVecinos(v.elemento, vecino.elemento))
                    return false;
                }
            }
            return true;
            }
        }
        return false;
    }


    private Vertice buscaVertice(T elemento){
              for(Vertice v : vertices){
                  if(v.elemento.equals(elemento))
                  return v;
              }
              return null;
          }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
