package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
             this.llave=llave;
             this.valor=valor;

        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
         public Iterador() {
             indice   = 0;
             iterador = null;
             while( indice < entradas.length ){
               if( entradas[indice]!=null && !entradas[indice].esVacia() ){
                 iterador = entradas[indice].iterator();
                 break;
               }
               indice+=1;
             }
         }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            return iterador!=null;
        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
            if( !iterador.hasNext() )
                throw new NoSuchElementException();

            Entrada entrada = iterador.next();
            if(!iterador.hasNext() ){
                indice+=1;
                while( indice < entradas.length ){
                    if( entradas[indice]!=null && !entradas[indice].esVacia() ){
                        iterador= entradas[indice].iterator();
                        return entrada;
                    }
                    indice+=1;
                }
                iterador=null;
            }
        return entrada;
        }
    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override public K next() {
              return siguiente().llave;
        }
    }

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador
        implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override public V next() {
         return siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
     public Diccionario(int capacidad, Dispersor<K> dispersor) {
       this.dispersor = dispersor;
       capacidad = ( capacidad < MINIMA_CAPACIDAD ) ?
                   nuevaCapacidad(MINIMA_CAPACIDAD) : nuevaCapacidad( capacidad );
       this.entradas  = nuevoArreglo(capacidad);

     }



    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
     public void agrega(K llave, V valor) {
         if( llave==null || valor==null )
             throw new IllegalArgumentException();

         Entrada nuevaEntrada = new Entrada( llave, valor );
         int direccion = dispersaLlave( llave );

         //Todavia no existe una lista en esa posición
         if( this.entradas[direccion] == null )
             this.entradas[direccion] = new Lista<Entrada>();

         //Busca si hay una entrada en la lista con la misma llave y la cambia
         for( Entrada entrada : this.entradas[direccion] ){
             if( entrada.llave.equals(llave) ){
                 entrada.valor = valor;
                 return;
             }
         }
         //Si no ocurrió ninguna de las anteriores solo se agrega la entrada
         this.entradas[direccion].agrega( nuevaEntrada );
         this.elementos +=1;

         if(this.carga()>MAXIMA_CARGA)
             this.aumentaCapacidad();
     }


    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
        if(llave==null)
           throw new IllegalArgumentException("");
        int i=dispersaLlave(llave);
        if(entradas[i]==null){
          throw new NoSuchElementException();
        }
        for(Entrada e : entradas[i]){
           if(e.llave.equals(llave))
           return e.valor;
        }
      throw new NoSuchElementException();
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <tt>true</tt> si la llave está en el diccionario,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(K llave) {
         if(llave==null)
         return false;
         int i=dispersaLlave(llave);
         if(entradas[i]==null){
          return false;
         }
         for(Entrada e : entradas[i]){
            if(e.llave.equals(llave))
            return true;
         }
       return false;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
      if(llave==null)
         throw new IllegalArgumentException("");
      int i=dispersaLlave(llave);
      if(entradas[i]==null){
        throw new NoSuchElementException();
      }
      for(Entrada e : entradas[i]){
         if(e.llave.equals(llave)){
            entradas[i].elimina(e);
            elementos--;
            return;
          }
      }
    throw new NoSuchElementException();

    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
          int longitud=0;
          for(Lista<Entrada> e : entradas){
            if(e!=null)
            longitud+=e.getLongitud()-1;
          }
          return longitud;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
      int Maxlongitud=0;
      for(Lista<Entrada> e : entradas){
        if(e!=null){
          if( Maxlongitud < e.getLongitud()-1)
                Maxlongitud= e.getLongitud()-1;
        }
            }
      return Maxlongitud;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
      double carga = (elementos + 0.0) / entradas.length;
      return carga;
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        return elementos==0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        int n=entradas.length;
        entradas=nuevoArreglo(n);
        elementos=0;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
      String s = "";
      if( this.esVacia()){
          return s+= "{}";
      }
      s += "{ ";
      for ( Lista<Entrada> l: entradas){
          if( l!= null){
        for( Entrada e: l){
            s+= "'"+e.llave+"': '"+e.valor+"', ";
        }
          }
      }
      s+= "}";
      return s;
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Diccionario<K, V> d =
            (Diccionario<K, V>)o;
            if(d.elementos != elementos)
          	    return false;
          	for (int i=0; i< entradas.length; i++){
          	    if( entradas[i] != null)
          		for (Entrada e : entradas[i]){
          		    if(!d.contiene(e.llave))
          		    	return false;
          		    if(!e.valor.equals(d.get(e.llave)))
          		       return false;
          		}
          	}
          	return true ;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }

    private int nuevaCapacidad( int capacidad ){
        int nuevaCapacidad=0;
        int i=1;
        while( nuevaCapacidad <= capacidad*2 ){
            nuevaCapacidad = (int)Math.pow(2,i);
            i+=1;
        }
        return nuevaCapacidad;
    }

    private int dispersaLlave( K llave ){
        return ( dispersor.dispersa( llave ) & (entradas.length-1) );
    }

    private void aumentaCapacidad(){
        int capacidad = this.entradas.length*2;

        Lista<Entrada>[] copiaEntradas = this.entradas;
        this.entradas  = nuevoArreglo( capacidad );
        this.elementos = 0;

        for( int i=0; i< copiaEntradas.length; i++){
            if( copiaEntradas[i]!= null )
                for( Entrada entrada : copiaEntradas[i] )
                    this.agrega( entrada.llave, entrada.valor);
        }
    }

    private Entrada buscaLlaveEnLista( int direccion, K llave ){
        if( this.entradas[direccion]==null || this.entradas[direccion].esVacia() )
            return null;
        for( Entrada entrada : this.entradas[direccion] )
            if( entrada.llave.equals(llave) )
                return entrada;
        return null;
    }

}
