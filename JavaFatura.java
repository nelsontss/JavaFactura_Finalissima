import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collections;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream;
import java.io.IOException; 
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.Serializable;

/**
 * Classe com todas as entidades
 * @version 27/5/2018
 */
public class JavaFatura implements Serializable
{   
    private Map<Integer,Entidade> entidades;
    private int nFaturas;
    //private List<Fatura> faturas;
    /**
     * Construtor vazio de JavaFatura
     */
    public JavaFatura(){
       this.entidades = new HashMap<Integer,Entidade>();
       this.nFaturas = 0;
       //this.faturas = new ArrayList<Fatura>();
    } 
    /**
     * Construtor parametrizado de JavaFatura
     * @param entidades Map do nif das entidades e as proprias entidades do sistema
     * @param faturas Lista de faturas do sistema
     * @param nFaturas Numero de faturas do sistema
     */
    public JavaFatura(Map<Integer,Entidade> entidades, List<Fatura> faturas,int nFaturas){
       this.entidades = new HashMap<>();
       //this.faturas = new ArrayList<>();
       for(Map.Entry<Integer,Entidade> e : entidades.entrySet())
        this.entidades.put(e.getKey(), e.getValue().clone());
       this.nFaturas = nFaturas;
       /*for(Fatura e : faturas)
       this.faturas.add(e.clone());
       */
    }
    /**
     * Construtor de copia de JavaFatura
     * @param j JavaFatura que se pretende copiar
     */
    public JavaFatura(JavaFatura j){
        this.entidades = j.getEntidades();
        this.nFaturas = j.getNFaturas();
        //this.faturas = j.getFaturas();
    }
    /**
     * Metodo que obtem o numero de faturas
     * @return Numero de faturas do sistema
     */
    public int getNFaturas(){
        return this.nFaturas;
    }
    /**
     * Metodo que define o Map das Entidades e dos seus nifs
     * @param entidades Map das entidades e dos seus nifs
     */
    public void setEntidades(Map<Integer,Entidade> entidades) {
       HashMap<Integer,Entidade> r = new HashMap<>();
       for(Map.Entry<Integer,Entidade> e : entidades.entrySet())
        r.put(e.getKey(), e.getValue().clone());
       this.entidades=r;
    }
    
    /*public void setFaturas(ArrayList<Fatura> faturas) {
       ArrayList<Fatura> r = new ArrayList<>();
       for(Fatura e : faturas)
        r.add(e.clone());
       this.faturas = r;
    }*/
    
    /**
     * Metodo que obtem o Map das entidades e dos seus nifs
     * @return Map das entidades e dos seus nifs
     */
    public Map<Integer,Entidade> getEntidades() {
      
       return this.entidades;
    }
    
    /*public List getFaturas() {
       ArrayList<Fatura> r = new ArrayList<>();
       for(Fatura e : faturas)
        r.add(e.clone());
       return r;
    }*/
    /**
     * Metodo que cria uma empresa e coloca-a no map das entidades
     * @param nif Nif da empresa
     * @param email Email da empresa
     * @param nome Nome da empresa
     * @param morada Morada da empresa
     * @param password password da empresa
     * @param act_eco Lista com as atividades economicas em que a empresa esta inserida
     */
    public void regista_empresa(int nif, String email, String nome, String morada, 
        String password, ArrayList<AtividadesEconomicas> act_eco) throws ContribuinteJaExisteException {
        if(!entidades.containsKey(nif)){
            Empresa r = new Empresa(nif, email, nome, morada, password, act_eco, 
                  new HashMap<>());
            entidades.put(nif,r); 
            
        }else{
             throw new ContribuinteJaExisteException("Empresa " + nif + " ja existe");
        }
    }
    /**
     * Metodo que cria um contribuinte e coloca-o no map das entidades
     * @param nif Nif do contribuinte
     * @param email Email do contribuinte
     * @param nome Nome do contribuinte
     * @param morada Morada do contribuinte
     * @param password password do contribuinte
     * @param agr_fam Numero de pessoas do agregado familiar
     * @param nifs_agr Lista com os nifs do agregado familiar
     */
    public void regista_contribuinte(int nif, String email, String nome, String morada, 
        String password, int agr_fam, ArrayList<Integer> nifs_agr) throws ContribuinteJaExisteException{
         if(!entidades.containsKey(nif)){
             Contribuinte r = new Contribuinte(nif,email,nome,morada,password,
                        agr_fam,nifs_agr,new ArrayList<AtividadesEconomicas>(),
                        new HashMap<Integer,Fatura>());
             entidades.put(nif,r);
         }else{
             throw new ContribuinteJaExisteException("Contribuinte " + nif + " ja existe");
         }
    }
    /**
     * Metodo que permite fazer o login de uma entidade
     * @param nif Nif da entidade
     * @param password Password da entidade
     * @return True se e possivel fazer login, False se nao e possivel
     */
    public boolean login(int nif, String password){
        if(entidades.isEmpty() || !entidades.containsKey(nif))
            return false;
        if(entidades.get(nif).getPassword().equals(password))
            return true;
        return false;    
    }
    /**
     * Metodo que passa uma fatura para o respetivo contribuinte e empresa
     * @param nif_cont Nif do contribuinte
     * @param nif_e Nif da empresa
     * @param descri Descriçao da despesa
     * @param valor Valor da fatura
     */
    public void passa_fatura (int nif_cont, int nif_e, String descri, double valor){
        Empresa e = (Empresa) entidades.get(nif_e);
        Entidade c = entidades.get(nif_cont);
        if(e!=null && c!=null){
            Fatura f = new Fatura(nFaturas,nif_e,e.getNome(),LocalDate.now(),nif_cont,descri,
                                    e.getAct_eco(),valor);
            nFaturas++;                        
            e.addFatura(f);
            c.addFatura(f);        
        }
    }
    
    /**
     * Metodo que grava em ficheiro o estado
     * @param filename Nome do ficheiro
     */
    public void save(String fileName) throws FileNotFoundException,IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
    /**
     * Metodo que carrega o ficheiro com o estado
     * @param filename Nome do ficheiro
     * @return Estado do JavaFatura
     */
    public static JavaFatura load(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        JavaFatura u = (JavaFatura) ois.readObject();
        ois.close();
        return u;
    }
    /**
     * Metodo que calcula as deduçoes fiscais das entidades
     * @param nif Nif da entidade
     * @return Valor da deduçao fiscal
     */
    public double calculaMontDeducao(int nif) throws ContribuinteNaoExisteException{
        double sum=0;
        if (this.entidades.containsKey(nif)){
             Entidade a=this.entidades.get(nif);
             if (a instanceof Contribuinte){
                Contribuinte x = (Contribuinte) a;
                for (Fatura f: a.getFaturas().values()){
                    if (f.getValida() && x.getCodEco().contains(f.getAti_eco().get(0))){ 
                        
                        sum=sum+(f.getValor()*
                             (f.getAti_eco().get(0).getFatorDed() + 
                                    x.reducaoImposto()) );
                    }
                } 
            }
            if (a instanceof Empresa){
                Empresa e=(Empresa) a;
                for(Fatura f: e.getFaturas().values()){
                  if (f.getValida() && f.getNif_cliente() == e.getNif()){ 
                        
                        sum=sum+(f.getValor()*
                             (f.getAti_eco().get(0).getFatorDed() + 
                                    e.reducaoImposto()));
                    }  
                }
                
            }
        }else
            throw new ContribuinteNaoExisteException("O contribuinte " + nif+ " ja existe!");
        return sum;      
    }
    /**
     * Metodo que calcula o valor das deduçoes fiscais do agregado familiar
     * @param nif Nif do contribuinte
     * @return Valor da deduçao fiscal do agregado familiar do contribuinte
     */
    public double despesaAgrFam (int nif) throws ContribuinteNaoExisteException{
        double sum = 0;
        double total=0;
        int tam=0;
        try{
            sum= calculaMontDeducao(nif);
            Entidade a =this.entidades.get(nif);
            if (a instanceof Contribuinte){
                  Contribuinte c = (Contribuinte) a;
                  tam=c.getAgr_fam();
                  for(int s: c.getNifs_agr())
                  sum+=calculaMontDeducao(s);
            }
            total= tam*sum;
            return sum;
        }catch(ContribuinteNaoExisteException e){
            throw e;
        }
        
    }
    /**
     * Metodo que devolve a lista dos 10 contribuintes que mais despesas tem no sistema
     * @return Lista dos 10 contribuintes que mais despesas tem no sistema
     */
    public List<Entidade> eValor10(){
        return this.entidades.values().stream()
                                      .sorted(new ComparadorValorDespesaEntidade())
                                      .limit(10)
                                      .collect(Collectors.toList());
    }
    /**
     * Metodo que devolve as x empresas que mais faturas tem no sistema e associam-se ao seu valor de deduçao fiscal
     * @param x Numero de empresas que pretendemos obter com o metodo
     * @return Map com as x empresas e as respetivas deduçoes fiscais
     */
    public List<Entidade> maisFatEmp(int x) throws ContribuinteNaoExisteException{
        Set<Entidade> set = new TreeSet<Entidade>(new ComparadorFaturasEmpresa());
        for (Entidade e: this.entidades.values())
            if(e.getClass() == Empresa.class)
                set.add(e.clone());
        List<Entidade> aux = set.stream().limit(x).collect(Collectors.toList());
        
        
        return aux;
    }
    /**
     * Metodo que soma o valor de todas as faturas 
     * @param map Map com as entidades e respetivas deduçoes fiscais
     * @return Soma de todas as deduçoes fiscais do map
     */
    public double somaFat(List<Entidade> map) throws ContribuinteNaoExisteException{
        double sum=0.0;
        for(Entidade mp: map)
            sum+=calculaMontDeducao(mp.getNif());
        return sum;
    }
}
