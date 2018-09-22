import java.util.HashMap;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * Classe das entidades
 * @version 27/5/2018
 */
public class Entidade implements Serializable
{
    // instance variables - replace the example below with your own
    private int nif;
    private String email;
    private String nome;
    private String morada;
    private String password;
    private HashMap<Integer,Fatura> faturas;
    
    /**
     * Construtor vazio de Entidade
     */
    public Entidade(){
        this.nif=0;
        this.email="";
        this.nome="";
        this.morada="";
        this.password="";
        this.faturas=new HashMap<Integer,Fatura>();
    }
    
    /**
     * Construtor parametrizado de Entidade
     * @param nif Nif correspondente à entidade
     * @param nome Nome da entidade
     * @param morada Morada da entidade
     * @param Password Password da Entidade
     * @faturas map das faturas
     */
    public Entidade(int nif, String email, String nome, String morada, String password,
                        HashMap<Integer,Fatura> faturas){
        this.nif=nif;
        this.email=email;
        this.nome=nome;
        this.morada=morada;
        this.password=password;
        this.faturas = new HashMap<Integer,Fatura>();
        for(Fatura s: faturas.values())
            this.faturas.put(s.getId(),s.clone());
    }
    /**
     * Construtor de copia de Entidade
     * @param j Entidade que se pretende copiar
     */
    public Entidade(Entidade e) {
        this.nif=e.getNif();
       this.email=e.getEmail();
       this.nome=e.getNome();
       this.morada=e.getMorada();
       this.password=e.getPassword();
       this.faturas= e.getFaturas();
    }
    
    //getters
    /**
     * Metodo que obtem o nif
     * @return nif da entidade
     */
    public int getNif() {
        return this.nif;
    }
    /**
     * Metodo que obtem o email
     * @return email da entidade
     */
    public String getEmail() {
        return this.email;
    }
    /**
     * Metodo que obtem o nome
     * @return nome da entidade
     */
    public String getNome() {
        return this.nome;
    }
    /**
     * Metodo que obtem a morada
     * @return morada da entidade
     */
    public String getMorada() {
        return this.morada;
    }
    /**
     * Metodo que obtem a password
     * @return password da entidade
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Metodo que obtem o map das faturas e o id da fatura
     * @return map das faturas e id da fatura
     */
    public HashMap<Integer,Fatura> getFaturas() {
       HashMap<Integer,Fatura> r = new HashMap<Integer,Fatura>();
       for(Fatura s: this.faturas.values())
        r.put(s.getId(),s.clone());
       return r;
    }
    
    // setters
    /**
     * Metodo que define o nif
     * @param nif da entidade
     */
    public void setNif(int nif) {
        this.nif = nif;
    }
    /**
     * Metodo que define o email
     * @param email da entidade
     */
    public void setEmail(String email) {
        this.email=email;
    }
    /**
     * Metodo que define o nome
     * @param nome da entidade
     */
    public void setNome(String nome) {
        this.nome=nome;
    }
    /**
     * Metodo que define a morada
     * @param morada da entidade
     */
    public void setMorada(String morada) {
        this.morada=morada;
    }
    /**
     * Metodo que define a password
     * @param password da entidade
     */
    public void setPassword(String password) {
        this.password=password;
    }
    /**
     * Metodo que define o map de faturas e id da fatura
     * @param faturas e id das faturas da entidade
     */
    public void setFaturas(HashMap<Integer,Fatura> faturas) {
        this.faturas=new HashMap<Integer,Fatura>();
        for(Fatura s: faturas.values())
            this.faturas.put(s.getId(),s.clone());
    }
    /**
     * Metodo que adiciona uma fatura às faturas guardadas
     * @param fatura a adicionar
     */
    public void addFatura(Fatura f){
        this.faturas.put(f.getId(),f.clone());
    }
    /**
     * Metodo que testa a igualdade entre um objeto e uma entidade
     * @param entidade
     * @return true se forem iguais e false se forem diferentes
     */
    public boolean equals(Object o){
        if(this==o)
            return true;
        
        if(o==null || this.getClass()!=o.getClass())
            return false;
        
        Entidade a = (Entidade) o;
        return (this.nif == a.getNif() && this.email.equals(a.getEmail()) &&
            this.nome.equals(a.getNome()) && this.morada.equals(a.getMorada()) &&
                  this.password.equals(a.getPassword()) && 
                           this.faturas.equals(a.getFaturas()));
    }
    /**
     * Metodo que transforma uma entidade numa String
     * @return String com os parametros da entidade
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\nNif: ");sb.append(this.nif);
        sb.append("\nEmail: ");sb.append(this.email);
        sb.append("\nNome: ");sb.append(this.nome);
        sb.append("\nMorada: ");sb.append(this.morada);
        sb.append("\nPassword: ");sb.append(this.password);
        sb.append("\nFaturas: ");sb.append(faturas.toString());
        return sb.toString();
    }
    /**
     * Metodo que duplica uma entidade
     * @return entidade duplicada
     */    
    public Entidade clone(){
        return new Entidade(this);
    }
    /**
     * Metodo que valida uma fatura
     * @param id id da entidade
     * @param n Indice do array da atividade economica
     */    
    public void validaFatura(int id, int n){
        Fatura f = this.faturas.get(id);
        f.setValidada(true);
        f.setValida(true);
        ArrayList<AtividadesEconomicas> r = new ArrayList<>();
        r.add(f.getAti_eco().get(n));
        f.setAti_eco(r);
    }
    /**
     * Metodo que devolve o valor total das suas faturas
     * @return Valor total das faturas 
     */
    public double totalGasto(){
        double valor = 0;
        for(Fatura f: this.getFaturas().values()){
            if(this.getNif() ==  f.getNif_cliente())
                valor+=f.getValor();
        }
        return valor;
    }
}


