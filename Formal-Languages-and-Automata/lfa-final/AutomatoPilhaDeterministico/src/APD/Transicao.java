package APD;

public class Transicao {
    String input, desempilha, empilha;
    int destino;

    Transicao() {

    }

    Transicao(String input, String desempilha, int destino, String empilha) {
	this.input = input;
	this.desempilha = desempilha;
	this.destino = destino;
	this.empilha = empilha;
    }

    public String getInput() {
	return input;
    }

    public String getDesempilha() {
	return desempilha;
    }

    public String getEmpilha() {
	return empilha;
    }

    public int getDestino() {
	return destino;
    }
    
}
