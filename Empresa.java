import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.HashMap;
/**
 * Classe das empresas
 * @version 27/5/2018
 */



public class Empresa extends Entidade implements Serializable
{
    // instance variables 
    
    private HashMap<String,Double> localidadesInterior;
    private ArrayList<AtividadesEconomicas> act_eco;
    private double factor_ded;
    /**
     * Metodo que coloca no map "localidadesInterior" os respetivos pares;
     */
    public void setLocalidadesInterior(){
      this.localidadesInterior.put("Alentejo",0.09);
      this.localidadesInterior.put("Beja",0.05);
      this.localidadesInterior.put("Tras-dos-Montes",0.05);
      this.localidadesInterior.put("Bragança",0.10);
      this.localidadesInterior.put("Vila Real", 0.07);
      this.localidadesInterior.put("Guarda",0.08);
      this.localidadesInterior.put("Evora",0.08);
    }
    
    /**
     * Construtor vazio para a classe Empresa
     */
    public Empresa()
    {
        // initialise instance variables
       super();
       this.localidadesInterior = new HashMap<String,Double>();
       this.setLocalidadesInterior();
       this.act_eco=new ArrayList<AtividadesEconomicas>();
       this.factor_ded = 0;
       
     
    }

    
    /**
     * Constructor standard para a classe Empresa
     * @param nif Nif da empresa
     * @param email Email da empresa
     * @param nome Nome da empresa
     * @param morada Morada da empresa
     * @param password Password da empresa
     * @param act_eco Lista com as atividades economicas em que a empresa esta inserida
     * @param faturas Map das faturas da empresa
     */
    public Empresa(int nif, String email, String nome, String morada, 
        String password, ArrayList<AtividadesEconomicas> act_eco, 
                HashMap<Integer,Fatura> faturas)
    {
        // initialise instance variables
       super(nif,email,nome,morada,password,faturas);
       this.act_eco=new ArrayList<AtividadesEconomicas>();
       this.localidadesInterior = new HashMap<String,Double>();
       for(AtividadesEconomicas s: act_eco)
        this.act_eco.add(s);
       this.setLocalidadesInterior();
       
       
       
       this.factor_ded=reducaoImposto();
       
     
    }
    
    /**
     * Constructor copy para a classe Empresa
     * @param umaEmpresa Empresa que se pretende copiar
     */
    public Empresa(Empresa umaEmpresa) {
        super(umaEmpresa);
        this.localidadesInterior = new HashMap<String,Double>();
        setLocalidadesInterior();
        this.act_eco=umaEmpresa.getAct_eco();
        this.factor_ded=umaEmpresa.getFactor_ded();    
    } 
    
    
    //getters
    
    /**
     * Metodo que devolve o ArrayList de atividades economicas
     * @return ArrayList de atividades economicas
     */
    public ArrayList<AtividadesEconomicas> getAct_eco() {
         ArrayList<AtividadesEconomicas> r = new ArrayList<>();
         for(AtividadesEconomicas s: this.act_eco)
            r.add(s);
         return r;
    }
    /**
     * Metodo que devolve o fator de deduçao da empresa
     * @return Fator de deduçao da empresa
     */
    public double getFactor_ded() {
        return this.factor_ded;
    }
    /**
     * Metodo que devolve o map das faturas ordenado por data de cada contribuinte
     * @param begin Data de inicio das faturas
     * @param end Data de fim das faturas
     * @return Map das faturas ordenado por data de cada contribuinte
     */
    public HashMap<Integer,TreeSet<Fatura>>getFaturasPorContEntreDatas(LocalDate begin, LocalDate end){
        HashMap<Integer,TreeSet<Fatura>> r = new HashMap<>();
        for(Fatura f: this.getFaturas().values()){
            if(f.getData().compareTo(begin) >= 0 && f.getData().compareTo(end) <= 0)  
                if(r.containsKey(f.getNif_cliente())){
                        r.get(f.getNif_cliente()).add(f.clone());
                    }else{
                        TreeSet<Fatura> x = new TreeSet<>(new ComparadorValorDespesa());
                        x.add(f.clone());
                        r.put(f.getNif_cliente(),x);
                    }
        }
        
        return r;
    }
    /**
     * Metodo que devolve o map das faturas ordenado por valor de cada contribuinte
     * @return Map das faturas ordenadas por valor de cada contribuinte
     */
    public HashMap<Integer,TreeSet<Fatura>> getFaturasPorContPorValor(){
        HashMap<Integer,TreeSet<Fatura>> r = new HashMap<>();
        for(Fatura f: this.getFaturas().values()){
            if(r.containsKey(f.getNif_cliente())){
                r.get(f.getNif_cliente()).add(f.clone());
            }else{
                TreeSet<Fatura> x = new TreeSet<>(new ComparadorValorDespesa());
                x.add(f.clone());
                r.put(f.getNif_cliente(),x);
            }
        }
        
        return r;
    }
    
    // setters
    /**
     * Metodo que define o fator de deduçao
     * @param factor_ded Fator de deduçao
     */
    public void setFactor_ded(double factor_ded) {
        this.factor_ded=factor_ded;
    }
    /**
     * Metodo que define o ArrayList das atividades economicas
     * @param act_eco ArrayList de atividades economicas
     */
    public void setAct_eco(ArrayList<AtividadesEconomicas> act_eco) {
       this.act_eco = new ArrayList<AtividadesEconomicas>();
       for(AtividadesEconomicas s: act_eco)
        this.act_eco.add(s);
    }
    
    /**
     * Metodo que devolve o valor total das faturas num intervalo de datas
     * @param begin Data inicial
     * @param end Data final
     * @return Valor total das faturas nesse intervalo de tempo
     */
    public double total_fatorado(LocalDate begin,LocalDate end) {
        return this.getFaturas().values().stream()
                                         .filter(f->f.getData().compareTo(begin) >=0 && f.getData().compareTo(end) <=0 && f.getNif_emitente() == this.getNif())
                                         .mapToDouble(f -> f.getValor()).sum();
    }
    /**
     * Metodo que verifica a igualdade entre uma empresa e um objeto
     * @param o Objeto a comparar
     * @return True se os dois sao iguais e False se nao sao iguais
     */
    public boolean equals(Object o){
        if(this==o)
            return true;
        
        if(o==null || this.getClass()!=o.getClass())
            return false;
        
        Empresa a = (Empresa) o;
        return (super.equals(a) && 
                this.act_eco.equals(a.getAct_eco()) && 
                  this.factor_ded == a.factor_ded );
    }
    /**
     * Metodo que transforma a empresa numa String
     * @return String com os parametros da empresa
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        
        sb.append("\nActividade economica: ");sb.append(this.act_eco.toString());
        sb.append("\nFactor de deduçao: ");sb.append(this.factor_ded);
        
        return sb.toString();
    }
    /**
     * Metodo que duplica uma empresa
     * @return Empresa duplicada
     */
    public Empresa clone() {
        return new Empresa(this); 
    }
    /**
     * Metodo que devolve a lista de faturas ordenada por data das faturas
     * @return Lista ordenada por data das faturas
     */
    public List<Fatura> faturasPorData(){
        TreeSet<Fatura> TreeFatData= new TreeSet(new ComparadorData());
        for (Fatura f : this.getFaturas().values()){
            TreeFatData.add(f.clone());
        }
        List<Fatura> fatData = new ArrayList<Fatura>(TreeFatData);
        return fatData;
    }
    /**
     * Metodo que devolve a lista de faturas ordenada por valor das faturas
     * @return Lista ordenada por valor das faturas
     */
    public List<Fatura> faturasPorValor(){
        TreeSet<Fatura> TreeFatValor= new TreeSet(new ComparadorValorDespesa());
        for (Fatura f : this.getFaturas().values()){
            TreeFatValor.add(f.clone());
        }
        List<Fatura> fatValor = new ArrayList<Fatura>(TreeFatValor);
        return fatValor;
    }
    /**
     * Metodo que devolve a reduçao do imposto caso a morada da empresa contenha uma das localidades definida no map localidadesInterior 
     * @return A reduçao de imposto
     */
    public double reducaoImposto(){
        double i=0.0;
        Iterator<Map.Entry<String,Double>> it= this.localidadesInterior.entrySet().iterator();
        while(it.hasNext() && i!=0){
            Map.Entry<String,Double> pair= it.next();
            if (this.getMorada().toLowerCase().contains(pair.getKey().toLowerCase()))
                i=pair.getValue();
        }
        return i;
    }
}    