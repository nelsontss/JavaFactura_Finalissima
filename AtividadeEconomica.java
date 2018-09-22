import java.io.Serializable;
/**
 * Classe de atividade económica
  * @version 27/05
   */
public class AtividadeEconomica implements AtividadesEconomicas, Serializable
{
    // instance variables - replace the example below with your own
    private String nome;
    private double fatorDed;
    private double valorMax;
    
    /**
     * Construtor vazio de Atividade economica
     */
    public AtividadeEconomica()
    {
        // initialise instance variables
        this.nome = "";
        this.fatorDed = 0.0;
        this.valorMax=0.0;
    }
    
    
    
        /**
     * Construtor parametrizado de Entidade
     * @param nome Nome da entidade
     */
    public AtividadeEconomica(String nome){
        this.nome = nome;
        
            if(nome.equals( "Saude")){
                 this.fatorDed = 0.4;
                 this.valorMax = 5000;
                }else
            if(nome.equals( "Educaçao")) {
                this.fatorDed = 0.4;
                this.valorMax = 2500;
            }else
            if(nome.equals( "Restauraçao")){
                this.fatorDed = 0.3; 
                this.valorMax= 50;
            }else
            if(nome.equals( "Transportes")){ 
                this.fatorDed = 0.2;
                this.valorMax = 500;
            }else
            if(nome.equals( "Reparaçao de veiculos")) {
                this.fatorDed= 0.25; 
                this.valorMax= 1000;
            }else
            if(nome.equals( "Serviços bancarios")) {
                this.fatorDed= 0.3; 
                this.valorMax= 1500;
            }else
            if(nome.equals( "Serviços de fornecimento de eletricidade e agua")) {
                this.fatorDed= 0.4; 
                this.valorMax= 2000;
            }else
            if(nome.equals( "Cabeleireiros")){ 
                this.fatorDed= 0.05; 
                this.valorMax= 40;
            } else {
                this.fatorDed = 0;
                this.valorMax = 0;
            }
            
            
    }
         /**
     * Construtor de copia de Entidade
     * @param j Entidade que se pretende copiar
     */
    public AtividadeEconomica(AtividadeEconomica a){
        this.nome = a.getNome();
        this.fatorDed = a.getFatorDed();
        this.valorMax = a.getValorMax();
    }
    
    /**
     * Metodo que obtem o nome
     * @return nome
     */
    public String getNome(){
        return this.nome;
    }
        /**
     * Metodo que obtem o fator de dedução
     * @return fator de dedução
     */
    public double getFatorDed(){
        return this.fatorDed;
    }
        /**
     * Metodo que obtem o valor máximo que pode ser deduzido
     * @return valor máximo
     */
    public double getValorMax(){
        return this.valorMax;
    }
    
        /**
     * Metodo que define o nome na Atividade economica
     * @param nome Nome
     */
    public void setNome(String nome){
        this.nome=nome;
    }
        /**
     * Metodo que define o fator de dedução
     * @param fatorDed fator de dedução
     */
    public void setFatorDed(double fatorDed){
        this.fatorDed = fatorDed;
    }
        /**
     * Metodo que define o valor maximo de dedução
     * @param valorMax Valor maximo de dedução
     */
    public void setValorMax(double valorMax){
        this.valorMax = valorMax;
    }
        /**
     * Metodo que verifica a igualdade entre uma Atividade economica e um objeto
     * @param o Objeto a comparar
     * @return True se sao iguais e False se nao sao iguais
     */   
    public boolean equals(Object o){
        
        if (this==o)
            return true;
        
        if (o==null || this.getClass() != o.getClass())
            return false;
            
        AtividadeEconomica a = (AtividadeEconomica) o;
            
        return this.nome.equals(a.getNome()) && this.fatorDed == a.getFatorDed() 
                && this.valorMax == a.getValorMax();
    }
        /**
     * Metodo que transforma uma atividade economica numa String
     * @return String com os parametros da atividade economica
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.nome);
        return sb.toString();
    }
        /**
     * Metodo que duplica uma fatura
     * @return Fatura duplicada
     */    
    public AtividadeEconomica clone(){
        return new AtividadeEconomica(this);
    }
}
