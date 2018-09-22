import java.io.Serializable;
/**
 * Exceção para a já existência do contribuinte a inserir.
 * @version 27/05
 */
public class ContribuinteJaExisteException extends Exception implements Serializable
{
       /**
     * Constructor Standart para a exceção
     */
    public ContribuinteJaExisteException(String msg){
        super(msg);
    }
}
