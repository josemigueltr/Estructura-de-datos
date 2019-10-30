package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T> >
        extends ArbolBinarioOrdenado<T> {

/**
 * Clase interna protegida para vértices.
 */
protected class VerticeRojinegro extends Vertice {

/** El color del vértice. */
public Color color;

/**
 * Constructor único que recibe un elemento.
 * @param elemento el elemento del vértice.
 */
public VerticeRojinegro(T elemento) {
        super(elemento);
        color= Color.NINGUNO;

}

/**
 * Regresa una representación en cadena del vértice rojinegro.
 * @return una representación en cadena del vértice rojinegro.
 */
public String toString() {
        if(this==null)
                return "";
        return (this.color== Color.ROJO) ? "R{"+this.elemento.toString()+"}" : "N{"+this.elemento.toString()+"}";
}

/**
 * Compara el vértice con otro objeto. La comparación es
 * <em>recursiva</em>.
 * @param objeto el objeto con el cual se comparará el vértice.
 * @return <code>true</code> si el objeto es instancia de la clase
 *         {@link VerticeRojinegro}, su elemento es igual al elemento de
 *         éste vértice, los descendientes de ambos son recursivamente
 *         iguales, y los colores son iguales; <code>false</code> en
 *         otro caso.
 */
@Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
                return false;
        @SuppressWarnings("unchecked")
        VerticeRojinegro vertice = (VerticeRojinegro)objeto;
        return (color==vertice.color) && super.equals(vertice);
}
}

/**
 * Constructor sin parámetros. Para no perder el constructor sin parámetros
 * de {@link ArbolBinarioOrdenado}.
 */
public ArbolRojinegro() {
        super();
}

/**
 * Construye un árbol rojinegro a partir de una colección. El árbol
 * rojinegro tiene los mismos elementos que la colección recibida.
 * @param coleccion la colección a partir de la cual creamos el árbol
 *        rojinegro.
 */
public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
}



private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro)vertice;
        return v;
}


/**
 * Construye un nuevo vértice, usando una instancia de {@link
 * VerticeRojinegro}.
 * @param elemento el elemento dentro del vértice.
 * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
 */
@Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
}

/**
 * Regresa el color del vértice rojinegro.
 * @param vertice el vértice del que queremos el color.
 * @return el color del vértice rojinegro.
 * @throws ClassCastException si el vértice no es instancia de {@link
 *         VerticeRojinegro}.
 */
public Color getColor(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = verticeRojinegro(vertice);
        return v.color;
}

/**
 * Agrega un nuevo elemento al árbol. El método invoca al método {@link
 * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
 * vértices y girando el árbol como sea necesario.
 * @param elemento el elemento a agregar.
 */
@Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro v = verticeRojinegro(ultimoAgregado);
        v.color=Color.ROJO;
        balancea(v);
}
/*


 */

/*Metodo auxiliar de rebalanceo de agrega
   consta de 5 casos los cuales son:
   1-Caso 1. El vértice v tiene padre ⌀; terminamos.
   2-Caso 2. El vértice p es negro: como el vértice v es rojo, el árbol no se ha desbalanceado y terminamos.
   3-Caso 3. El vértice t es rojo: como p también es rojo, coloreamos t y p de
    negro, coloreamos a de rojo y hacemos recursión sobre a
   4-Caso 4.Los vértices v y p están cruzados
   5-Caso 5.Los vértices v y p no están cruzados
 **********Simbologia*****

   v=vertice agregado
   P=padre del vertices--->en codigo sera padre
   t=Hermano del vertice-->en codigo sera tio
   a=abuelo del vertice -->en codigo sera el abuelo del vertice, es decir el padre del pade del vertice

 ***********************Metodos auxiliares****************************************************
   1-Padre(vertice)--->Me regresa el padre del vertice
   2-vRojo(vertice)---->Regresa true si el verice es de color rojo, false en otro caso
   3-vNegro(vertice)--->Regresa true si el vertice es null o es de color ROJO, false en otro caso
   4-Tio(vertice)----->Regresa el tio del vertice
   5-Abuelo(verice)------>Regresa el abuelo del vertice
   6-Soncruzados(vertice)------>Regresa true si el vertice y su padre entan es distintas direcciones
 */

private void balancea(VerticeRojinegro vertice){
        VerticeRojinegro papa= Padre(vertice);

//---------------------------1-------------------------------------------------
        if(papa==null) {
                vertice.color=Color.NEGRO;
                return;
        }
//---------------------------------2-------------------------------------------
        if(vNegro(papa)) {
                return;
        }
//-------------------------------------3---------------------------------------
        VerticeRojinegro tio= Tio(vertice);
        if(tio!=null) {
                VerticeRojinegro abuelo=Abuelo(vertice);
                if(vRojo(tio)) {
                        tio.color=Color.NEGRO;
                        papa.color=Color.NEGRO;
                        abuelo.color=Color.ROJO;
                        balancea(abuelo);
                        return;
                }
        }
//-------------------------------------------4---------------------------------
        if(Soncruzados(vertice)) {
                if(vertice.padre.padre.derecho==papa) {
                        super.giraDerecha(papa);
                }
                else{
                        super.giraIzquierda(papa);
                }
                caso5(papa);
                return;
        }
//-------------------------------------5------------------------
        caso5(vertice);
}


//caso 5 de agrega
private void caso5(VerticeRojinegro vertice){
        VerticeRojinegro abuelo = Abuelo(vertice);
        VerticeRojinegro papa = Padre(vertice);
        papa.color=Color.NEGRO;
        abuelo.color=Color.ROJO;
        if(papa.derecho==vertice)
                super.giraIzquierda(abuelo);
        else
                super.giraDerecha(abuelo);
}

/**
 * Elimina un elemento del árbol. El método elimina el vértice que contiene
 * el elemento, y recolorea y gira el árbol como sea necesario para
 * rebalancearlo.
 * @param elemento el elemento a eliminar del árbol.
 */

@Override public void elimina(T elemento) {
        VerticeRojinegro v = verticeRojinegro(super.busca(elemento));
        if(v==null) {
                return;
        }

        if(v.izquierdo!=null && v.derecho!=null) {
                v=verticeRojinegro(super.intercambiaEliminable(v));
        }

        //si el vertice es un ahoja le cramos un hijo fantasma
        if(v.izquierdo==null && v.derecho==null) {
                VerticeRojinegro fantasma =verticeRojinegro(nuevoVertice(null));
                fantasma.color = Color.NEGRO;
                v.izquierdo = fantasma;
                fantasma.padre=v;

        }
        //guardo los valores de vertice a eliminar antes de eliminarlo
        VerticeRojinegro hijo = Obtenhijo(v);
        Color padrecolor = v.color;
        super.eliminaVertice(v);
        elementos--;

//caso 1
//hijo es rojo y por lo tanto vertice a eliminar es negro. Sencillamente coloreamos h de negro
//y terminamos.
        if(vRojo(hijo)) {
                hijo.color=Color.NEGRO;
                return;
        }
//vertice a eliminar es rojo y por lo tanto h es negro. No tenemos que hacer nada.
        if(padrecolor==Color.ROJO ) {
                mata(hijo);
        }
/*vertice a eliminar y su hijo son los dos negros Rebalanceamos sobre h.
   eliminamos al fantasma al regresar de la recursion.
 */
        if(padrecolor==Color.NEGRO&&vNegro(hijo)) {
                rebalancea2(hijo);
         mata(hijo);

        }

}


/*Metodo auxiliar de rebalanceo de eliminar
   consta de 6 casos los cuales son:
   1-Caso 1. El vértice v tiene padre ⌀; terminamos.
   2-Caso 2. El vértice h es rojo y por lo tanto p es negro
   3-Caso 3. Los vértices p, h, h i y h d son negros
   4-Caso 4. Los vértices h, h i y h d son negros y p es rojo
   5-Caso 5. El vértice v es izquierdo, h i es rojo y h d es negro
   6-aso 6. El vértice v es izquierdo y h d es rojo o el vértice v es derecho y
   h i es rojo
 **********Simbologia*****

   v=hijo del vertice a eliminar
   P=padre del vertices--->en codigo sera padre
   h=Hermano del vertice-->en codigo sera hermano
   hi=Hijo izquierdo del hermano de vertice,es decir el sobrinoIzq-->en codigo sera sobrinoIzq
   hd=Hijo izquierdo del hermano de vertice,es decir el sobrinoDer-->en codigo sera sobrinoDer

 ***********************Metodos auxiliares****************************************************
   1-Hermano(vertice)--->Me regresa el vertice hermano del vertice,sie es diferente de null
   2-vRojo(vertice)---->Regresa true si el verice es de color rojo, false en otro caso
   3-vNegro(vertice)--->Regresa true si el vertice es null o es de color ROJO, false en otro caso
   4-esIzquierdo(vertice)----->Regresa TRue si el vertice es hijo izquierdo,false en otro caso
   5-esDerecho(verice)------>Regresa TRue si el vertice es hijo derecho,false en otro caso
   6-Obtenhijo(vertice)------>Regresa el vertice hijo sin importar si es izquierdo o derecho
 */
private void rebalancea2(VerticeRojinegro vertice){

        //------------------------1-------------------------------------------------------------------------------
        if(vertice.padre==null)
                return;

        //------------------------ 2-------------------------------------------------------------------------------

        VerticeRojinegro padre = verticeRojinegro(vertice.padre);
        VerticeRojinegro hermano =Hermano(vertice);
        if(vRojo(hermano)) {
                padre.color=Color.ROJO;
                hermano.color=Color.NEGRO;
                if (esIzquierdo(vertice))
                        super.giraIzquierda(padre);
                else
                        super.giraDerecha(padre);
                padre = verticeRojinegro(vertice.padre);    //Como se hizo un giro se deben de actualizar las referencias.
                hermano = Hermano(vertice);
        }

        //---------------------------------3-------------------------------------------------------------------------

        VerticeRojinegro sobrinoIzq = verticeRojinegro(hermano.izquierdo);
        VerticeRojinegro sobrinoDer = verticeRojinegro(hermano.derecho);
        //true si todos los vertices son negros,false en otro caso
        boolean negros=(vNegro(hermano) && vNegro(padre)&& vNegro(vertice)&&vNegro(sobrinoIzq)&& vNegro(sobrinoDer)) ? true : false;
        if(negros) {
                hermano.color=Color.ROJO;
                rebalancea2(padre);
                return;
        }

        //_-----------------------------------------4-----------------------------------------------------------------

        //true si todos los vertices son negros a  exepcion del padre,false en otro caso
        boolean nonegros=(vRojo(padre)&&vNegro(hermano)&& vNegro(vertice)&&vNegro(sobrinoIzq)&& vNegro(sobrinoDer)) ? true : false;
        if(nonegros) {
                padre.color = Color.NEGRO;
                hermano.color = Color.ROJO;
                return;
        }

        //-----------------------------------------5-------------------------------------------------------------------

        if((esIzquierdo(vertice) && vRojo(sobrinoIzq) && vNegro(sobrinoDer)) || (esDerecho(vertice) && vNegro(sobrinoIzq) && vRojo(sobrinoDer))) {
                if(!vNegro(sobrinoIzq)) {
                        sobrinoIzq.color=Color.NEGRO;
                }else{
                        sobrinoDer.color=Color.NEGRO;
                }
                hermano.color=Color.ROJO;
                if(esIzquierdo(vertice)) {
                        super.giraDerecha(hermano);
                }else{
                        super.giraIzquierda(hermano);
                }
                hermano=Hermano(vertice);
                sobrinoIzq=verticeRojinegro(hermano.izquierdo);
                sobrinoDer=verticeRojinegro(hermano.derecho);
        }
        //--------------------------------6---------------------------------------------------------------------------------------
        hermano.color = padre.color;
        padre.color = Color.NEGRO;
        if(vertice.padre.izquierdo==vertice)
                sobrinoDer.color = Color.NEGRO;
        else
                sobrinoIzq.color = Color.NEGRO;

        if(vertice.padre.izquierdo==vertice)
                super.giraIzquierda(padre);
        else
                super.giraDerecha(padre);

}

/**
 * Lanza la excepción {@link UnsupportedOperationException}: los árboles
 * rojinegros no pueden ser girados a la izquierda por los usuarios de la
 * clase, porque se desbalancean.
 * @param vertice el vértice sobre el que se quiere girar.
 * @throws UnsupportedOperationException siempre.
 */
@Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
}

/**
 * Lanza la excepción {@link UnsupportedOperationException}: los árboles
 * rojinegros no pueden ser girados a la derecha por los usuarios de la
 * clase, porque se desbalancean.
 * @param vertice el vértice sobre el que se quiere girar.
 * @throws UnsupportedOperationException siempre.
 */
@Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
}

/* METODOS AUXILIARES UTILIZADOS*/



// regresa al papa de un vertice
private VerticeRojinegro Padre(VerticeRojinegro v) {
        if (v.padre == null)
                return null;
        return verticeRojinegro(v.padre);
}


//2-regresa al tio de un vertice
private VerticeRojinegro Tio(VerticeRojinegro vertice){
        VerticeRojinegro abuelo=Abuelo(vertice);

        if(vertice.padre==null)
                return null;
        if(abuelo==null)
                return null;
        if(abuelo.izquierdo==vertice.padre) {//el padre de v es izquierdo, el tio es derecho
                if(abuelo.derecho==null) //el abuelo de v no tiene hayDerecho no haY tio
                        return null;
                return verticeRojinegro(abuelo.derecho);

        }else //el papa es derecho y el tio es izquierdo
        {
                if(abuelo.izquierdo==null) //No hay Tio
                        return null;
                return verticeRojinegro( abuelo.izquierdo);
        }
}



//regresa el abuelo de un vertice
private VerticeRojinegro Abuelo(VerticeRojinegro vertice) {
        if (vertice.padre == null)
                return null;
        if (vertice.padre.padre == null) //nos referimos al padre del padre de v es decir al abuelo de v
                return null;
        return verticeRojinegro(vertice.padre.padre);
}

//regresa si un vertice es ROJO
private boolean vRojo(VerticeRojinegro vertice){
        if(vertice==null)
                return false;
        else
                return vertice.color==Color.ROJO;
}

//regresa un vertice es negro
private boolean vNegro(VerticeRojinegro vertice){
        return vertice == null || vertice.color == Color.NEGRO;
}

//indica  si el vertice padre esta cruzado con su hijo

private boolean Soncruzados(VerticeRojinegro vertice){

        if(vertice.padre.derecho == vertice && Abuelo(vertice).derecho==vertice.padre)
                return false;
        if(vertice.padre.izquierdo == vertice && Abuelo(vertice).izquierdo==vertice.padre)
                return false;

        return true;
}

//indica si un vertice es hijo izquierdo
private boolean esIzquierdo(VerticeRojinegro v){
        if (v.padre.izquierdo == v)
                return true;
        return false;
}
//indica si un vertice es hijo derecho
private boolean esDerecho(VerticeRojinegro v){
        if (v.padre.derecho == v)
                return true;
        return false;
}

// regresa el hijo del vertice
private VerticeRojinegro Obtenhijo(VerticeRojinegro vertice) {
        if (vertice.hayIzquierdo()) {
                return verticeRojinegro(vertice.izquierdo);
        }
        return verticeRojinegro(vertice.derecho);
}

//regresa el hermano de un vertice
private VerticeRojinegro Hermano(VerticeRojinegro vertice){
        if (vertice.padre==null)
                return null;
        if(Padre(vertice).izquierdo==vertice && Padre(vertice).derecho!=null)
                return verticeRojinegro(Padre(vertice).derecho);
        if(Padre(vertice).derecho==vertice && Padre(vertice).izquierdo!=null)
                return verticeRojinegro(Padre(vertice).izquierdo);
        return null;
}

private void mata(VerticeRojinegro hijo){
  if(hijo.elemento==null) {
          if(Padre(hijo)!=null) {
                  VerticeRojinegro p = Padre(hijo);
                  if(esIzquierdo(hijo)) {
                          p.izquierdo=null;

                  }else{
                          p.derecho=null;
                  }
          }else{
                  raiz=null;
          }


  }

}
}
