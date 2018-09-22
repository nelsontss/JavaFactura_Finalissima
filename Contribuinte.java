import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
/**
 * Classe dos contribuintes
 * @version 27/5/2018
 */



public class Contribuinte  extends Entidade implements Serializable
{
    // instance variables 
    private int agr_fam;
    private ArrayList<Integer> nifs_agr;
    private static final double coef = 0; 
    private ArrayList<AtividadesEconomicas> codEco;

    /**
     * Construtor vazio para a classe Contribuinte
     */
    public Contribuinte()
    {
        // initialise instance variables
       super();
       this.agr_fam=0;
       this.nifs_agr=new ArrayList<Integer>();
       this.codEco= new ArrayList<AtividadesEconomicas>();
     
    }

    
    /**
     * Constructor standard para a classe Contribuinte
     * @param nif Nif do contribuinte
     * @param email Email do contribuinte
     * @param nome Nome do contribuinte
     * @param morada Morada do contribuinte
     * @param password Password do contribuinte
     * @param agr_fam Numero de pessoas do agregado familiar
     * @param nifs_agr Lista com os nifs do agregado familiar
     * @param ae Lista com as atividades economicas para as quais o contribuinte pode descontar
     * @param faturas Map com as faturas do contribuinte
     */
    public Contribuinte(int nif, String email, String nome, String morada, 
        String password, int agr_fam, ArrayList<Integer> nifs_agr, ArrayList<AtividadesEconomicas> ae, 
            HashMap<Integer,Fatura>faturas)
    {
        // initialise instance variables
       super(nif,email,nome,morada,password,faturas);
       this.agr_fam=agr_fam;
       this.nifs_agr= new ArrayList<Integer>(agr_fam);
       for(Integer s: nifs_agr)
           this.nifs_agr.add(s);
       this.codEco = new ArrayList<AtividadesEconomicas>();
       this.codEco.add(new AtividadeEconomica("Saude"));
       this.codEco.add(new AtividadeEconomica("Educaçao"));
       this.codEco.add(new AtividadeEconomica("Restauraçao"));
       this.codEco.add(new AtividadeEconomica("Transportes"));
       this.codEco.add(new AtividadeEconomica("Reparaçao de veiculos"));
       this.codEco.add(new AtividadeEconomica("Serviços bancarios"));
       this.codEco.add(new AtividadeEconomica("Serviços de fornecimento de eletricidade e agua"));
       this.codEco.add(new AtividadeEconomica("Cabeleireiros"));
    }
    
    /**
     * Constructor copy para a classe Contribuinte
     * @param umContribuinte Contribuinte que se pretende duplicar
     * @return Contribuinte duplicado
     */
    public Contribuinte(Contribuinte umContribuinte) {
       super(umContribuinte);
       this.agr_fam=umContribuinte.getAgr_fam();
       this.nifs_agr= umContribuinte.getNifs_agr(); 
       this.codEco= umContribuinte.getCodEco();
    } 
    
    
    //getters
    /**
     * Metodo que devolve o numero do agregado familiar
     * @return Numero do agregado familiar
     */
    public int getAgr_fam() {
        return this.agr_fam;
    }
    /**
     * Metodo que devolve a lista dos nifs do agregado familiar
     * @return Lista com os nifs do agregado familiar
     */
    public ArrayList<Integer> getNifs_agr() {
        ArrayList<Integer> r = new ArrayList<Integer>();
        for(Integer s: this.nifs_agr)
            r.add(s);
        return r;
    }
    /**
     * Metodo que devolve a lista das atividades economicas
     * @return Lista de atividades economicas
     */
    public ArrayList<AtividadesEconomicas> getCodEco() {
        ArrayList<AtividadesEconomicas> r = new ArrayList<AtividadesEconomicas>();
        for(AtividadesEconomicas a: this.codEco){
            AtividadeEconomica x = (AtividadeEconomica) a;
            r.add(x.clone());
        }
        return r;
    }
    
    // setters
    /**
     * Metodo que define a lista de nifs do agregado familiar
     * @param nifs_agr Lista com os nifs do agregado familiar
     */
    public void setNifs_agr(ArrayList<Integer> nifs_agr) {
        this.nifs_agr=new ArrayList<Integer>();
        for(Integer s: nifs_agr)
            this.nifs_agr.add(s);
    }
    /**
     * Metodo que define a lista das atividades economicas
     * @param codsEco Lista das atividades economicas
     */
    public void setCodEco(ArrayList<AtividadesEconomicas> codsEco) {
        this.codEco=new ArrayList<AtividadesEconomicas>();
        for(AtividadesEconomicas a: codsEco){
            AtividadeEconomica x = (AtividadeEconomica) a;
            this.codEco.add(x.clone());
        }
    }
    /**
     * Metodo que devolve a reduçao de imposto do contribuinte
     * @return Reduçao de imposto
     */
    public double reducaoImposto(){
        if(agr_fam <= 5){
            return 0;
        }
        return 0.05 * (agr_fam-6);
    }
    /**
     * Metodo que verifica se um contribuinte e um objeto sao iguais
     * @param o Objeto a comparar
     * @return True se sao iguais e False se nao sao iguais
     */
    public boolean equals(Object o){
        if(this==o)
            return true;
        
        if(o==null || this.getClass()!=o.getClass())
            return false;
        
        Contribuinte a = (Contribuinte) o;
        return super.equals(a) && this.agr_fam==a.getAgr_fam() &&
                        this.nifs_agr.equals(a.getNifs_agr()) 
                        && this.codEco.equals(a.getCodEco());
    }
    /**
     * Metodo que transforma um contribuinte numa String
     * @return Contribuinte transformada numa String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
      
        sb.append("\nNumero de pessoas no agregado familiar: ");sb.append(this.agr_fam);
        sb.append("\nNif's do agregado familiar: ");sb.append(this.nifs_agr.toString());
        //sb.append("\nPossiveis deduçoes: ");sb.append(this.codEco.toString());
        return sb.toString();
    }
    /**
     * Metodo que duplica um contribuinte
     * @return Contribuinte duplicado
     */
    public Contribuinte clone() {
        return new Contribuinte(this); 
    }
    
}