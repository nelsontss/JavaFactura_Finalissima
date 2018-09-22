import java.time.LocalDate;
import java.util.Comparator;
/**
 * Comparador de datas.
  * @version 27/05
   */
public class ComparadorData implements Comparator<Fatura>
{
 /**
 * Metodo que compara a data de duas faturas
 * @param f1 Fatura 1
 * @param f2 Fatura 2
 * @return resultado da comparação
 */
    public int compare(Fatura f1, Fatura f2)
    {
        if (f1.getData().isAfter(f2.getData()))
            return 1;
        if (f1.getData().isBefore(f2.getData()))
            return -1;
        return f1.getDespesa().compareTo(f2.getDespesa());
    }
}
