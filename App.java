import java.io.IOException;
import java.time.LocalDate; 
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 
import java.io.FileNotFoundException;
/**
 * Menus do Projeto
 * @version 27/5/2018
 */
public class App
{
   private JavaFatura estado;
   
   public static void main(String[] args){
       App a = new App();
       
       a.run();
   }
   /**
    * Metodo que carrega o estado pelo ficheiro onde este esta guardado
    * @param save Nome do ficheiro
    * @return Estado do sistema
    */
   public static JavaFatura carregaEstado(String save){
        try{
            return JavaFatura.load(save);   
        }
        catch (FileNotFoundException e){
            System.out.println("Ficheiro nao encontrado!");          
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Erro a aceder ao ficheiro!");
        }catch(ClassNotFoundException e){
            System.out.println("Classe nao encontrada");
        }
        return new JavaFatura();
    }
   /**
     * Metodo que testa exceptions para poder gravar o ficheiro
     * @param save Nome do ficheiro
     */
   public void grava(String save){
       try {
            this.estado.save("save");
        }catch(FileNotFoundException e){
            System.out.println("Ficheiro nao encontrado!");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Erro a aceder ao ficheiro 2!");
        } 
    } 
   /**
    * Metodo que constroi o menu para validar faturas
    * @param nif Nif da entidade
    */
   public void validarFaturaMenu(int nif) throws FaturaNaoExisteException{
        Scanner sc = new Scanner(System.in);
        Entidade e = this.estado.getEntidades().get(nif);
        e.getFaturas().values().stream().filter(s-> s.getValida() == false).toString();
        System.out.println("Prima 0 para voltar");
        System.out.println("Qual o id da fatura que deseja validar?");
        int id = sc.nextInt();
        if(e.getFaturas().containsKey(id)){
            Fatura f = e.getFaturas().get(id);
            f.toString();
            System.out.println("Escolha a atividade economica: ");
            for(int i = 0; i<f.getAti_eco().size(); i++){
               System.out.println((i+1) + " - " + f.getAti_eco().get(i).getNome());
            }
            int x = sc.nextInt();
            e.validaFatura(id,x-1);
            this.estado.getEntidades().get(f.getNif_emitente()).validaFatura(id,x-1);
        }
        else if (id!=0){
                throw new FaturaNaoExisteException("Id " + id + " nao existe!");
             }
             else
                userIndMenu(nif);
    } 
   /**
    * Metodo que tenta validar uma fatura
    * @param nif Nif da entidade
    */ 
   public void tryValidarFaturaMenu(int nif){
        try{
            this.validarFaturaMenu(nif);
        }catch(FaturaNaoExisteException e){
            System.out.println("O id de fatura que inseriu nao existe!");
            tryValidarFaturaMenu(nif);
        }
    } 
   /**
    * Metodo que imprime o montade de deduçao acumulado pelo contribuinte e agregado familiar
    * @param nif Nif da entidade
    */
   public void montanteDeducaoMenu(int nif){
        try{
            System.out.println("Montante de deduçao acumulado pelo contribuinte: " + this.estado.calculaMontDeducao(nif));
            System.out.println("Montante de deduçao acumulado pelo agregado familiar: " + this.estado.despesaAgrFam(nif));
        }catch(ContribuinteNaoExisteException e){
            System.out.println(e.getMessage());
        }
    } 
   /**
    * Metodo que constroi o menu para as opçoes do contribuinte
    * @param nif Nif da entidade(contribuinte)
    */ 
   public void userIndMenu(int nif){
        Entidade e = this.estado.getEntidades().get(nif);
        System.out.println("Bem vindo! " + e.getNome());
        System.out.println("Opçoes:");
        System.out.println("1 - Ver lista de faturas");
        System.out.println("2 - Validar Fatura");
        System.out.println("3 - Ver montante de deduçao acumulado");
        System.out.println("0 - Sair");
        System.out.println("Insira a sua opçao: ");
        Scanner sc = new Scanner(System.in);
        int sair = 1;
        do {
                switch(sc.nextInt()) {
                    case 0: sair = 0;                        
                            break;
                    case 1: System.out.println(e.getFaturas().toString());
                            break;
                    case 2: tryValidarFaturaMenu(nif);
                            break;
                    case 3: montanteDeducaoMenu(nif);
                            break;
                }
        }while(sair!=0);
        
        } 
   /**
    * Metodo que constroi o menu para imprimir as faturas no intervalo a definir
    * @param nif Nif da entidade
    */
   public void faturasNoIntervaloMenu(int nif){
        Empresa e = (Empresa)this.estado.getEntidades().get(nif);
        Scanner sc = new Scanner(System.in);
        System.out.println("Ver faturas desde: (ano-mes-dia)");
        String date = sc.nextLine();
        LocalDate begin = LocalDate.parse(date);
        System.out.println("Ate: (ano-mes-dia)");
        date = sc.nextLine();
        LocalDate end = LocalDate.parse(date);
        System.out.println(e.getFaturasPorContEntreDatas(begin,end).toString());
        
    } 
   /**
    * Metodo que constroi o menu para selecionar o tipo de ordenaçao das faturas a receber
    * @param nif Nif da entidade(Empresa)
    */     
   public void visualizarFaturasMenu(int nif){
        Empresa e = (Empresa)this.estado.getEntidades().get(nif);
        System.out.println("Opçoes:");
        System.out.println("0 - Sair");
        System.out.println("1 - Ver lista de faturas por contribuinte durante um intervalo de tempo");
        System.out.println("2 - Ver faturas por contribuinte por ordem decrescente de valor");
        System.out.println("3 - Ver faturas por ordem decrescente de valor");
        System.out.println("4 - Ver faturas ordenadas por data");
        int sair = 1;
        Scanner sc = new Scanner(System.in);
        do {
                switch(sc.nextInt()) {
                    case 0: sair = 0;                        
                            break;
                    case 1: faturasNoIntervaloMenu(nif);
                            break;
                    case 2: System.out.println(e.getFaturasPorContPorValor().toString());
                            break;
                    case 3: System.out.println(e.faturasPorValor().toString());
                            break;
                    case 4: System.out.println(e.faturasPorData().toString());
                            break;          
                }
        }while(sair!=0);
    }     
   /**
    * Metodo que constroi o menu para uma entidade(empresa) passar uma fatura para uma entidade(contribuinte)
    * @param nif Nif da entidade(empresa)
    */
   public void passarFaturaMenu(int nif){
        Scanner sc = new Scanner(System.in);
        Entidade e = this.estado.getEntidades().get(nif);
        e.getFaturas().values().stream().filter(s-> s.getValida() == false).toString();
        System.out.println("Prima 0 para voltar");
        System.out.println("Qual o nif do cliente?");
        int id = sc.nextInt();
        if(id != 0){
            System.out.println("Descriçao do produto: ");
            sc.nextLine();
            String descri = sc.nextLine();
            System.out.println("Valor da fatura: ");
            Double valor = sc.nextDouble();
            this.estado.passa_fatura(id,nif,descri,valor);
        }
    }  
   /**
    * Metodo que constroi o menu para imprimir o valor faturado entre um intervalo a definir
    * @param nif Nif da entidade(empresa)
    */
   public void totalFaturadoMenu(int nif){
        Empresa e = (Empresa)this.estado.getEntidades().get(nif);
        Scanner sc = new Scanner(System.in);
        System.out.println("Ver faturaçao desde: (ano-mes-dia)");
        String date = sc.nextLine();
        LocalDate begin = LocalDate.parse(date);
        System.out.println("Ate: (ano-mes-dia)");
        date = sc.nextLine();
        LocalDate end = LocalDate.parse(date);
        System.out.println(e.total_fatorado(begin,end));
    } 
   /**
    * Metodo que constroi o menu para as opçoes da empresa
    * @param nif Nif da entidade(empresa)
    */
   public void userEmpMenu (int nif){
        Entidade e = this.estado.getEntidades().get(nif);
        System.out.println("Bem vindo !" + e.getNome());
        System.out.println("Opçoes:");
        System.out.println("1 - Ver lista de faturas");
        System.out.println("2 - Passar fatura");
        System.out.println("3 - Ver total faturado");
        System.out.println("0 - Sair");
        System.out.println("Insira a sua opçao: ");
        Scanner sc = new Scanner(System.in);
        int sair = 1;
        do {
                switch(sc.nextInt()) {
                    case 0: sair = 0;                        
                            break;
                    case 1: visualizarFaturasMenu(nif);
                            break;
                    case 2: passarFaturaMenu(nif);
                            break;
                    case 3: totalFaturadoMenu(nif);
                            break;
                }
        }while(sair!=0);
        
    }     
   
   
    
    /**
    * Metodo que imprime o top 10 gastadores
    */
   public void imprimeTop10Gastadores(){
       List<Entidade> top = this.estado.eValor10();
       
       for(Entidade e: top){
           System.out.println(e.getNome() + "-" + e.getNif() + "-" + e.totalGasto());
       }
       
   }
    
    /**
    * Metodo que constroi o menu para as opçoes do administrador
    */
   public void adminMenu(){
        System.out.println("Opçoes:");
        System.out.println("0 - Sair");
        System.out.println("1 - Top 10 gastadores");
        System.out.println("2 - Top x de empresas com mais faturas");
        System.out.println("Insira a sua opçao: ");
        Scanner sc = new Scanner(System.in);
        int sair = 1;
        
        do {
                switch(sc.nextInt()) {
                    case 0: sair = 0;                        
                            break;
                    case 1: imprimeTop10Gastadores();
                            break;
                    case 2: System.out.println("Quantas empresas deseja ver no top?");
                            int x = sc.nextInt();    
                            try{
                                imprimeList(this.estado.maisFatEmp(x));
                                System.out.println("Total: " + this.estado.somaFat(this.estado.maisFatEmp(x)));
                            }catch(ContribuinteNaoExisteException e){
                                System.out.println(e.getMessage());
                            }
                            break;
                    
                }
        }while(sair!=0);
    }
   /**
     * Metodo que imprime o map de entidades e respetivas deduçoes fiscais
     * @param m Map com as entidades e respetivas deduçoes fiscais 
     */ 
   public void imprimeList(List<Entidade> m){
        try{
            for(Entidade me: m){
                System.out.println(me.getNome() + "-" + me.getNif() 
                        + " Valor de deduçoes fiscais: " + this.estado.calculaMontDeducao(me.getNif()));
                
            }
        }catch(ContribuinteNaoExisteException e){
            System.out.println(e.getMessage());
        }
   }
   /**
    * Metodo que constroi o menu para efetuar o login da entidade
    */
   public void loginMenu(){
        System.out.println("Caso queira regressar ao main menu prima 0: ");
        Scanner sc = new Scanner(System.in);
        System.out.println("Nif: ");
        int nif = sc.nextInt();
        if(nif==0){
            this.grava("save");
            
        }else{
            System.out.println("Password: ");
            sc.nextLine();//scan do '/n'
            String password = sc.nextLine();
            boolean f = this.estado.login(nif,password);
            if(f==false){
                System.out.println("Nif ou password incorretos");
                loginMenu();
            }else{
                if(nif == 111999111){
                        this.adminMenu();
                }else{
                    if(this.estado.getEntidades().get(nif) instanceof Contribuinte){
                        this.userIndMenu(nif);
                    }else if (this.estado.getEntidades().get(nif) instanceof Empresa){
                        this.userEmpMenu(nif);
                    }
                }
                    
            }
        }
    } 
   /**
     * Metodo que regista uma empresa no sistema
     * @param nif Nif da empresa
     * @param email Email da empresa
     * @param nome Nome da empresa
     * @param morada Morada da empresa
     * @param pass Password da empresa
     */
   public void registerEmp(int nif, String email, String nome, String morada, String pass){
        Scanner sc = new Scanner(System.in);
        System.out.println("Numero de atividades economicas: ");
        int nAti = sc.nextInt();
        sc.nextLine();
        ArrayList<AtividadesEconomicas> atiEco = new ArrayList<AtividadesEconomicas>(nAti);
        for(int i = 0; i<nAti; i++){
            System.out.println("Atividade Economica nº" + (i+1));
            atiEco.add(new AtividadeEconomica(sc.nextLine()));
        }
        
        try{
            this.estado.regista_empresa(nif, email, nome, morada, pass, atiEco);
            this.grava("save");
        
            
        }catch(ContribuinteJaExisteException e){
            System.out.println("O contribuinte com esse nif ja existe!");
            registerMenu(false);
        }
    }    
   /**
     * Metodo que permite registar um contribuinte no sistema
     * @param nif Nif da contribuinte
     * @param email Email da contribuinte
     * @param nome Nome da contribuinte
     * @param morada Morada da contribuinte
     * @param pass password da contribuinte
     */
   public void registerInd(int nif, String email, String nome, String morada, String pass){
        Scanner sc = new Scanner(System.in);
        System.out.println("Numero de pessoas do agregado familiar: ");
        int nAgr = sc.nextInt();
        ArrayList<Integer> nifsAgr = new ArrayList<Integer>(nAgr);
        for(int i = 0; i<nAgr; i++){
            System.out.println("Nif nº" + (i+1));
            nifsAgr.add(sc.nextInt());
        }
        
        try{
            this.estado.regista_contribuinte(nif, email, nome, morada, pass, nAgr, nifsAgr);
            this.grava("save");
            
            
        }catch(ContribuinteJaExisteException e){
            System.out.println("O contribuinte com esse nif ja existe!");
            registerMenu(true);
        }
    } 
   /**
    * Metodo que constroi o menu para registar uma entidade
    * @param individual True se for contribuinte e false se for empresa
    */
   public void registerMenu(boolean individual){
        System.out.println("Caso queira regressar ao main menu prima 0: ");
        Scanner sc = new Scanner(System.in);
        System.out.println("Nif: ");
        int nif = sc.nextInt();
        if(nif==0){
            this.grava("save");
            
        }else{
            System.out.println("Nome: ");
            sc.nextLine();
            String nome = sc.nextLine();
            System.out.println("Email: ");
            String email = sc.nextLine();
            System.out.println("Morada: (rua,distrito)");
            String morada = sc.nextLine();
            System.out.println("Password: ");
            String password = sc.nextLine();
            if(individual){
                this.registerInd(nif,email,nome,morada,password);
            }else{
                this.registerEmp(nif,email,nome,morada,password);
            }
        }   
    }
   /**
     * Metodo que devolve o valor que inserimos no menu
     * @return Valor que inserimos no menu e que esteja entre 0 e 5 
     */
   public int getOpcao(){
        Scanner sc = new Scanner(System.in);
        int x = -1;
        while(x<0 || x>5)
         x = sc.nextInt();
        
        return x; 
    } 
   /**
    * Metodo que constroi o menu ao iniciar a main
    */
   public void mainMenu(){
        System.out.println("Bem vindo ao JavaFatura!");
        System.out.println("Opçoes:");
        System.out.println("1 - Login");
        System.out.println("2 - Registar contribuinte individual");
        System.out.println("3 - Registar contribuinte empresarial");
        System.out.println("0 - Sair");
        System.out.println("Insira a sua opçao: ");
    } 
   /**
    * Metodo que corre a app
    */ 
   public void run(){
       this.estado = carregaEstado("save");
       int sair = 1;   
       do {
            this.mainMenu();
                switch(this.getOpcao()) {
                    case 0: sair = 0;
                            break;
                    case 1: this.loginMenu();
                            break;
                    case 2: this.registerMenu(true);
                            break;
                    case 3: this.registerMenu(false);
                            break;
                }
       }while(sair!=0);
        
       this.grava("save");
   }
   
   
}
