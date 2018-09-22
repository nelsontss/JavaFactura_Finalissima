import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Comparator;
/**
 * Comparador de faturas de empresas.
 * @version 27/05
 */
public class ComparadorFaturasEmpresa implements Comparator<Entidade>
{
    public int compare(Entidade e1, Entidade e2){ //decrescente
        int valor1=e1.getFaturas().values().size(), valor2=e2.getFaturas().values().size();
        if (valor1>valor2)
            return -1;
        if (valor1<valor2)
            return 1;
        return e1.getNome().compareTo(e2.getNome());
    }
}
