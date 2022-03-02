/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matheus
 */
public class AvaliacaoBean {
    private int id;
    private String texto;
    private float nota;
    private int idusuario;
    private int idalbum;

    public AvaliacaoBean(int id, String texto, float nota, int idusuario, int idalbum) {
        this.id = id;
        this.texto = texto;
        this.nota = nota;
        this.idusuario = idusuario;
        this.idalbum = idalbum;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public float getNota() {
        return nota;
    }
    public void setNota(float nota) {
        this.nota = nota;
    }

    public int getIdusuario() {
        return idusuario;
    }
    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdalbum() {
        return idalbum;
    }
    public void setIdalbum(int idalbum) {
        this.idalbum = idalbum;
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("id: "+id+" texto: "+texto+" nota: "+nota+
                " idusuario: "+idusuario+" idalbum: "+idalbum);
        /*
        if(ambulatorio!= null)
            sb.append(" andar: "+ambulatorio.getAndar()+" capacidade: "+ambulatorio.getCapacidade());*/
        return sb.toString();
    }
}
