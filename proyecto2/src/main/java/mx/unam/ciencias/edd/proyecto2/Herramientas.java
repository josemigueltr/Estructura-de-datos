package mx.unam.ciencias.edd.proyecto2;
/**
 * Clase para crear elementos en SVG.
 */

 public class Herramientas {


 /*Metodo auxiliar para dibujar numeros*/
	public String numero (int n, int x, int y,String letracolor) {
		  return String.format("\n <text x='%d' y='%d' font-size='15'  text-anchor='middle' fill='%s' > %d </text> \n",x,y,letracolor,n);
	}

	/*Metodo auxiliar, dibuja un rectangulo*/
	public String rectangulo (int width, int altura, int x, int y) {
		  return String.format("\n <rect x='%d' y='%d'  width='%d' height='%d' stroke='blue' stroke-width='1' fill='white' />\n",x,y,width,altura);
	}

  /*metodo auxiliar,dibuja un circulo*/
	public String circulo (int x, int y,String color) {
	    return  String.format("\n \t<circle cx='%d' cy='%d' r='15' stroke='#00cc00' stroke-width='3' fill='%s' />\n",x,y,color);
	}

/* Figuras con numeros */

  /*Dibuja un  rectangulo con un numero*/
	public String rectanguloConNumero(int elemento, int x, int y, int width, int altura,String letracolor) {
		  return this.rectangulo(width,altura,x,y) + this.numero(elemento,x+20,60,letracolor);
	}

	/* Guarda un circulo  con numero */
	public String circuloConNumero (int n, int x, int y,String color,String letracolor) {
			return this.circulo( x, y, color) + this.numero(n, x, y+5,letracolor);
		}

	/* Flechas para listas o pilas y colas */
	public String dobleFlecha (int x, int y) {
		  return String.format("<text x='%d' y='%d' font-weight='bold' font-size='20'> %s </text>",x,y,"↔");
	}

 /* Dibuja una flechaDerecha*/
	public String flechaDerecha (int x, int y) {
		  return String.format("<text x='%d' y='%d' font-weight='bold' font-size='20'> %s </text>",x,y,"→");
	 }

  /* Dibuja una arista*/
	 public String linea (int x1, int y1, int x2, int y2) {
      return String.format("\n <line x1='%d' y1='%d' x2='%d' y2='%d'  style='stroke:#785000;stroke-width:6'/>\n",x1,y1,x2,y2);

	  }

}
