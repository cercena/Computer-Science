public class EspeciesBean {
    private int codespecie;
    private String nomeespecie;
    private int expectativaespecie;
    
    public EspeciesBean(int codespecie, String nomeespecie, int expectativaespecie){
        this.codespecie = codespecie;
        this.nomeespecie = nomeespecie;
        this.expectativaespecie = expectativaespecie;
    }
    
    public String toString(){
        return "codespecie: "+codespecie+" nomeespecie: "+nomeespecie+" expectativaespecie: "+expectativaespecie;
    }

    public int getCodespecie() {
        return codespecie;
    }
    public void setCodespecie(int codespecie) {
        this.codespecie = codespecie;
    }

    public String getNomeespecie() {
        return nomeespecie;
    }
    public void setNomeespecie(String nomeespecie) {
        this.nomeespecie = nomeespecie;
    }

    public int getExpectativaespecie() {
        return expectativaespecie;
    }
    public void setExpectativaespecie(int expectativaespecie) {
        this.expectativaespecie = expectativaespecie;
    }
   
}
