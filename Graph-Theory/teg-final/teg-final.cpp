#include <iostream>
#include <string.h>
#include <stdlib.h>
#include <fstream>
#include <sstream>
#include <list>
#include <queue>
using namespace std;
#define INFINITO 10000000


class Grafo
{
private:
	int V; // número de vértices

  // ponteiro para um array contendo as listas de adjacências
	list<pair<int, int> > * adj;

  void printCaminhos(int , int , /*int,*/ bool [], int [], int &); 

public:

	Grafo(int V) // construtor
	{
		this->V = V; // atribui o número de vértices

    //cria as listas onde cada lista é uma lista de pairs onde cada pair é formado pelo vértice destino e o custo
		adj = new list<pair<int, int> >[V]; 
	}

  // adiciona uma aresta ao grafo de v1 à v2
	void addAresta(int v1, int v2, int custo) 
	{
		adj[v1].push_back(make_pair(v2, custo));
	}
  
  void caminhos_possiveis(int v1, int v2);
  int dijkstra(int orig, int dest);
  void horarios(int tempo, int chegada);
  
};



void Grafo::caminhos_possiveis(int v1, int v2)
{
  // Marca todos os vértices como não visitados
  bool *visitado = new bool[V];

  // Cria um vetor para guardar os caminhos
  int *caminho = new int[V];
  int indice_caminho = 0; // Inicializa path[] como vazio
  int custo = 0;

  // Inicializa todos os vértices como não visitados
  for (int i = 0; i < V; i++)
     visitado[i] = false;

    // Chama a função auxiliar recursiva para imprimir todos os caminhos
    printCaminhos(v1, v2, /*custo,*/ visitado, caminho, indice_caminho);
}


// Função recursiva para imprimir todos os caminhos de v1 a v2
// visitado[] mantém o rastreio dos vértices no caminho analisado
// caminho[] guarda os vértices atuais e indice_caminho é o índice atual em caminho[]
void Grafo::printCaminhos(int v1, int v2, /*int custo,*/ bool visitado[], int caminho[], int &indice_caminho)
{
  // Marca o nó atual e guarda no vetor caminho[]
  visitado[v1] = true;
  caminho[indice_caminho] = v1;
  indice_caminho++;
  // Se o vértice atual é igual o vértice de destino, então imprime o caminho[] atual
  if (v1 == v2)
  {
    for (int i = 0; i<indice_caminho; i++){
      cout << caminho[i] << " ";
    }
    cout << endl;
    //cout << "Distância: " << custo <<endl;    
  }
  else // Se o vértice atual não é o de destino
  {
    // Repetir para todos os vértices adjacentes ao vértice atual
    list< pair<int, int> >::iterator i;
    for (i = adj[v1].begin(); i != adj[v1].end(); ++i){
      if (!visitado[i->first]){
        //custo = custo + i->second;
        printCaminhos(i->first, v2, /*custo,*/ visitado, caminho, indice_caminho);
      }
    }
  }

  // Remove o vértice atual de caminho[] e o marca como não visitado
  indice_caminho--;
  visitado[v1] = false;
}


// algoritmo de Dijkstra
int Grafo::dijkstra(int orig, int dest)
{
	int dist[V]; // vetor de distâncias
	int visitados[V]; //vetor de visitados serve para caso o vértice já tenha sido expandido (visitado), não expandir mais
		
  // fila de prioridades de pair (distancia, vértice)
	priority_queue < pair<int, int>, vector<pair<int, int> >, greater<pair<int, int> > > pq;

	// inicia o vetor de distâncias e visitados
	for(int i = 0; i < V; i++)
	{
		dist[i] = INFINITO;
		visitados[i] = false;
	}

	dist[orig] = 0; // a distância de orig para orig é 0

	// insere na fila
	pq.push(make_pair(dist[orig], orig));

	// loop do algoritmo
	while(!pq.empty())
	{
		pair<int, int> p = pq.top(); // extrai o pair do topo
		int u = p.second; // obtém o vértice do pair
		pq.pop(); // remove da fila

		// verifica se o vértice não foi expandido
		if(visitados[u] == false)
		{
			visitados[u] = true; // marca como visitado

			list<pair<int, int> >::iterator it;

			// percorre os vértices "v" adjacentes de "u"
			for(it = adj[u].begin(); it != adj[u].end(); it++)
			{
				// obtém o vértice adjacente e o custo da aresta
				int v = it->first;
				int custo_aresta = it->second;

				// relaxamento (u, v)
				if(dist[v] > (dist[u] + custo_aresta))
				{
					// atualiza a distância de "v" e insere na fila
					dist[v] = dist[u] + custo_aresta;
					pq.push(make_pair(dist[v], v));
				}
			}
		}
	}

	return dist[dest]; // retorna a distância mínima até o destino
}



//Gera a tabela de horários a partir de horário de chegada e uma duração de trajeto
void Grafo::horarios(int tempo, int chegada){
  int partida = 0;
  partida = chegada - tempo;

  if(partida < 0){
    partida = 24 + partida;
    if (partida < 0){
      partida = 24 + partida;
      cout << "Sexta - " << partida << "horas" << endl;
    }
    else{
      cout << "Sabado - " << partida << "horas" << endl;
    }  
  }
  else{
    cout << "Domingo - " << partida << "horas" << endl;
  }

}



//Conta linhas do arquivo para conhecer a quantidade de vértices totais
int conta_linhas(){
  int vertices = 0;
  string linha;

  ifstream arquivo;
  arquivo.open ("entrada.txt");

  if(arquivo.is_open()){
    while(getline(arquivo,linha)){
      vertices++;
    }
  }
  else{
    cout << "Erro ao abrir o arquivo!" << endl;
  }

  arquivo.close();
  return vertices;
}


//Lê as informações do arquivo para então passá-las para clase Grafo
void inicializa(Grafo g)
{
  int vertices=0, origem=0, cont=1, destino, peso;
  char linha[30], corte[3] = "-;";
  string adj, d, p, destino_peso;
  
 
  FILE *arquivo;
  arquivo = fopen("entrada.txt", "r");

  if(arquivo != NULL){  

    while( fgets(linha,sizeof(linha),arquivo) ){

      if(origem == 2){
        goto pula;
      }

      for (char *token = strtok(linha,corte); token != NULL; token = strtok(NULL, corte)){
        if(cont == 1){
          destino = atoi(strdup(token));
          cont++;
       }else{
          if (cont == 2){
            peso = atoi(strdup(token));
            g.addAresta(origem, destino, peso);
            cont = 1;
          }
        }
      }

      pula:
      origem++;
    }
  
  } 
  else{
    cout << "Erro ao abrir o arquivo" << endl;
  }

  fclose(arquivo);
  
}



int main(int argc, char *argv[])
{
  int v = conta_linhas(); 
  Grafo g(v); 
  inicializa(g);
  cout << "Leitura do arquivo de entrada e passagem de parâmetros completas\n" << endl;

  int opcao,origem,destino,encontro,chegada,distancia;

  /*
  list<pair<int, int> >::iterator it;
  for(int i=0; i<9; i++){
    cout << "Cidade " << i << endl;
    for(it = g.adj[i].begin(); it != g.adj[i].end(); it++){
      cout << it->first << "-" << it->second << " ";
    }
    cout << endl;
  }
  */


  while(opcao != 3){
    cout << "Escolha uma opção:" << endl;
    cout << "1 - Possíveis rotas de uma cidade para outra" << endl;
    cout << "2 - Tabela de horários de saída de todas as cidades" << endl;
    cout << "3 - Sair" << endl;
    cin >> opcao;

    if(opcao == 1){

      cout << "Informe a cidade incial" << endl;
      cin >> origem;
      cout << "Informe a cidade final" << endl;
      cin >> destino;
      g.caminhos_possiveis(origem,destino); 

    }

    if(opcao == 2){

      cout << "Informe a cidade do encontro" << endl;
      cin >> encontro;
      cout << "Informe o horario de chegada" << endl;
      cin >> chegada;
      cout << "INFORMAÇÃO: A DURACAO DO PERCURSO É DADO PELA DIVISÃO DA DISTANCIA PELA VELOCIDADE MEDIA, velocidade media = 10km/h " << endl;
      for(int i=0; i<v; i++){
        cout << "Cidade " << i << endl;
        distancia = g.dijkstra(i, encontro);
        if(distancia == 10000000){
          cout << "Não existe rota para a cidade de encontro" << endl;
          continue;
        }
        g.horarios(distancia/10, chegada);
      }  

    }

    cout << endl;
  }
	
  /*Grafo g(5);

	g.addAresta(0, 1, 4);
	g.addAresta(0, 2, 2);
	g.addAresta(0, 3, 5);
	g.addAresta(1, 4, 1);
	g.addAresta(2, 1, 1);
	g.addAresta(2, 3, 2);
	g.addAresta(2, 4, 1);
	g.addAresta(3, 4, 1);

	cout << g.dijkstra(0, 4) << endl;
  */
}