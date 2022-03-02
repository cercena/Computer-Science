#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "avl-tree.c"
#include "b-tree.c"

int main() {
    int i, j, *sets[100];

    // 100 conjuntos para inserção nas árvores
    srand(time(0));
    for (i = 0; i < 100; i++) {
        sets[i] = malloc((i + 1) * sizeof(int));
        for (j = 0; j <= i; j++) {
            sets[i][j] = rand();
        }
    }

    // Processamento das árvores para o pior caso, valores decrescentes
    TreeAVL treeAVL_1;
    startTreeAVL(&treeAVL_1);
    TreeB* treeB_1 = startTreeB(2);

    FILE *fp;
    char* filename = "worst-case.csv";
    fp = fopen(filename, "w+");

    fprintf(fp, "AVL, B");
    for (i = 99; i >= 0; i--) {
        fprintf(fp, "\n%d, %d", addNode(&treeAVL_1, i), addKey(treeB_1, i));
    }
    fclose(fp);

    // Reinicialização das árvores, agora para o caso moderados com valores aleatórios
    cleanSubtree(&treeAVL_1, treeAVL_1.root);
    TreeAVL treeAVL_2[100];
    TreeB* treeB_2[100];
    for (i = 0; i < 100; i++) {
        startTreeAVL(&treeAVL_2[i]);
        treeB_2[i] = startTreeB(2);
    }

    filename = "medium-case.csv";
    fp = fopen(filename, "w+");

    fprintf(fp, "AVL, B");
    for (i = 0; i < 100; i++) {
        for (j = 0; j < i; j++) {
            addNode(&treeAVL_2[i], sets[i][j]);
            addKey(treeB_2[i], sets[i][j]);
        }
        fprintf(fp, "\n%d, %d", addNode(&treeAVL_2[i], sets[i][j]), addKey(treeB_2[i], sets[i][j]));
    }
    fclose(fp);

    // Liberação da memória
    for (i = 0; i < 100; i++) {
        cleanSubtree(&treeAVL_2[i], treeAVL_2[i].root);
        free(sets[i]);
    }

    return 1;
}