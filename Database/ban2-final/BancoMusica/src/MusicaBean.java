/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matheus
 */
public class MusicaBean {
    private int id;
    private String nome;
    private float duracao;
    private int idalbum;

    public MusicaBean(int id, String nome, float duracao, int idalbum) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
        this.idalbum = idalbum;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getDuracao() {
        return duracao;
    }
    public void setDuracao(float duracao) {
        this.duracao = duracao;
    }

    public int getIdalbum() {
        return idalbum;
    }
    public void setIdalbum(int idalbum) {
        this.idalbum = idalbum;
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("id: "+id+" nome: "+nome+" duracao: "+duracao+" tipo: "+idalbum);
        /*
        if(ambulatorio!= null)
            sb.append(" andar: "+ambulatorio.getAndar()+" capacidade: "+ambulatorio.getCapacidade());*/
        return sb.toString();
    }
}
