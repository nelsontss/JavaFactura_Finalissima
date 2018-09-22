import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Comparator;
/**
 * Comparador de valor de despesas de entidades.
  * @version 27/05
   */
public class ComparadorValorDespesaEntidade implements Comparator<Entidade>
{
     /**
 * Metodo que compara o valor de despesa de duas entidades
 * @param e1 Entidade 1
 * @param e2 Entidade 2
 * @return resultado da comparação
 */
    public int compare(Entidade e1, Entidade e2){
        double valor1=0, valor2=0;
        for(Fatura f: e1.getFaturas().values())
            if(e1.getNif() ==  f.getNif_cliente())
            valor1+=f.getValor();
        for(Fatura f: e2.getFaturas().values())
            if(e2.getNif() ==  f.getNif_cliente())    
            valor2+=f.getValor();
        if (valor1>valor2)
            return -1;
        if (valor1<valor2)
            return 1;
        return e1.getNome().compareTo(e2.getNome());
    }
}
