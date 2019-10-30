package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
     public static int dispersaXOR(byte[] llave) {
         int dispersion=0;

         int l = llave.length;
         int i = 0;

         while( l >= 4 ){
             dispersion^=combina(llave[i+0],llave[i+1],llave[i+2],llave[i+3]);
             l-=4;
             i+=4;
         }

         byte byte0 = 0b00000000;
         switch( l ){
             case 1: dispersion^=combina(llave[i+0],byte0     ,byte0     ,byte0); break;
             case 2: dispersion^=combina(llave[i+0],llave[i+1],byte0     ,byte0); break;
             case 3: dispersion^=combina(llave[i+0],llave[i+1],llave[i+2],byte0); break;
         }
         return dispersion;
     }

     /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
      int a = 0x9E3779B9;
      int b = 0x9E3779B9;
      int c = 0xFFFFFFFF;

      int l = llave.length;
      int i = 0;


      while( l >= 12 ){
          a+=combina(llave[i+3] ,llave[i+2] ,llave[i+1], llave[i+0]);
          b+=combina(llave[i+7] ,llave[i+6] ,llave[i+5], llave[i+4]);
          c+=combina(llave[i+11],llave[i+10],llave[i+9], llave[i+8]);
          int[]trio = mezcla(a,b,c);
          a = trio[0];
          b = trio[1];
          c = trio[2];
          l-=12; i+=12;
      }

      c+= llave.length;

      switch (l){
        case 11: c+=((llave[i+10] & 0xFF)<<24);
        case 10: c+=((llave[i+9]  & 0xFF)<<16);
        case 9:  c+=((llave[i+8]  & 0xFF)<<8 );
        case 8:  b+=((llave[i+7]  & 0xFF)<<24);
        case 7:  b+=((llave[i+6]  & 0xFF)<<16);
        case 6:  b+=((llave[i+5]  & 0xFF)<<8 );
        case 5:  b+=((llave[i+4]  & 0xFF)<<0 );
        case 4:  a+=((llave[i+3]  & 0xFF)<<24);
        case 3:  a+=((llave[i+2]  & 0xFF)<<16);
        case 2:  a+=((llave[i+1]  & 0xFF)<<8 );
        case 1:  a+=((llave[i+0]  & 0xFF)<<0 );
      }

      int[]trio =mezcla(a,b,c);
      c = trio[2];
      return c;

    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        int h=5381;
        for(int i=0; i<llave.length; i++){
            h += (h << 5) + (llave[i]& 0xFF);
        }
        return h;
    }

    private static int combina(byte a, byte b , byte c , byte d){
      return ((a & 0xFF)<<24) | ((b & 0xFF)<<16) |
             ((c & 0xFF)<< 8) | ((d & 0xFF)<< 0);
    }

    private static int[] mezcla(int a, int b, int c){
        int [] trio=new int [3];

        a -= b; a -= c; a ^= (c >>> 13);
        b -= c; b -= a; b ^= (a <<   8);
        c -= a; c -= b; c ^= (b >>> 13);

        a -= b; a -= c; a ^= (c >>> 12);
        b -= c; b -= a; b ^= (a <<  16);
        c -= a; c -= b; c ^= (b >>>  5);

        a -= b; a -= c; a ^= (c >>>  3);
        b -= c; b -= a; b ^= (a <<  10);
        c -= a; c -= b; c ^= (b >>> 15);

        trio[0]=a;
        trio[1]=b;
        trio[2]=c;

        return trio;
    }
}
