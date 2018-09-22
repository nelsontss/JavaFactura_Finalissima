import java.time.LocalDate;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * Classe das faturas
 * @version 27/5/2018
 */
public class Fatura implements Serializable
{
    //variaveis de instancia
    private int id;
    private int nif_emitente;
    private String emitente;
    private LocalDate data; //dia/mes/ano
    private int nif_cliente;
    private String despesa;
    private ArrayList<AtividadesEconomicas> ati_eco;
    private boolean valida;
    private boolean validada;
    private double valor;


    /**
     * Construtor vazio para a classe fatura
     */
    public Fatura(){
        //inicializar fatura vazia
        this.id=0;
        this.nif_emitente=0;
        this.emitente="";
        this.data=LocalDate.now();
        this.nif_cliente=0;
        this.despesa="";
        this.ati_eco=new ArrayList<AtividadesEconomicas>();
        this.valida = false;
        this.validada = false;
        this.valor=0;
    }
    
    /**
     * Construtor standard para a classe fatura
     * @param id Id da fatura
     * @param nif_emitente Nif da empresa que emite a fatura
     * @param emitente Nome do emitente da fatura
     * @param data Data da fatura
     * @param nif_cliente Nif do contribuinte que detem a fatura
     * @param despesa Nome da despesa
     * @param ati_eco Lista das atividades economicas
     * @param valor Valor da fatura
     */
    public Fatura (int id, int nif_emitente, String emitente, LocalDate data, int nif_cliente, 
                    String despesa, ArrayList<AtividadesEconomicas> ati_eco, double valor){
        //inicializar fatura com os argumentos dados
        this.id = id;
        this.nif_emitente=nif_emitente;
        this.emitente=emitente;
        this.data=data;
        this.nif_cliente=nif_cliente;
        this.despesa=despesa;
        if(ati_eco.size() == 1){
            this.valida = true;
        }else
            this.valida = false;
        
        this.validada = false;
        this.ati_eco= new ArrayList<AtividadesEconomicas>();
        for(AtividadesEconomicas s: ati_eco)
            this.ati_eco.add(s);
        this.valor=valor;
    }    

    /**
     * Construtor copy para a classe fatura
     * @param umaFatura Fatura que se pretende copiar
     */    
    public Fatura (Fatura umaFatura){
        this.id = umaFatura.getId();
        this.nif_emitente=umaFatura.getNif_emitente();
        this.emitente=umaFatura.getEmitente();
        this.data=umaFatura.getData();
        this.nif_cliente=umaFatura.getNif_cliente();
        this.despesa=umaFatura.getDespesa();
        this.ati_eco=umaFatura.getAti_eco();
        this.valor=umaFatura.getValor();
        this.valida = umaFatura.getValida();
        this.validada = umaFatura.getValidada();
    }
    
    //getters
    /**
     * Metodo que devolve o id da fatura
     * @return Id da fatura
     */
    public int getId(){
        return this.id;
    }
    /**
     * Metodo que diz que uma fatura e valida ou nao
     * @return True se e valida, False se nao e valida
     */
    public boolean getValida(){
        return this.valida;
    }
    /**
     * Metodo que diz se a fatura esta validada
     * @return Id da fatura
     */
    public boolean getValidada(){
        return this.validada;
    }
    /**
     * Metodo que devolve o nif do emitente da fatura
     * @return Nif do emitente da fatura
     */
    public int getNif_emitente(){
        return this.nif_emitente;
    }
    /**
     * Metodo que devolve o nome do emitente da fatura
     * @return Nome do emitente
     */
    public String getEmitente(){
        return this.emitente;
    }
    /**
     * Metodo que devolve a data da fatura
     * @return Data da fatura
     */
    public LocalDate getData(){
        return this.data;
    }
    /**
     * Metodo que devolve o nif do detentor da fatura
     * @return Nif do detentor da fatura
     */
    public int getNif_cliente(){
        return this.nif_cliente;
    }
    /**
     * Metodo que devolve o nome da despesa
     * @return Nome da despesa
     */
    public String getDespesa(){
        return this.despesa;
    }
    /**
     * Metodo que devolve a lista das atividades economicas
     * @return Lista das atividades economicas 
     */
    public ArrayList<AtividadesEconomicas> getAti_eco(){
        ArrayList<AtividadesEconomicas> r = new ArrayList<AtividadesEconomicas>();
        
        for(AtividadesEconomicas s: this.ati_eco){
            AtividadeEconomica x = (AtividadeEconomica) s;
            r.add(x.clone());
        }
        return r;    
    }
    /**
     * Metodo que devolve o valor da fatura
     * @return Valor da fatura
     */
    public double getValor(){
        return this.valor;
    }
    
    //setters
    /**
     * Metodo que define o id da fatura
     * @param id Id da fatura
     */
    public void setId(int id){
        this.id=id;
    }
    /**
     * Metodo que define o nif do emitente da fatura
     * @param nif_emitente Nif do emitente da fatura
     */
    public void setNif_emitente(int nif_emitente){
        this.nif_emitente=nif_emitente;
    }
    /**
     * Metodo que define o nome do emitente da fatura
     * @param emitente Nome do emitente da fatura
     */
    public void setEmitente(String emitente){
        this.emitente=emitente;
    }
    /**
     * Metodo que define a data da fatura
     * @param data Data da fatura
     */
    public void setData(LocalDate data){
        this.data=data;
    }
    /**
     * Metodo que define o nif do detentor da fatura
     * @param nif_cliente Nif do detentor da fatura
     */
    public void setNif_cliente(int nif_cliente){
        this.nif_cliente=nif_cliente;
    }
    /**
     * Metodo que define a despesa da fatura
     * @param despesa Nome da despesa da fatura
     */
    public void setDespesa(String despesa){
        this.despesa=despesa;
    }
    /**
     * Metodo que define a lista das atividades economicas possiveis da fatura
     * @param ati_eco Lista de atividades economicas
     */
    public void setAti_eco(ArrayList<AtividadesEconomicas> ati_eco){
        this.ati_eco=new ArrayList<AtividadesEconomicas>();
        for(AtividadesEconomicas s: ati_eco)
            this.ati_eco.add(s);
    }
    /**
     * Metodo que define o valor da fatura
     * @param valor Valor da fatura
     */
    public void setValor(double valor){
        this.valor=valor;
    }
    /**
     * Metodo que define se uma fatura e valida ou nao
     * @param valida Boolean que diz se uma fatura e valida ou nao
     */
    public void setValida(boolean valida){
        this.valida = valida;
    }
    /**
     * Metodo que define se uma fatura esta validada ou nao
     * @param validada Boolean que diz se uma fatura esta validada ou nao
     */
    public void setValidada(boolean validada){
        this.validada = validada;
    }
    /**
     * Metodo que verifica a igualdade entre uma fatura e um objeto
     * @param o Objeto a comparar
     * @return True se sao iguais e False se nao sao iguais
     */    
    public boolean equals(Object o){
        if (this==o)
            return true;
        
        if (o==null || this.getClass() != o.getClass())
            return false;
            
        Fatura a= (Fatura) o;
        return (this.id == a.getId() && this.nif_emitente==a.getNif_emitente() && this.emitente.equals(a.getEmitente()) &&
                this.data.equals(a.getData()) && this.nif_cliente==a.getNif_cliente() && 
                this.despesa.equals(a.getDespesa()) && this.ati_eco.equals(a.getAti_eco()) &&
                this.valor==a.getValor() && this.valida==a.getValida() && this.validada==a.getValidada());
    }
    /**
     * Metodo que transforma uma fatura numa String
     * @return String com os parametros da fatura
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\nId: ");sb.append(this.id);
        sb.append("\nNif do emitente: ");sb.append(this.nif_emitente);
        sb.append("\nEmitente: ");sb.append(this.emitente);
        sb.append("\nData: ");sb.append(this.data);
        sb.append("\nNif do Cliente: ");sb.append(this.nif_cliente);
        sb.append("\nDescriçao da despesa: ");sb.append(this.despesa);
        sb.append("\nAtividade economica: ");sb.append(this.ati_eco.toString());
        sb.append("\nValor: ");sb.append(this.valor);
        
        return sb.toString();
    }
    /**
     * Metodo que duplica uma fatura
     * @return Fatura duplicada
     */    
    public Fatura clone (){
        return new Fatura (this);
    }    
}