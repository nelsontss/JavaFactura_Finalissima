import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Comparator;
/**
 * Comparador de valor de despesas de faturas.
 * @version 27/05 
   */
public class ComparadorValorDespesa implements Comparator<Fatura>
    {
 /**
 * Metodo que compara o valor de despesas de duas faturas
 * @param f1 Fatura 1
 * @param f2 Fatura 2
 * @return resultado da comparação
 */
        public int compare(Fatura f1, Fatura f2){
            if (f1.getValor()>f2.getValor())
                return -1;
            if (f1.getValor()<f2.getValor())
                return 1;
            return f1.getDespesa().compareTo(f2.getDespesa());
        }
    }
