package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
      if(cabeza==null){
        return "";
      }
      String r = cabeza.elemento.toString()+ ",";
  Nodo n=cabeza.siguiente;
      while(n != null) {
              r += n.elemento.toString() +",";
              n = n.siguiente;
      }
      return r;

    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
     if(elemento==null)
    throw new IllegalArgumentException();

      Nodo nodo = new Nodo(elemento);
    if(rabo == null) {
       rabo = cabeza = nodo;       /*si la cola es vacia , el elemento sera igual al rabo,cabeza pues solo habra un elemento*/
    } else{
          rabo.siguiente = nodo;
          rabo = rabo.siguiente;   /*se agrega el elemento al final de la cola*/
  }
  }

}
