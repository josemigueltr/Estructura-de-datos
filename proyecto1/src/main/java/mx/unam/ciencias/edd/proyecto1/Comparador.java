package mx.unam.ciencias.edd.proyecto1;
import java.text.Collator;
import java.util.Comparator;

/**
 * <p>Clase para convertir un objeto de tipo Colllator en un objeto de tipo Comparator.</p>
 *
 * <p>Nos permite utilizar Collator como comparador de Strings.</p>
 *
 * <p>La clase ComparaStrings implementan la interfaz { @link IComparatos<String>}</p>
 *
 */
public class Comparador implements Comparator<String>{
    public int compare(String a, String b){
        Collator comp = Collator.getInstance();
        comp.setStrength(Collator.PRIMARY);
        return comp.compare(a.replaceAll("\\P{Graph}", ""),b.replaceAll("\\P{Graph}", ""));
    }
}
