package APD;
import java.util.ArrayList;

public class Estado {
    ArrayList<Transicao> transicoes = new ArrayList<Transicao>();
    boolean estadoFinal = false;

    Estado() {

    }

    public void addTransicao(String input, String desempilha, int destino, String empilha) {
	transicoes.add(new Transicao(input, desempilha, destino, empilha));
    }

    public ArrayList<Transicao> getTransicoes() {
	return transicoes;
    }
	
    public boolean isEstadoFinal() {
	return estadoFinal;
    }
	
    public void mudaTipoEstado(boolean status) {
	estadoFinal = status;
    }    
}
