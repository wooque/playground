#include <iostream>

using std::cout;
using std::endl;

class Node
{
public:
	Node *left_, *right_;
	int value_;
	
	Node(int value, Node* left, Node* right):
		value_(value),
		left_(left),
		right_(right) {}
	
	~Node()
	{
		delete left_;
		left_ = nullptr;
		delete right_;
		right_ = nullptr;
	}
};

int sumit(Node *node)
{
	if(nullptr == node)
		return 0;
	return node->value_ + sumit(node->left_) + sumit(node->right_);;
}

Node* expand(int init, int level)
{
	if(0 == level)
		return nullptr;
	return new Node(init, expand(init, level - 1), expand(init, level - 1));
}

int main(int argc, char **argv)
{
	for(int j = 1; j < 101; j++)
    {
		for(int i = 1; i < 21; i++)
		{
			auto tree = expand(25 - i, i);
			cout << i << ": " << sumit(tree) << endl;
			delete tree;
		}
    }
	return 0;
}

