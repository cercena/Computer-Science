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
public class AlbumBean {
    private int id;
    private String nome;
    private Date datalanc;
    private float notamedia;
    private int rank;
    private int idartista;

    public AlbumBean(int id, String nome, Date datalanc, float notamedia, int rank, int idartista) {
        this.id = id;
        this.nome = nome;
        this.datalanc = datalanc;
        this.notamedia = notamedia;
        this.rank = rank;
        this.idartista = idartista;
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

    public Date getDatalanc() {
        return datalanc;
    }
    public void setDatalanc(Date datalanc) {
        this.datalanc = datalanc;
    }

    public float getNotamedia() {
        return notamedia;
    }
    public void setNotamedia(float notamedia) {
        this.notamedia = notamedia;
    }

    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getIdartista() {
        return idartista;
    }
    public void setIdartista(int idartista) {
        this.idartista = idartista;
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("id: "+id+" nome: "+nome+" datalanc: "+datalanc+
                " notamedia: "+notamedia+" rank: "+rank+" idartista: "+idartista);
        /*
        if(ambulatorio!= null)
            sb.append(" andar: "+ambulatorio.getAndar()+" capacidade: "+ambulatorio.getCapacidade());*/
        return sb.toString();
    }
}
