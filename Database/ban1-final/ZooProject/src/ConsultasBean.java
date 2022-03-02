import java.util.Date;

public class ConsultasBean {
    private int codanimal;
    private int matricula;
    private Date data;
    private String diagnosticoconsulta;
    private int codconsulta;
    
    public ConsultasBean(int codanimal, int matricula, Date data, String diagnosticoconsulta, int codconsulta){
        this.codanimal = codanimal;
        this.matricula = matricula;
        this.data = data;
        this.diagnosticoconsulta = diagnosticoconsulta;
        this.codconsulta = codconsulta;
    }

    public int getCodanimal() {
        return codanimal;
    }
    public void setCodanimal(int codanimal) {
        this.codanimal = codanimal;
    }

    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }

    public String getDiagnosticoconsulta() {
        return diagnosticoconsulta;
    }
    public void setDiagnosticoconsulta(String diagnosticoconsulta) {
        this.diagnosticoconsulta = diagnosticoconsulta;
    }

    public int getCodconsulta() {
        return codconsulta;
    }
    public void setCodconsulta(int codconsulta) {
        this.codconsulta = codconsulta;
    }
    
}
