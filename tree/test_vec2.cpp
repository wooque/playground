#include <memory>
#include <iostream>

using std::unique_ptr;
using std::cout;
using std::endl;

class Tree {
public:

	struct Node {
		int value, left, right;
	};

	static const int TREE_LEAF = -1;
	
	Node *tree;
    int cap;

    Tree(int size) {
        tree = new Node[size];
        cap = 0;
    }
	
	int expand(int init, int level) {
		if (level <= 0)
			return TREE_LEAF;
        int left = expand(init, level-1);
        int right = expand(init, level-1);
		tree[cap++] = {init, left, right};
        return cap - 1;
	}

	int sum(int node_index) {
		if (node_index == TREE_LEAF)
			return 0;
		Node node = tree[node_index];
		return node.value + sum(node.left) + sum(node.right);
	}
	
	void clear() {
		cap = 0;
	}
};

int main() {
	auto tree = unique_ptr<Tree>(new Tree(1<<20));	
    for(int j = 1; j < 101; j++) {
        for(int i = 1; i < 21; i++) {
			cout << i << ": " << tree->sum(tree->expand(25-i, i)) << endl;
			tree->clear();
        }
    }
    return 0;
}
