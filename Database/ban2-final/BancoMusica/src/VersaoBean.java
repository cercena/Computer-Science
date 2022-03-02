/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matheus
 */
public class VersaoBean {
    private int id;
    private int ano;
    private String midia;
    private String pais;
    private int idgravadora;
    private int idalbum;

    public VersaoBean(int id, int ano, String midia, String pais, int idgravadora, int idalbum) {
        this.id = id;
        this.ano = ano;
        this.midia = midia;
        this.pais = pais;
        this.idgravadora = idgravadora;
        this.idalbum = idalbum;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getMidia() {
        return midia;
    }
    public void setMidia(String midia) {
        this.midia = midia;
    }

    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getIdgravadora() {
        return idgravadora;
    }
    public void setIdgravadora(int idgravadora) {
        this.idgravadora = idgravadora;
    }

    public int getIdalbum() {
        return idalbum;
    }
    public void setIdalbum(int idalbum) {
        this.idalbum = idalbum;
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("id: "+id+" ano: "+ano+" midia: "+midia+
                " país: "+pais+" idgravadora: "+idgravadora+" idalbum: "+idalbum);
        /*
        if(ambulatorio!= null)
            sb.append(" andar: "+ambulatorio.getAndar()+" capacidade: "+ambulatorio.getCapacidade());*/
        return sb.toString();
    }
}
