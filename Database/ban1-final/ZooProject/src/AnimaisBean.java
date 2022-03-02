import java.util.Date;

public class AnimaisBean {
   private String nomeanimal;
   private int codespecie;
   private int codanimal;
   private int codanimalpai;
   private int codanimalmae;
   private Date dtnascanimal;
   private EspeciesBean especie;
   
   public AnimaisBean(String nomeanimal, int codespecie, int codanimal, int codanimalpai, int codanimalmae, Date dtnascanimal) {
       this.nomeanimal= nomeanimal;
       this.codespecie = codespecie;
       this.codanimal = codanimal;
       this.codanimalpai = codanimalpai;
       this.codanimalmae = codanimalmae;
       this.dtnascanimal = dtnascanimal;
   }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("nomeanimal: "+nomeanimal+" codespecie: "+codespecie+" codanimal: "+codanimal+
                " codanimalpai: "+codanimalpai+" codanimalmae: "+codanimalmae+" dtnscanimal: "+dtnascanimal);
        if(getEspecie()!= null)
            sb.append(" codespecie: "+getEspecie().getCodespecie()+" nomespecie: "+getEspecie().getNomeespecie()+
                    "expectativaespecie: "+getEspecie().getExpectativaespecie());
        return sb.toString();
        
    }
    
    public String getNomeanimal() {
        return nomeanimal;
    }
    public void setNomeanimal(String nomeanimal) {
        this.nomeanimal = nomeanimal;
    }

    public int getCodespecie() {
        return codespecie;
    }
    public void setCodespecie(int codespecie) {
        this.codespecie = codespecie;
    }

    public int getCodanimal() {
        return codanimal;
    }
    public void setCodanimal(int codanimal) {
        this.codanimal = codanimal;
    }

    public int getCodanimalpai() {
        return codanimalpai;
    }
    public void setCodanimalpai(int codanimalpai) {
        this.codanimalpai = codanimalpai;
    }

    public int getCodanimalmae() {
        return codanimalmae;
    }
    public void setCodanimalmae(int codanimalmae) {
        this.codanimalmae = codanimalmae;
    }

    public Date getDtnascanimal() {
        return dtnascanimal;
    }
    public void setDtnascanimal(Date dtnascanimal) {
        this.dtnascanimal = dtnascanimal;
    }

    public EspeciesBean getEspecie() {
        return especie;
    }
    public void setEspecie(EspeciesBean especie) {
        this.especie = especie;
    }
      
}