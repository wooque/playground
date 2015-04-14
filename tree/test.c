#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct Node Node;
struct Node
{
	Node *left, *right;
	int value;
};

void clear(Node *node)
{
    if(NULL == node)
        return;
    if(NULL != node->left)
    {
        clear(node->left);
        free(node->left);
        node->left = NULL;
    }
	if(NULL != node->right)
    {
        clear(node->right);
        free(node->right);
        node->right = NULL;
    }
}

int sumit(Node *node)
{
	if(NULL == node)
		return 0;
	return node->value + sumit(node->left) + sumit(node->right);
}

Node* expand(int init, int level)
{
	if(0 == level)
		return NULL;
    Node *new = (Node *)malloc(sizeof(Node));
    new->value = init;
    new->left = expand(init, level - 1);
    new->right = expand(init, level - 1);
	return new;
}

int main(int argc, char **argv)
{
	clock_t start = clock();
	
	for(int j = 1; j < 101; j++)
		for(int i = 1; i < 21; i++)
		{
			Node *tree = expand(25 - i, i);
			printf("%d: %d\n", i, sumit(tree));
            clear(tree);
		}
		
	clock_t end = clock();
	printf("Time: %f s", ((double)(end-start))/1000000); 
	return 0;
}

