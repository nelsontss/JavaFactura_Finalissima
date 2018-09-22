import java.io.Serializable;
/**
 * Exceção para a nao existencia de fatura.
 * @version 27/05
 */
public class FaturaNaoExisteException extends Exception implements Serializable
{
        /**
     * Constructor vazio para a exceção
     */
    public FaturaNaoExisteException(){
        super();
    }
        /**
     * Constructor Standart para a exceção
     */
    public FaturaNaoExisteException(String msg){
        super(msg);
    }
}
