#include <stdio.h>
#include <stdlib.h>

typedef struct nodeB {
    int total;
    int* keys;
    struct nodeB** children;
    struct nodeB* parent; 
} NodeB;

typedef struct treeB {
  NodeB* root;
  int order;
} TreeB;

TreeB* startTreeB(int);
NodeB* startNode(TreeB*);
void runTree(NodeB*);
int binarySearch(NodeB*, int);
int findKey(TreeB*, int);
NodeB* findNode(TreeB*, int);
void addKeyNode (NodeB*, NodeB*, int);
int overflow(TreeB*,NodeB*);
NodeB* divideNode(TreeB*, NodeB*);
void addKeyRecursive(TreeB*, NodeB*, NodeB*, int);
int addKey(TreeB*, int);

int operationsB;

TreeB* startTreeB(int order) {
    TreeB* a = malloc(sizeof(TreeB));
    a->order = order;
    a->root = startNode(a);

    return a;
}

NodeB* startNode(TreeB* tree) {
    operationsB += 2;
    int max = tree->order * 2;
    operationsB += 3;
    NodeB* node = malloc(sizeof(NodeB));
    operationsB++;
    node->parent = NULL;
    operationsB += 4;
    node->keys = malloc(sizeof(int) * (max + 1));
    operationsB += 4;
    node->children = malloc(sizeof(NodeB) * (max + 2));
    operationsB++;
    node->total = 0;
    operationsB++;
    for (int i = 0; i < max + 2; i++) {
        operationsB += 3;
        node->children[i] = NULL;
    }
    return node;
}

void runTree(NodeB* node) {
    if (node != NULL) {
        for (int i = 0; i < node->total; i++){
            runTree(node->children[i]); 
            
            printf("%d ",node->keys[i]);
        }

        runTree(node->children[node->total]); 
    }
}

int binarySearch(NodeB* node, int key) {
    operationsB++;
    int start = 0, end = node->total - 1, half;
    operationsB += 3;
    while (start <= end) {
        operationsB += 2;
        half = (start + end) / 2;
        operationsB++;	        
        if (node->keys[half] == key) {	
		    return half; 
        } else if (node->keys[half] > key) {
            operationsB += 2;
            end	= half - 1;	
        } else {
            operationsB++;
            start = half + 1;
        }
    }
    return start; 
}

int findKey(TreeB* tree, int key) {	
    NodeB *node = tree->root;
    while (node != NULL) {
        int i = binarySearch(node, key);
        if (i < node->total && node->keys[i] == key) {
            return 1; 
        } else {
            node = node->children[i];
        }
    }
    return 0; 	
}

NodeB* findNode(TreeB* tree, int key) {	
    operationsB++;
    NodeB *node = tree->root;
    operationsB++;
    while (node != NULL) {
        operationsB++;
        int i = binarySearch(node, key);
        operationsB++;
        if (node->children[i] == NULL)
            return node; 
        else {
            operationsB++;
            node = node->children[i];
        }
    }

    return NULL; 
}

void addKeyNode(NodeB* node, NodeB* new, int key) {
    operationsB++;
    int i = binarySearch(node, key);
    operationsB++;
    for (int j = node->total - 1; j >= i; j--) {
        operationsB += 2;
        node->keys[j + 1] = node->keys[j];
        operationsB++;
        node->children[j + 2] = node->children[j + 1];
    }
    operationsB++;
    node->keys[i] = key;
    operationsB++;
    node->children[i + 1] = new;
    operationsB++;
    node->total++;
}

int overflow(TreeB* tree, NodeB* node) {

    operationsB++;

    return node->total > tree->order * 2;
}

NodeB* divideNode(TreeB* tree, NodeB* node) {
    operationsB += 2;
    int half = node->total / 2;
    operationsB++;
    NodeB* new = startNode(tree);
    operationsB++;
    new->parent = node->parent;
    operationsB++;
    for (int i = half + 1; i < node->total; i++) {
        operationsB += 3;
        new->children[new->total] = node->children[i];
        operationsB++;
        new->keys[new->total] = node->keys[i];
        operationsB += 2;
        if (new->children[new->total] != NULL) new->children[new->total]->parent = new;
        operationsB++;
        new->total++;
    }

    new->children[new->total] = node->children[node->total];
    if (new->children[new->total] != NULL) new->children[new->total]->parent = new;    
    node->total = half;
    return new;
}

void addKeyRecursive(TreeB* tree, NodeB* node, NodeB* new, int key) {
    addKeyNode(node, new, key);
    operationsB++;
    if (overflow(tree, node)) {
        operationsB++;
        int promoted = node->keys[tree->order];
        operationsB++;
        NodeB* new = divideNode(tree, node);

        operationsB++;
        if (node->parent == NULL) {
            operationsB++;           
            NodeB* parent = startNode(tree);
            operationsB++;
            parent->children[0] = node;
            addKeyNode(parent, new, promoted);
            
            operationsB++;
            node->parent = parent;
            operationsB++;
            new->parent = parent;
            operationsB++;
            tree->root = parent;
        } else
            addKeyRecursive(tree, node->parent, new, promoted);
    }
}

int addKey(TreeB* tree, int key) {
    operationsB = 0;
    operationsB++;
    NodeB* node = findNode(tree, key);
    addKeyRecursive(tree, node, NULL, key);
    return operationsB;
}