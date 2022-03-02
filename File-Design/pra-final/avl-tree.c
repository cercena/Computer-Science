
#include <stdio.h>
#include <stdlib.h>
#define REPEATED_KEY -1

typedef struct nodeAVL {
    struct nodeAVL *parent;
    struct nodeAVL *left;
    struct nodeAVL *right;
    int value;
} NodeAVL;

typedef struct treeAVL {
    struct nodeAVL *root;
} TreeAVL;

void startTreeAVL(TreeAVL* tree);
int emptyTree(TreeAVL* tree);
int addNode(TreeAVL* tree, int value);
void removeNode(TreeAVL* tree, NodeAVL* node);
void runInOrder(NodeAVL* node, void (*callback) (int));
void runPreOrder(NodeAVL* node, void (*callback) (int));
void runPosOrder(NodeAVL* node, void (*callback) (int));
void cleanSubtree(TreeAVL* arvore, NodeAVL* node);

int operationsAVL;

int height(NodeAVL* node);
NodeAVL* balance(NodeAVL* node);

void startTreeAVL(TreeAVL *tree) {
    tree->root = NULL;
}

int emptyTree(TreeAVL *tree) {
    return tree->root == NULL;
}

void removeNode(TreeAVL *tree, NodeAVL *node) {
    if (node->left) {
        removeNode(tree, node->left); 
    }
    if (node->right) {
        removeNode(tree, node->right);
    }
    if (!node->parent) {
        tree->root = NULL;
    } else {
        if (node->parent->left == node) {
            node->parent->left = NULL;
        } else {
            node->parent->right = NULL;
        }
    }
    free(node);
}

void runInOrder(NodeAVL* node, void (*callback) (int)) {
    if (node) {
        runInOrder(node->left, callback);
        callback(node->value);
        runInOrder(node->right, callback);
    }
}

void runPreOrder(NodeAVL* node, void (*callback) (int)) {
    if (node) {
        callback(node->value);
        runPreOrder(node->left, callback);
        runPreOrder(node->right, callback);
    }
}

void runPosOrder(NodeAVL* node, void (*callback) (int)) {
    if (node) {
        runPosOrder(node->left, callback);
        runPosOrder(node->right, callback);
        callback(node->value);
    }
}

int max(int x, int y) { 
    operationsAVL++;
    return (x > y) ? x : y;
} 

int height(NodeAVL* node) {
    operationsAVL += 2;
    if (!node || (!node->left && !node->right))
        return 0;
    operationsAVL++;
    return 1 + max(height(node->left), height(node->right));
}

int fb(NodeAVL* node) {
    operationsAVL += 2;
    int leftHeight = 0, rightHeight = 0;
    operationsAVL++;
    if (node->left) {
        operationsAVL++;
        leftHeight = height(node->left) + 1;
    }
    operationsAVL++;
    if (node->right) {
        operationsAVL++;
        rightHeight = height(node->right) + 1;
    }
    operationsAVL++;
    return leftHeight - rightHeight;
}

NodeAVL* rse(NodeAVL* satellite) {
    operationsAVL += 3;
    NodeAVL *parent = satellite->parent, *pivot = satellite->right, *barb = pivot->left;
    operationsAVL++;
    satellite->parent = pivot;
    operationsAVL++;
    pivot->left = satellite;
    operationsAVL++;
    if (!barb) {
        operationsAVL++;
        satellite->right = NULL;
    } else {
        operationsAVL++;
        barb->parent = satellite;
        operationsAVL++;
        satellite->right = barb;
    }
    operationsAVL++;
    pivot->parent = parent;
    operationsAVL += 2;
    if (parent && parent->left == satellite) {
        operationsAVL++;
        parent->left = pivot;
    } else if (parent) {
        operationsAVL += 2;
        parent->right = pivot;
    }
    return pivot;
}

NodeAVL* rsd(NodeAVL* satellite) {
    operationsAVL += 3;
    NodeAVL *parent = satellite->parent, *pivot = satellite->left, *barb = pivot->right;
    operationsAVL++;
    satellite->parent = pivot;
    operationsAVL++;
    pivot->right = satellite;
    operationsAVL++;
    if (!barb) {
        operationsAVL++;
        satellite->left = NULL;
    } else {
        operationsAVL++;
        barb->parent = satellite;
        operationsAVL++;
        satellite->left = barb;
    }
    operationsAVL++;
    pivot->parent = parent;
    operationsAVL += 2;
    if (parent && parent->left == satellite) {
        operationsAVL++;
        parent->left = pivot;
    } else if (parent) {
        operationsAVL += 2;
        parent->right = pivot;
    }
    return pivot;
}

NodeAVL* rde(NodeAVL* satellite) {
    operationsAVL++;
    satellite->right = rsd(satellite->right);
    return rse(satellite);
}

NodeAVL* rdd(NodeAVL* satellite) {
    operationsAVL++;
    satellite->left = rse(satellite->left);
    return rsd(satellite);
}

NodeAVL* balance(NodeAVL* node) {
    operationsAVL++;
    while (node) {
        operationsAVL++;
        int factor = fb(node);
        operationsAVL++;
        if (factor > 1) {
            operationsAVL++;
            int childFactor = fb(node->left);
            operationsAVL++;
            if (childFactor > 0) {
                return rsd(node);
            } else {
                return rdd(node);
            }
        } else if (factor < -1) {
            operationsAVL += 2;
            int childFactor = fb(node->right);
            operationsAVL++;
            if (childFactor < 0) {
                return rse(node);
            } else {
                return rde(node);
            }
        }
        operationsAVL++;
        node = node->parent;
    }
    return node;
}

int addNode(TreeAVL *tree, int value) {
    operationsAVL = 0;
    operationsAVL += 3;
    NodeAVL* node = malloc(sizeof(NodeAVL));
    operationsAVL++;
    if (!node)
        return 0;
    operationsAVL++;
    node->value = value;
    operationsAVL++;
    node->left = NULL;
    operationsAVL++;
    node->right = NULL;
    operationsAVL++;
    if (emptyTree(tree)) {
        operationsAVL++;
        node->parent = NULL;
        operationsAVL++;
        tree->root = node;
        return operationsAVL;
    } else {
        operationsAVL += 2;
        NodeAVL *i = tree->root, *pivot = NULL;
        do {
            operationsAVL++;
            if (i->value == value) {
                free(node);
                return REPEATED_KEY;
            } else if (value < i->value) {
                operationsAVL += 2;
                if (!i->left) {
                    operationsAVL++;
                    node->parent = i;
                    operationsAVL++;
                    i->left = node;
                    operationsAVL++;
                    pivot = balance(i->parent);
                    operationsAVL++;
                    if (pivot && !pivot->parent) {
                        operationsAVL++;
                        tree->root = pivot;
                    }
                    return operationsAVL;
                } else {
                    operationsAVL++;
                    i = i->left;
                }
            } else {
                operationsAVL++;
                if (!i->right) {
                    operationsAVL++;
                    node->parent = i;
                    operationsAVL++;
                    i->right = node;
                    operationsAVL++;
                    pivot = balance(i->parent);
                    operationsAVL++;
                    if (pivot && !pivot->parent) {
                        operationsAVL++;
                        tree->root = pivot;
                    }
                    return operationsAVL;
                } else {
                    operationsAVL++;
                    i = i->right;
                }
            }
        operationsAVL++;
        } while (1);
    }        
}

void cleanSubtree(TreeAVL *tree, NodeAVL *node) {
    if (node) {
        cleanSubtree(tree, node->left);
        cleanSubtree(tree, node->right);
        removeNode(tree, node);
    }
}