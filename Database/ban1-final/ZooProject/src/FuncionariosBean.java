import java.util.HashSet;
import java.util.Iterator;

public class FuncionariosBean {
   private int matricula;
   private String nome;
   private String endereco;    
   private String email;
   private int crmv;
   private String especialidade;

   public FuncionariosBean(int matricula, String nome, String endereco, String email, int crmv, String especialidade) {
       this.matricula = matricula;
       this.nome = nome;
       this.endereco = endereco;
       this.email = email;
       this.crmv = crmv;
       this.especialidade= especialidade;
   }
        
    public String toString(){
        return "matricula: "+matricula+" nome: "+nome+" endereco: "+endereco+" email: "+email+" crmv: "+crmv+" especialidade: "+especialidade;
        /*
        StringBuffer sb = new StringBuffer();
        sb.append("codm: "+codm+" nome: "+getNome()+" idade: "+idade+
                " especialidade: "+getEspecialidade()+" cpf: "+cpf+" cidade: "+cidade+" nroa: "+nroa);
        if(ambulatorio!= null)
            sb.append(" andar: "+ambulatorio.getAndar()+" capacidade: "+ambulatorio.getCapacidade());
        return sb.toString();
        */
    }        

    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getCrmv() {
        return crmv;
    }
    public void setCrmv(int crmv) {
        this.crmv = crmv;
    }
    
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
