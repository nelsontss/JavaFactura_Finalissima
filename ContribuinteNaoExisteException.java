
/**
 * Exceção para a não existência do contribuinte.
 * @version 27/05
 */
public class ContribuinteNaoExisteException extends Exception
{
    // instance variables - replace the example below with your own
    
    /**
     * Constructor vazio para a exceção
     */
    public ContribuinteNaoExisteException()
    {
       super();
    }
    /**
     * Constructor standart para a exceção
     */
    public ContribuinteNaoExisteException(String msg)
    {
       super(msg);
    }    
}
