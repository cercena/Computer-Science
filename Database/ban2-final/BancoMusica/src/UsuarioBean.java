/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matheus
 */
public class UsuarioBean {
    private int id;
    private String username;
    private String senha;
    private String nome;
    private int idade;
    private char sexo;
    private String local;
    private String descricao;
    private String email;
    private char papel;

    public UsuarioBean(int id, String username, String senha, String nome, int idade, char sexo, String local, String descricao, String email, char papel) {
        this.id = id;
        this.username = username;
        this.senha = senha;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.local = local;
        this.descricao = descricao;
        this.email = email;
        this.papel = papel;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public char getSexo() {
        return sexo;
    }
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public char getPapel() {
        return papel;
    }
    public void setPapel(char papel) {
        this.papel = papel;
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("id: "+id+" username: "+username+" senha: "+senha+
                " nome: "+nome+" idade: "+idade+" sexo: "+sexo+
                " loca: "+local+" descricao: "+descricao+" email: "+email+" papel: "+papel);
        /*
        if(ambulatorio!= null)
            sb.append(" andar: "+ambulatorio.getAndar()+" capacidade: "+ambulatorio.getCapacidade());*/
        return sb.toString();
    }
}
