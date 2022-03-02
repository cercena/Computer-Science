package APD;
import java.util.Scanner;
import java.util.Stack;

public class APD {
    
    // Variáveis globais
    public static int estadoAtual = 0; // estado atual da análise
    public static int estadoTotal; // número total de estados
    public static String alfabeto; // símbolos do alfabeto
    public static Stack<String> pilha; // pilha característica
    public static Estado[] estados; // vetor que armazena os estados e suas informações
    public static Scanner leitor = new Scanner(System.in); // scanner para input
    public static boolean aceitaString = false; // torna-se verdadeiro se a string submetida for aceita pelo automato
    public static boolean existeTransicao = false; // torna-se verdadeiro se existir transição de um estado para outro
    public static String vazio = "E"; // transições vazias/epslon representadas com a letra 'E'
   
    // Input da tupla do autômato
    public static void main(String[] args) {
        System.out.println("SIMULADOR DE AUTÔMATO COM PILHA DETERMINÍSTICO");
        
	pilha = new Stack<String>();
	pilha.push("$"); // símbolo inicial da pilha

        // Captura do número de estados para a criação do vetor de estados
	System.out.print("Informe o número de estados totais: ");
	estadoTotal = leitor.nextInt();
	estados = new Estado[estadoTotal];
	for (int i = 0; i < estadoTotal; i++) // estanciação dos estados
            estados[i] = new Estado();
        
        // Muda o tipo dos estados informados para estado final
	System.out.print("Informe os estados finais (separando por espaços!), digite -1 para encerrar: ");
	boolean completoEstadoFinal = false;
	do {
            int estadoFinal = leitor.nextInt();
            if (estadoFinal == -1)
		completoEstadoFinal = true;
            else
		estados[estadoFinal].mudaTipoEstado(true);
        } while (!completoEstadoFinal);

	// Criação do alfabeto
	System.out.print("Informe os símbolos do alfabeto do autômato (sem separar por espaços!): ");
	alfabeto = leitor.next();

	// Criação das transições
	System.out.println("Informe as transições função programa (separando seus elementos por espaço!): ");
        System.out.println("[Aperte Enter para adicionar a próxima transição, digite -1 para encerrar]");
	boolean completoTransicoes = false;
	do {
            int estadoFonte = leitor.nextInt();
            if (estadoFonte == -1)
		completoTransicoes = true;
            else
		estados[estadoFonte].addTransicao(leitor.next(), leitor.next(), leitor.nextInt(), leitor.next());
	} while (!completoTransicoes);

        System.out.println(" ");
	// Checagem de aceitação de strings - feita por outras funções
	System.out.println("Entre com uma string de maneira sequencial por símbolos, e então digite -1 para encerrar: ");
	boolean continuaTesteString = true;
	while (continuaTesteString) {
            estadoAtual = 0;
            testaString();
            System.out.println("Continuar testando strings? (s/n)");
            if (!leitor.next().toLowerCase().equals("s"))
		continuaTesteString = false;
            pilha.clear();
            pilha.push("$");
            aceitaString = false;
            System.out.println(" ");
        }

	leitor.close();
        System.out.println(" ");
	System.out.println("Programa finalizado");
        
    }
    
    // Testagem de fitas
    public static void testaString() {
        
	boolean completoInput = false;
	do {
            System.out.print("Estado atual: " + estadoAtual + " - Pilha: ");
            // Imprime o que está gravado na pilha, em ordem
            for (int i = pilha.size()-1; i >= 0; i--)
		System.out.print(pilha.elementAt(i));
            
            System.out.print(", Digite um único símbolo: ");

            String inputString = leitor.next();
            if (!inputString.equals("-1")) { // se equivale a -1, termina-se o input
                if (alfabeto.contains(inputString) || inputString.equals(vazio))
                    completoInput = userInput(inputString);
                else {
                    completoInput = true;
                    aceitaString = false;
                }
            }else{ // se o input equivale a -1
                completoInput = true;
            }
	} while (!completoInput);

        // Checa se a string é aceita e se o input do usuário acabou por causa de uma transição existente, e não uma inexistene
	if (estados[estadoAtual].isEstadoFinal() && existeTransicao) {
            aceitaString = true;
	} else
            aceitaString = false;

	if (!aceitaString)
            System.out.println("String não é aceita");
        else
            System.out.println("String é aceita");
        
    }
    
    // Função auxiliar para a testagem mediante um único símbolo da string
    public static  boolean userInput(String inputString) {
	int valorTransicao = achaTransicao(inputString);
	if (valorTransicao >= 0) { // uma transição existente é encontrada, então não retorna valor negativo
            desempilhaPilha(valorTransicao);
            empilhaPilha(valorTransicao);
            estadoAtual = estados[estadoAtual].getTransicoes().get(valorTransicao).getDestino(); // muda o estado atual para a próximo estado
            
            existeTransicao = true;
            return false;
	}else { // não existe transição para o símbolo, então não há necessidade de seguir o teste e a string já é rejeitada
            aceitaString = false;
            existeTransicao = false;
            return true;
	}
    }
    
    // Procura de transições dado um símbolo referente ao estado analisado
    public static int achaTransicao(String inputString) {
	// Percorre as transições do estado atual e checa se existe uma transição para o símbolo fornecido
	for (int i = 0; i < estados[estadoAtual].getTransicoes().size(); i++) {
            // Se encontrar um símbolo que seja o mesmo do inputString
            if (estados[estadoAtual].getTransicoes().get(i).getInput().equals(inputString)) {
            // Checa se a pilha possui o que é preciso
                for (int j = 0; j < estados[estadoAtual].getTransicoes().get(i).getDesempilha().length(); j++) {
                    if (Character.toString(estados[estadoAtual].getTransicoes().get(i).getDesempilha().charAt(j))
                                                                     .equals(pilha.elementAt(pilha.size()-1- j))) {
                        return i;
		    }
                }
            }
	}
	return -1; // se chegar aqui, não existe transição
    }
    
    // Desempilhamento na pilha conforme a referência da transição
    public static void desempilhaPilha(int valorTransicao) {
        for (int j = 0; j < estados[estadoAtual].getTransicoes().get(valorTransicao).getDesempilha().length(); j++) {
            if (Character.toString(estados[estadoAtual].getTransicoes().get(valorTransicao).getDesempilha().charAt(j))
                                                                                                .equals(pilha.peek())){
		pilha.pop();
            }
         }
    }
    
    // Empilhamento na pilha conforme a referência da transição
    public static void empilhaPilha(int valorTransicao) {
	if (!estados[estadoAtual].getTransicoes().get(valorTransicao).getEmpilha().equals("E")){ // se não equivale a vazio, adiciona-se alguma coisa	
            for (int j = estados[estadoAtual].getTransicoes().get(valorTransicao).getEmpilha().length()-1; j >= 0; j--) {
		pilha.push(Character.toString(estados[estadoAtual].getTransicoes().get(valorTransicao).getEmpilha().charAt(j)));
            }
	}
    }
            
}
