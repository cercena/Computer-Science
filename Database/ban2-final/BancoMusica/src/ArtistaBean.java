/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date;

/**
 *
 * @author Matheus
 */
public class ArtistaBean {
    private int id;
    private String nome;
    private String bio;
    private char tipo;
    private Date dataorigem;
    private String localorigem;
    
    public ArtistaBean(int id, String nome, String bio, char tipo, Date dataorigem, String localorigem) {
        this.id = id;
        this.nome = nome;
        this.bio = bio;
        this.tipo = tipo;
        this.dataorigem = dataorigem;
        this.localorigem = localorigem;
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

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public char getTipo() {
        return tipo;
    }
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public Date getDataorigem() {
        return dataorigem;
    }
    public void setDataorigem(Date dataorigem) {
        this.dataorigem = dataorigem;
    }

    public String getLocalorigem() {
        return localorigem;
    }
    public void setLocalorigem(String localorigem) {
        this.localorigem = localorigem;
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("id: "+id+" nome: "+nome+" bio: "+bio+
                " tipo: "+tipo+" dataorigem: "+dataorigem+" localorigem: "+localorigem);
        /*
        if(ambulatorio!= null)
            sb.append(" andar: "+ambulatorio.getAndar()+" capacidade: "+ambulatorio.getCapacidade());*/
        return sb.toString();
    } 
}

