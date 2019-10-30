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
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* La lista de vecinos del vértice. */
        public Lista<Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
          this.elemento=elemento;
          color=Color.NINGUNO;
          vecinos = new Lista<Vecino>();
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
            return this.color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            this.indice=indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
          return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
          if (this.distancia < vertice.distancia)
              return -1;
          if (this.distancia > vertice.distancia)
              return 1;
          return 0;
      }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
                this.peso=peso;
                this.vecino=vecino;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
           return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
             return this.vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            return vecino.color;
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
       return vecino.vecinos;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<Vertice>();
        aristas = 0;
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
      if(elemento == null || contiene(elemento)){
          throw new IllegalArgumentException("El elemento ya está contenido en la Grafica");
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
          conecta(a,b,1);
      }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
       if (peso <= 0)
          throw new IllegalArgumentException("no se permiten pesos negativos");
      //Variables temporales almacenaran en un vertice los eleementos a conectar
        Vertice u =busca(a);
        Vertice v =busca(b);
//si no estan en la grafia alguno de los 2 elementos lanzamos un error
        if(u==null || v==null)
            throw new NoSuchElementException();
  //si son vecinos o un elemento es igual a otro(no se permiten lazos) mandamos un error
        if(sonVecinos(a,b) || a.equals(b))
           throw new IllegalArgumentException();
        Vecino B = new Vecino(v,peso);
        Vecino A = new Vecino(u,peso);
        u.vecinos.agrega(B);
        v.vecinos.agrega(A);
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
        Vertice u = busca(a);
        Vertice v = busca(b);
        if (!sonVecinos(a,b) || a.equals(b))
             throw new IllegalArgumentException("Los elementos nos son vecinos");
            Vecino vu = null;
            Vecino vv = null;
        /* Buscamos a v en los vecinos de u */
        for (Vecino w : u.vecinos)
            if (w.vecino.equals(v))
                     vu=w;
/* Buscamos a u en los vecinos de v*/
        for (Vecino w : v.vecinos)
            if (w.vecino.equals(u))
                vv = w;
        u.vecinos.elimina(vu);
        v.vecinos.elimina(vv);
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
      if (!contiene(elemento))
            throw new NoSuchElementException("El elemento no está contenido en la gráfica.");
        Vertice v = busca(elemento);
        for (Vertice ver : vertices)
            for (Vecino vec : ver.vecinos)
                if (vec.vecino.equals(v)) {
                    ver.vecinos.elimina(vec);
                    aristas--;
                }
        vertices.elimina(v);

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
      if (!this.contiene(a) || !this.contiene(b))
            throw new NoSuchElementException("Algún elemento no está en la gráfica.");
        Vertice u=busca(a);
        Vertice v=busca(b);
        for (Vecino w : v.vecinos)
            if (w.vecino.equals(u))
                return true;
        return false;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
      if(!contiene(a) || !contiene(b))
        throw new NoSuchElementException();
      if(!sonVecinos(a,b))
      throw new IllegalArgumentException();
        Vertice v =busca(a);
        Vertice u = busca(b);
        for (Vecino ve : v.vecinos)
          if (ve.vecino.equals(u))
              return ve.peso;
              return -1;
    }



    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
      if(!contiene(a) || !contiene(b))
          throw new NoSuchElementException("Algun elemento no pertenece ala grafica");
      if(!sonVecinos(a,b) ||  peso <= 0)
          throw new NoSuchElementException("no se permiten pesos negativos");
      Vertice v = busca(a);
      Vertice u = busca(b);
      for (Vecino w : v.vecinos) {
          Vertice aux = w.vecino;
          if (aux.elemento.equals(u.elemento)) {
              w.peso = peso;
          }
      }
      for (Vecino w: u.vecinos ) {
          Vertice aux = w.vecino;
          if (aux.elemento.equals(v.elemento)) {
              w.peso = peso;
          }
      }
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        if (!this.contiene(elemento))
            throw new NoSuchElementException("El elemento no está en la gráfica.");
        for (Vertice v : vertices){
           if (v.elemento.equals(elemento))
                return v;
            }
        return null;

    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
          if (vertice == null ||
           (vertice.getClass() != Vertice.class &&
           vertice.getClass() != Vecino.class)) {
              throw new IllegalArgumentException("Vértice inválido");
            }
            if (vertice.getClass() == Vertice.class) {
              Vertice v = (Vertice)vertice;
              v.color = color;
            }
            if (vertice.getClass() == Vecino.class) {
                Vecino v = (Vecino)vertice;
                v.vecino.color = color;
              }
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
            for(Vecino u : aux.vecinos){
                if(u.vecino.color == Color.NEGRO){
                u.vecino.color = Color.ROJO;
                cola.mete(u.vecino);
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
      Vertice v = busca(elemento);
   if(v == null){
       throw new NoSuchElementException("El elemento no está en la gráfica");
   }else{
       Cola<Vertice> pila = new Cola<Vertice>();
       for(Vertice a : vertices){
       a.color = Color.ROJO;
       }
       v.color = Color.NEGRO;
       pila.mete(v);
       while(!pila.esVacia()){
       Vertice aux = pila.saca();
       accion.actua(aux);
       for(Vecino u : aux.vecinos){
           if(u.vecino.color == Color.ROJO){
           u.vecino.color = Color.NEGRO;
           pila.mete(u.vecino);
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
      Vertice v = busca(elemento);
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
           for(Vecino u : aux.vecinos){
               if(u.vecino.color == Color.ROJO){
                    u.vecino.color = Color.NEGRO;
                    pila.mete(u.vecino);
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
            return this.vertices.getElementos() == 0;
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
      this.vertices.limpia();
      this.aristas = 0;
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
           for(Vecino  v1 : v.vecinos){
           if( v1.vecino.color != Color.ROJO){
               s += "(" + v.elemento.toString() + ", " + v1.vecino.elemento.toString() + "), ";
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
        if(getElementos() == grafica.getElementos()) {
           if(getAristas() == grafica.getAristas()) {
                for(Vertice v : vertices) {
                     if(!grafica.contiene(v.elemento))
                         return false;
                     for(Vecino vecino : v.vecinos) {
                         if(!grafica.sonVecinos(v.elemento, vecino.vecino.elemento))
                             return false;
                      }
                }
                     return true;
          }
      }
        return false;
}



    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <tt>a</tt> y
     *         <tt>b</tt>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
       Lista<VerticeGrafica<T>> l=new Lista<VerticeGrafica<T>>();
       Vertice s = busca(origen);
       Vertice t = busca(destino);
       if(s==null || t==null){
          throw new NoSuchElementException();
       }
       if(s==t){
          l.agrega(s);
          return l;
       }
      for(Vertice v:vertices){
          v.distancia=Double.MAX_VALUE;
      }
     s.distancia=0;
     Cola<Vertice> cola = new Cola<Vertice>();
     cola.mete(s);
    while(!cola.esVacia()){
        Vertice aux = cola.saca();
        for(Vecino u : aux.vecinos){
            if(u.vecino.distancia==Double.MAX_VALUE){
                u.vecino.distancia = aux.distancia+1;
                cola.mete(u.vecino);
             }
        }
   }
  if(t.distancia == Double.MAX_VALUE){
      return l;
   }
  l.agrega(t);
  Vertice v = t;
  while(v!=s){
      for(Vecino u:v.vecinos){
          if(u.vecino.distancia==v.distancia-1){
                l.agregaInicio(u.vecino);
                v=u.vecino;
                break;
          }
      }
 }
  return l;
    }


    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <tt>origen</tt> y
     *         el vértice <tt>destino</tt>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
      Lista<VerticeGrafica<T>> l=new Lista<VerticeGrafica<T>>();
      Vertice s = busca(origen);
      Vertice t = busca(destino);
      if(s==null || t==null){
         throw new NoSuchElementException();
      }
      if(s==t){
         l.agrega(s);
         return l;
      }
     for(Vertice v:vertices){
         v.distancia=Double.MAX_VALUE;
     }
     s.distancia=0;
     MonticuloMinimo<Vertice> monticulo = new MonticuloMinimo<Vertice>(vertices);
     while(!monticulo.esVacia()){
           Vertice u = monticulo.elimina();
           for(Vecino v : u.vecinos){
               if(v.vecino.distancia >u.distancia + v.peso){
                   v.vecino.distancia=u.distancia + v.peso;
                   monticulo.reordena(v.vecino);
                 }
           }
     }
     if(t.distancia == Double.MAX_VALUE){
         return l;
      }
      l.agrega(t);
      Vertice v = t;
      while(v!=s){
          for(Vecino u:v.vecinos){
              if(u.vecino.distancia==v.distancia-u.peso){
                    l.agrega(u.vecino);
                    v=u.vecino;
                    break;
              }
          }
     }
       l=l.reversa();
      return l;
    }
}
