#include <stdio.h> 
#include <stdlib.h> 
#include <string.h>  

struct Matriz { 
int linha; 
int coluna; 
int **m; 
}; 

int main(int argc, char *argv[]) { 
    struct Matriz Matriz1,Matriz2,Matriz3; 
    int n,i,j,k,lin1,col1,lin2,col2; 

    ///----------- LENDO ARQUIVOS 1 E 2 --------- 
    FILE *arq1; 
    arq1 = fopen ("arq1.txt", "r"); 
    char c,string[50]; 
    int linha=1,coluna=0,valor; 
    int linhajalida = 0; 
    int iniciopalavra=1,fimpalavra=0,fimordem=0; 

    while(1){ 
        if(arq1 == NULL) 
            printf ("Arquivo 1 nulo!\n"); 

        c = fgetc(arq1); 

        if (feof(arq1)) { 
        	break; 
        } 

        if(iniciopalavra==1){//primeira letra 
            i=0; 
            string[i] = c; 
            i++; 
            iniciopalavra=0; 
            fimpalavra=0; 
        } 
        else if (c!='\n' && c!=' '){//segunda à ultima letra 
            string[i]=c; 
            i++; 
            iniciopalavra=0; 
        } 
        else{//c=='\n'||c==' ' // fim palavra 
            string[i]='\0'; 
            iniciopalavra=1; 
            fimpalavra=1; 
        } 

        if(fimpalavra==1 && linha>=2){//leitura matriz 
            valor = atoi(string); 
            Matriz1.m[linha-2][coluna] = valor; 
            coluna++; 
        } 

        if(fimpalavra==1 && linha==1 && linhajalida==0){//linha matriz 
            linhajalida=1; 
            lin1 = atoi(string); 
        } 

        if(linha==1 && linhajalida==1){//coluna da matriz e alocando 
            col1 = atoi(string); 
            Matriz1.linha = lin1; 
            Matriz1.coluna = col1; 

            Matriz1.m=malloc(lin1*sizeof(int)); 
            for(j=0;j<lin1;j++){ 
                Matriz1.m[j]=malloc(col1*sizeof(int)); 
            } 
        } 

        if(c=='\n'){//cada pulo de linha 
            linha++; 
            coluna=0; 
        } 

        if(linha>lin1+1){ 
            break; 
        } 

    } 

    fclose(arq1); 
    printf ("\nMatriz1:\nlinhas: %i; colunas: %i; \n",Matriz1.linha,Matriz1.coluna); 

    for(i=0;i<lin1;i++){ 
        for(j=0;j<col1;j++){ 
            printf ("%i ", Matriz1.m[i][j]); 
        } 
        printf ("\n"); 
    } 

    ///LENDO ARQUIVO 2 
    FILE *arq2 = fopen ("arq2.txt", "r"); 
    linha=1; 
    coluna=0; 
    linhajalida = 0; 
    iniciopalavra=1; 
    fimpalavra=0; 
    fimordem=0; 

    while(1){ 
        if(arq2 == NULL) 
            printf ("Arquivo 2 nulo!\n"); 

        if (feof(arq2)) { 
            break; 
        } 

        c = fgetc(arq2); 

        if(iniciopalavra==1){//primeira letra 
            i=0; 
            string[i] = c; 
            i++; 
            iniciopalavra=0; 
            fimpalavra=0; 
        } 
        else if (c!='\n' && c!=' '){//segunda à ultima letra 
            string[i]=c; 
            i++; 
            iniciopalavra=0; 
        } 
        else{//c=='\n'||c==' ' // fim palavra 
            string[i]='\0'; 
            iniciopalavra=1; 
            fimpalavra=1; 
        } 

        if(fimpalavra==1 && linha>=2){//leitura matriz 
            valor = atoi(string); 
            //matriz2[linha-2][coluna] = valor; 
            Matriz2.m[linha-2][coluna]=valor; 
            coluna++; 
        } 

        if(fimpalavra==1 && linha==1 && linhajalida==0){//linha matriz 
            linhajalida=1; 
            lin2 = atoi(string); 
        } 

        if(linha==1 && linhajalida==1){//coluna da matriz e alocando 
            col2 = atoi(string); 
            Matriz2.linha = lin2; 
            Matriz2.coluna = col2; 
            Matriz2.m=malloc(lin2*sizeof(int)); 
            for(j=0;j<lin2;j++){ 
                Matriz2.m[j]=malloc(col2*sizeof(int)); 
            } 
        } 

        if(c=='\n'){//cada pulo de linha 
            linha++; 
            coluna=0; 
        } 

    } 

    fclose(arq2); 
    printf ("\nMatriz2:\nlinhas: %i; colunas: %i;\n",Matriz2.linha,Matriz2.coluna); 

    for(i=0;i<lin2;i++){ 
        for(j=0;j<col2;j++){ 
            printf ("%i ",Matriz2.m[i][j]); 
        } 
        printf ("\n"); 
    } 
    /// ---------------------- FIM LEITURA DE ARQUIVOS ------- 

    /// ---------------------------- INICIO MENU ----------------- 
    printf("\n\nSelecione as seguintes operacoes:\n"); 
    printf(" 1 - Transposicao \n"); 
    printf(" 2 - Soma \n"); 
    printf(" 3 - Subtracao \n"); 
    printf(" 4 - Multiplicacao \n"); 
    printf(" 5 - Multiplicacao por escalar \n"); 
    printf(" 6 - Verificar se sao iguais \n"); 
    printf(" 7 - Verficar se eh simetrica \n"); 
    printf ("\nOpcao digitada: "); 
    scanf("%i",&n); 

    if(n==1){///TRANSPOSICAO 
        Matriz3.linha = Matriz1.coluna; 
        Matriz3.coluna = Matriz1.linha;

        Matriz3.m=malloc(Matriz3.linha*sizeof(int)); 
        for(j=0;j<Matriz3.linha;j++){ 
            Matriz3.m[j]=malloc(Matriz3.coluna*sizeof(int)); 
        } 

        printf ("\n\nPrintando a matriz resultante e a salvando:\n\n"); 

        for (i = 0; i < Matriz3.linha; i++) { 
            for (j = 0; j < Matriz3.coluna; j++) { 
                Matriz3.m[i][j] = Matriz1.m[j][i]; 
                printf ("%i ",Matriz3.m[i][j]); 
            } 
            printf ("\n"); 
        } 
            printf ("\n"); 
    } 

    if(n==2){ /// SOMA 
        if(Matriz1.linha==Matriz2.linha&&Matriz1.coluna==Matriz2.coluna){ 
            Matriz3.linha=Matriz1.linha; 
            Matriz3.coluna=Matriz1.coluna; 

            Matriz3.m=malloc(Matriz3.linha*sizeof(int)); 
            for(j=0;j<Matriz3.linha;j++){ 
                Matriz3.m[j]=malloc(Matriz3.coluna*sizeof(int)); 
            } 

            printf ("\n\nPrintando a matriz resultante e a salvando:\n\n"); 

            for (i=0; i<Matriz3.linha; i++){ 
                for (j=0; j<Matriz3.linha; j++){ 
                    Matriz3.m[i][j] = Matriz1.m[i][j] + Matriz2.m[i][j]; 
                    printf ("%i ",Matriz3.m[i][j]); 
                } 
                printf ("\n"); 
            } 
            printf ("\n"); 
        } 
        else{ 
            printf("Soma impossivel, numeros de linhas e colunas sao diferentes!\n"); 
            return 0; 
        } 
    } 

    if(n==3){ /// SUBTRACAO 
        if(Matriz1.linha==Matriz2.linha&&Matriz1.coluna==Matriz2.coluna){ 
            Matriz3.linha=Matriz1.linha; 
            Matriz3.coluna=Matriz1.coluna; 

            Matriz3.m=malloc(Matriz3.linha*sizeof(int)); 
            for(j=0;j<Matriz3.linha;j++){ 
                Matriz3.m[j]=malloc(Matriz3.coluna*sizeof(int)); 
            } 

            printf ("\n\nPrintando a matriz resultante e a salvando:\n\n"); 

            for (i=0; i<Matriz3.linha; i++){ 
                for (j=0; j<Matriz3.linha; j++){ 
                    Matriz3.m[i][j] = Matriz1.m[i][j] - Matriz2.m[i][j]; 
                    printf ("%i ",Matriz3.m[i][j]); 
                } 
                printf ("\n"); 
            } 
            printf ("\n"); 
        } 
         else{ 
            printf("Soma impossivel, numeros de linhas e colunas sao diferentes!\n"); 
            return 0; 
        } 
    } 

    if(n==4){ ///MULTIPLICACAO 
        if(Matriz1.coluna==Matriz2.linha){ 
            Matriz3.linha = Matriz1.linha; 
            Matriz3.coluna = Matriz2.coluna; 

            Matriz3.m=malloc(Matriz3.linha*sizeof(int)); 
            for(j=0;j<Matriz3.linha;j++){ 
                Matriz3.m[j]=malloc(Matriz3.coluna*sizeof(int)); 
            } 

           for(i=0; i<Matriz3.linha; i++){ 
               for(j=0; j<Matriz3.coluna; j++){ 
                   for (k=0; k<Matriz1.coluna; k++){ 
                        Matriz3.m[i][j]+= Matriz1.m[i][k] * Matriz2.m[k][j]; 
                    } 
                    printf ("%i ",Matriz3.m[i][j]); 
                } 
                printf ("\n"); 
            } 
            printf ("\n"); 
        } 
    } 

    if(n==5){ ///MULTIPLICACAO POR ESCALAR 
        int x; 
        Matriz3.linha=Matriz1.linha; 
        Matriz3.coluna=Matriz1.coluna; 

        Matriz3.m=malloc(Matriz3.linha*sizeof(int)); 
        for(j=0;j<Matriz3.linha;j++){ 
            Matriz3.m[j]=malloc(Matriz3.coluna*sizeof(int)); 
        } 

        printf ("Qual o numero para multiplicar?\n"); 
        printf ("X: "); 
        scanf ("%i",&x); 

        printf ("\n\nPrintando a matriz resultante e a salvando:\n\n"); 

        for (i=0; i<Matriz3.linha; i++){ 
            for (j=0; j<Matriz3.linha; j++){ 
                Matriz3.m[i][j] = Matriz1.m[i][j] * x; 
                printf ("%i ",Matriz3.m[i][j]); 
            } 
            printf ("\n"); 
        } 
        printf ("\n"); 
    } 

    if(n==6){ /// SE SAO IGUAIS 
        if(Matriz1.linha==Matriz2.linha && Matriz1.coluna==Matriz2.coluna){ 
            for(i=0;i<Matriz1.linha;i++){ 
                for(j=0;j<Matriz1.coluna;j++){ 
                    if(Matriz1.m[i][j]!=Matriz2.m[i][j]){ 
                        printf ("\nNao sao iguais\n"); 
                        return 0; 
                    } 
                } 
            } 
        } 
        else 
            printf ("\nNao sao iguais\n"); 

        printf ("Eles sao iguais!\n"); 
        return 0; 
    } 

    if(n==7){ /// SE SAO SIMETRICAS 
        for(i=0;i<Matriz1.linha;i++){ 
            for(j=0;j<Matriz1.coluna;j++){ 
                if(Matriz1.m[i][j]!=Matriz1.m[j][i]){ 
                    printf ("\nNao eh simetrica\n"); 
                    return 0; 
                } 
            } 
        } 
        printf ("\nEla eh simetrica!\n"); 
        return 0; 
    } 
    ///--------------------- FIM MENU ---------- 

    /// ------------------  SALVAMENTO DO NOVO ARQUIVO ------- 
    FILE *arq3; 
    arq3 = fopen ("arq3.txt", "w"); 

    fprintf(arq3,"%d ", Matriz3.linha); 
    fprintf(arq3,"%d\n",Matriz3.coluna); 

    for(i=0;i<Matriz3.linha;i++){ 
        for(j=0;j<Matriz3.coluna;j++){ 
            fprintf (arq3,"%d ",Matriz3.m[i][j]); 
        } 
        fprintf (arq3,"\n"); 
    } 

    fclose(arq3); 
    if (arq3 != NULL) { 
        printf ("Dados gravados com sucesso!\n"); 
    } 
    /// --------------- FIM SALVAMENTO DE ARQUIVO 

    return 0; 
} 