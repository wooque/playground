#include <iostream>
#include <memory>

using std::cout;
using std::endl;
using std::shared_ptr;
using std::make_shared;

struct Node {
  
  int value;
	shared_ptr<Node> left, right;
	
  Node(int value_, shared_ptr<Node> left_, shared_ptr<Node> right_):
    value(value_),
		left(left_),
		right(right_)
  {}
};	

int sumit(shared_ptr<Node> node) {
  
	if (nullptr == node)
		return 0;
    
	return node->value + sumit(node->left) + sumit(node->right);
}
	
shared_ptr<Node> expand(int init, int level) {
  
	if (0 == level)
		return nullptr;
    
	return make_shared<Node>(init, expand(init, level - 1), expand(init, level - 1));
}

int main() {
	
	for(int j = 1; j < 101; j++) {
		for(int i = 1; i < 21; i++) {
      
      int result = sumit(expand(25 - i, i));
			cout << i << ": " << result << endl;
    }
  }
  
	return 0;
}
