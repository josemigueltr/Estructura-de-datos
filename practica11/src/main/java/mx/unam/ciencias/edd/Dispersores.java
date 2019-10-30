package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
//longitud del arreglo
      int l = llave.length;
      int i = 0;
      int r = 0;
      while(l>= 4){
         r^=combina(llave[i],llave[i+1],llave[i+2],llave[i+3]);
         i += 4;
         l -= 4;
       }

    //se entrara a este caso cuando el numero de bytes de un arreglo no sea multiplo de 4
      byte s=0b00000000;
      switch (l){
      case 1: r^=combina(llave[i],s,s,s);
        break;
      case 2: r^=combina(llave[i],llave[i+1],s,s);
        break;
      case 3: r^=combina(llave[i],llave[i+1],llave[i+2],s);
        break;
      }
      return r;
    }

   //Metodo auxiliar conbina enteros en biggendian
     private static int combina(byte a, byte b , byte c , byte d){
       int n= (((a & 0xFF)<<24) | ((b & 0xFF)<<16) | ((c & 0xFF)<<8) | (d & 0xFF));
       return n;
     }



    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
public static int dispersaBJ(byte[] llave) {
        int n = llave.length;
        int l = llave.length;
        int i = 0;
        int a = 0x9e3779b9;
        int b = 0x9e3779b9;
        int c = 0xFFFFFFFF;
        int [] r=null;
        while(l >= 12){
          a+= littlecomb(llave,i);
          b+=littlecomb(llave,i+4);
          c+= littlecomb(llave,i+8);
        r=mezcla(a,b,c);
        a=r[0];
        b=r[1];
        c=r[2];
        i+=12;
        l-=12;
          }
      c+= n;
      switch (l){
      case 11: c+=((llave[i+10] & 0xFF)<<24);
      case 10: c+=((llave[i+9] & 0xFF)<<16);
      case 9:  c+=((llave[i+8] & 0xFF)<<8);
      case 8:  b+=((llave[i+7] & 0xFF)<<24);
      case 7:  b+=((llave[i+6] & 0xFF)<<16);
      case 6:  b+=((llave[i+5] & 0xFF)<<8);
      case 5:  b+=(llave[i+4] & 0xFF);
      case 4:  a+=((llave[i+3] & 0xFF)<<24);
      case 3:  a+=((llave[i+2] & 0xFF)<<16);
      case 2:  a+=((llave[i+1] & 0xFF)<<8);
      case 1:  a+=(llave[i] & 0xFF);
      }
      r=mezcla(a,b,c);
      return  r[2];
}

       //Metodo auxiliar mezcla los 3 enteros y los regresa en un arreglo
         private static int [] mezcla(int a , int b ,int c){
           int [] r=new int [3];
           a -=b;
           a -=c;
           a ^= (c>>>13);
           b -= c;
           b -= a;
           b ^= (a<<8);
           c -= a;
           c -= b;
           c ^= (b>>>13);
           a -=b;
           a -=c;
           a ^= (c>>>12);
           b -= c;
           b -= a;
           b ^= (a<<16);
           c -= a;
           c -= b;
           c ^= (b>>>5);
           a -=b;
           a -=c;
           a ^= (c>>>3);
           b -= c;
           b -= a;
           b ^= (a<<10);
           c -= a;
           c -= b;
           c ^= (b>>>15);
           r[0]=a;
           r[1]=b;
           r[2]=c;
          return r;
         }


 //Metodo auxiliar que combina enteros en littleendian
      private static int littlecomb(byte[] llave,int i){
          int n= (llave[i] & 0xFF) | ((llave[i+1] & 0xFF)<<8) | ((llave[i+2] & 0xFF)<<16) | ((llave[i+3] & 0xFF)<<24);
          return n;
      }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
       int h = 5381 ;
       int n = llave.length;
       for (int i =0; i<n; i++){
           h+= (h<<5) + (llave[i] & 0xFF);
       }
      return h;


}
}
