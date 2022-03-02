#include <stdio.h>
#include <stdlib.h>
#include <string.h>

FILE *arq;
char aux[200];
char *texto[200];
char temp[200];
char string[50];
char c;
int cont = 0, i, j, n, col, lin;

typedef struct Palavra{
  int linha;
  int coluna;
  char dado[50];
}palavra;
  
typedef struct NoLDE {
  struct Palavra word;
  struct NoLDE *prox;
  struct NoLDE *ant;
}noLDE; 

typedef struct DescLDE { 
  struct NoLDE *inicio;
}descLDE;

descLDE * criaLista();
descLDE * leArquivo();

void insereStruct(descLDE *p,char palavra[200],int linha,int coluna);
void menu(descLDE *p);
void leLista(descLDE *p);
void buscaOcorrencias(descLDE *p, char palavra[50]);
void removePalavra(descLDE *p, char palavra[50]);
void removePalavraPos(descLDE *p, int i, int j);
void contOcorrencias(descLDE *p, char palavra[50]);
void contPalavras(descLDE *p);
void editaPalavraPos(descLDE *p, char palavra[50], int i, int j);
void contPalavrasChar(descLDE *p, char c);
void atualizaArquivo(FILE *arq, descLDE *p);


void main(int argc, char *argv[]) {  
  descLDE *p = leArquivo();
  menu(p);
}


//INTERFACE COM O USUARIO//
void menu(descLDE *p){
  printf("Escolha uma opção (1 a 9):\n");
  printf("01 - Exibir o texto original\n");
  printf("02 - Ocorrencias de uma dada palavra\n");
  printf("03 - Remover uma palavra\n");
  printf("04 - Remover uma palavra dada uma posicao\n");
  printf("05 - Numero de ocorrencias de uma palavra\n");
  printf("06 - Exibir o numero total de palavras do texto\n");
  printf("07 - Editar uma palavra dada uma posicao\n");
  printf("08 - Numero de palavras que possui um dado caractere\n");
  printf("09 - Sair e salvar o arquivo\n");

  while(n != 9) {
    printf("\n");
    scanf("%i",&n);
    switch(n){
      case 1: 
        leLista(p);
        break;
      case 2:
        printf("Informe uma palavra: "); scanf("%s",string);
        buscaOcorrencias(p, string);
        break;
      case 3:
        printf("Informe uma palavra: "); scanf("%s",string);
        removePalavra(p, string);
        atualizaArquivo(arq, p);
        break;
      case 4:
        printf("Informe uma linha: ");
        scanf("%i",&lin);
        printf("Informe uma coluna: ");
        scanf("%i",&col);
        removePalavraPos(p, lin, col);
        atualizaArquivo(arq, p);
        break;
      case 5:
        printf("Informe uma palavra:"); scanf("%s",string);
        contOcorrencias(p, string);
        break;
      case 6:
        contPalavras(p);
        break;
      case 7:
        printf("Informe uma palavra: "); scanf("%s",string);
        printf("Informe uma linha: ");
        scanf("%i",&lin);
        printf("Informe uma coluna: ");
        scanf("%i",&col);
        editaPalavraPos(p, string, lin, col);
        atualizaArquivo(arq, p);
        break;
      case 8:
        printf("Informe um caractere: "); 
        scanf(" %c", &c);
        contPalavrasChar(p, c);
        break;
      case 9:
        atualizaArquivo(arq, p);
        break;
      default:
        printf("Opcao invalida");
    }
  }
}


//FUNÇÕES AUXILIARES:
//Criação da lista
descLDE* criaLista(){  
  descLDE *desc = (descLDE*) malloc(sizeof(descLDE));
  if(desc) { 
    desc->inicio = NULL;
  }
  return desc;
}

//Leitura do arquivo
descLDE* leArquivo(){
  char *token;
  arq = fopen("arquivo.txt", "r");
  if(arq == NULL){
    printf("Nao foi possivel abrir o arquivo");
  }

  while(fgets(aux, sizeof(aux), arq) != NULL) {
    texto[cont] = strdup(aux);
    cont++;
  }

  descLDE *p = criaLista();
  j = 0;
  for (int i = 0; i < cont; ++i)
  {
    token = strtok(texto[i], " ");
    while( token != NULL ) {
      if (token[strlen(token)-1] == '\n'){
        token[strlen(token)-1] = '\0';
      }
      insereStruct(p,token,i,j);
      token = strtok(NULL, " ");    
      j++;
    }
    j = 0;
  }
  return p;
}

//Inserção do arquivo na lista
void insereStruct(descLDE *p,char palavra[200],int linha,int coluna){
  noLDE *temp = (noLDE*) malloc(sizeof(noLDE));
  noLDE *tempAnt = (noLDE*) malloc(sizeof(noLDE));
  strcpy(temp->word.dado,palavra);
  temp->word.linha = linha;
  temp->ant = NULL;
  temp->word.coluna = coluna;
  if(p->inicio == NULL) {
    p->inicio = temp;

  } else {
    noLDE *aux = p->inicio;
    while (aux->prox != NULL){
      tempAnt = aux;
      aux = aux->prox;
    }
    aux->prox = temp;
    aux->ant = tempAnt;
    temp->prox = NULL;
  }

}


//FUNÇÕES PRINCIPAIS:
//01) Leitura e exibição do texto original através da lista
void leLista(descLDE *p){
  int linha = 1;
  noLDE *aux = p->inicio;
  while (aux != NULL) {
    if(aux->word.linha == linha) {
      printf("\n");
      linha++;
    }
    printf("%s ", aux->word.dado);
    /*printf("\nLinha: %i\n", aux->word.linha);
    printf("Coluna: %i\n", aux->word.coluna);*/
    aux = aux->prox;
  }
  printf("\n");
}

//02) Busca de ocorrências de uma palavra: retorna as posições do primeiro caractere
void buscaOcorrencias(descLDE *p, char palavra[50]){
  noLDE *aux = p->inicio;
  while (aux != NULL) {
    if (strcmp(aux->word.dado,palavra) == 0) {
      printf("Linha: %i || Coluna: %i \n",aux->word.linha+1,aux->word.coluna+1);
    }
    aux = aux->prox;
  }
}

//03) Remoção de palavra
void removePalavra(descLDE *p, char palavra[50]){
  noLDE *aux = p->inicio;
  int flag = 0,flag2 = 0;;
  while (aux != NULL) {
    if (strcmp(aux->word.dado,palavra) == 0) {
      if(strcmp(aux->ant->word.dado,"") == 0) {
        p->inicio = aux->prox;
        i = aux->word.linha;
        j = aux->word.coluna;
        if(aux->word.coluna == 0 && aux->prox->word.linha != aux->word.linha){
          flag2 = 1;
        }
        free(aux);
        flag = 1;
      } else {
        i = aux->word.linha;
        j = aux->word.coluna;
        if(aux->word.coluna == 0 && aux->prox->word.linha != aux->word.linha){
          flag2 = 1;
        }
        aux->prox->ant = aux->ant;
        aux->ant->prox = aux->prox;
        free(aux);
        flag = 1;
      }
    }
    if (flag == 1 && aux->word.linha == i) {
      aux->word.coluna--;
    }
    if (flag2 == 1 && aux->word.linha != i) {
      aux->word.linha--;
    }
    aux = aux->prox;
  }
  if (flag == 0){
    printf("Não existe a palavra no arquivo\n");
  }
}

//04) Remoção de palavra na posição especificada
void removePalavraPos(descLDE *p, int i, int j){
  noLDE *aux = p->inicio;
  int flag = 0,flag2 = 0;
  while (aux != NULL) {
    if (aux->word.linha == i-1 && aux->word.coluna == j-1 && flag == 0) {
      if(strcmp(aux->ant->word.dado,"") == 0) {
        p->inicio = aux->prox;
        if(aux->word.coluna == 0 && aux->prox->word.linha != aux->word.linha){
          flag2 = 1;
        }
        free(aux);
        flag = 1;
      } else if(flag == 0) {
        if(aux->word.coluna == 0 && aux->prox->word.linha != aux->word.linha){
          flag2 = 1;
        }
        aux->prox->ant = aux->ant;
        aux->ant->prox = aux->prox;
        free(aux);
        flag = 1;
      }
    }
    if (flag == 1 && aux->word.linha == i-1) {
      aux->word.coluna--;
    }
    if (flag2 == 1 && aux->word.linha != i-1) {
      aux->word.linha--;
    }
    aux = aux->prox;
  }
  if (flag == 0){
    printf("Não existe a posição especificada na lista\n");
  }

}

//05) Exibição do número total de ocorrências de uma palavra
void contOcorrencias(descLDE *p, char palavra[50]){
  int ocorrencia = 0;
  noLDE *aux = p->inicio;
  while (aux != NULL) {
    if (strcmp(aux->word.dado,palavra) == 0) {
      ocorrencia++;
    }
    aux = aux->prox;
  }
  printf("Total de Ocorrencias: %i\n",ocorrencia);
}

//06) Exibição do número total de palavras no texto
void contPalavras(descLDE *p){
  int numPalavras = 0;
  noLDE *aux = p->inicio;
  while (aux != NULL) {
    numPalavras++;
    aux = aux->prox;
  }
  printf("Total de Palavras: %i\n", numPalavras);
}

//07) Editar palavra dada uma posição
void editaPalavraPos(descLDE *p, char palavra[50], int i, int j){
  int ocorrencia = 0;
  noLDE *aux = p->inicio;
  while (aux != NULL) {
    if (aux->word.linha == i-1 && aux->word.coluna == j-1) {
      printf("Palavra Anterior: %s\n",aux->word.dado);
      strcpy(aux->word.dado,palavra);
      printf("Palavra Editada: %s\n",aux->word.dado);
    }
    aux = aux->prox;
  }
}

//08) Exibição do número de palavras (e suas posições) que possuem um dado caractere
void contPalavrasChar(descLDE *p, char c){
  int ocorrencia = 0;
  noLDE *aux = p->inicio;
  while (aux != NULL) {
    for (int i = 0; i < strlen(aux->word.dado); ++i)
    {
      if (aux->word.dado[i] == c) {
        printf("Linha: %i || Coluna: %i \n",aux->word.linha+1,aux->word.coluna+1);
        ocorrencia++;
      }
    }
    aux = aux->prox;
  }
  printf("Total de Ocorrencias: %i\n",ocorrencia);
}

//09) Atualização do arquivo: cada palavra da multilista é gravada em um arquivo temporário, e renomear o original como OLD e o temporário como original
void atualizaArquivo(FILE *arq, descLDE *p){
  noLDE *aux = p->inicio;
  char string[50] = "";
  int linha = 1;
  arq = fopen("arquivo.txt", "w");
  while (aux != NULL) {
    strcpy(string,aux->word.dado);
    if(aux->prox != NULL){
      if(linha == aux->prox->word.linha){
        strcat(string,"\n");
        linha++;
      } else {
        strcat(string," ");
      }
      fputs(string, arq);
    } else {
      strcpy(string,aux->word.dado);
      fputs(string, arq);
    }
    aux = aux->prox;
  }
  fclose(arq);
}