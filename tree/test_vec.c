#include <stdlib.h>
#include <stdio.h>

typedef struct {
    int value, left, right;
} Node;

typedef struct {
    Node *data;
    int cap;
} Tree;

Tree *create_tree(int size) {
    Tree *tree = (Tree*) malloc(sizeof(Tree));
    tree->data = (Node*) malloc(size*sizeof(Node));
    tree->cap = 0;
    return tree;
}

int expand(Tree *tree, int init, int level) {
    if (tree == NULL || level <= 0)
        return -1;
    int left = expand(tree, init, level-1);
    int right = expand(tree, init, level-1);
    tree->data[tree->cap].value = init;
    tree->data[tree->cap].left = left;
    tree->data[tree->cap].right = right;
    tree->cap++;
    return tree->cap - 1;
}

int sum(Node *data, int node_index) {
    if (data == NULL || node_index == -1)
        return 0;
    Node node = data[node_index];
    return node.value + sum(data, node.left) + sum(data, node.right);
}

void clear(Tree *tree) {
    tree->cap = 0;
}

void delete_tree(Tree *tree) {
    free(tree->data);
    free(tree);
}

int main(int argc, char **argv) {
	Tree *tree = create_tree(1<<20);	
    for(int j = 1; j < 101; j++) {
        for(int i = 1; i < 21; i++) {
			int ind = expand(tree, 25-i, i);
            printf("%d: %d\n", i, sum(tree->data, ind));
			clear(tree);
        }
    }
    delete_tree(tree);
    return 0;
}
