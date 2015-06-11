package main;

import (
    "fmt"
)

type Node struct {
    val int
    left, right *Node
}

func expand(val int, level int) *Node {
    if level == 0 {
        return nil
    }
    return &Node{val, expand(val, level - 1), expand(val, level - 1)}
}

func sumit(node *Node) int {
    if node == nil {
        return 0
    }
    return node.val + sumit(node.left) + sumit(node.right)
}

func main() {
    for i := 1; i < 101; i++ {
        for j := 1; j < 21; j++ {
            result := sumit(expand(25 - j, j))
            fmt.Printf("%d: %d\n", j, result)
        }
    }
}
