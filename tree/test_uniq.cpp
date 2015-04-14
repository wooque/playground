#include <iostream>
#include <memory>

using std::cout;
using std::endl;
using std::move;
using std::unique_ptr;

struct Node {
  
    int value;
    unique_ptr<Node> left, right;
    
    Node(int value_, unique_ptr<Node> left_, unique_ptr<Node> right_):
        value(value_),
        left(move(left_)),
        right(move(right_))
    {}
};  

unique_ptr<Node> expand(int init, int level) {
  
    if (0 == level)
        return nullptr;
    
    return unique_ptr<Node>(new Node(init, expand(init, level - 1), expand(init, level - 1)));
}

int sumit(unique_ptr<Node> node) {
  
    if (nullptr == node)
        return 0;
    
    return node->value + sumit(move(node->left)) + sumit(move(node->right));
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
