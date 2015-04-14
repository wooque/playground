package main;

import "fmt"

type Node struct {
    val, left, right int
}

type Tree struct {
    nodes []Node
	free int
}

func expand(tree *Tree, val int, level int) int {
	if tree == nil || level <= 0 {
		return -1
	}
    tree.nodes[tree.free] = Node{val,
                                 expand(tree, val, level - 1),
                                 expand(tree, val, level - 1)}
    tree.free++
    return tree.free-1
}

func sumit(nodes *[]Node, node_index int) int {
	if nodes == nil || node_index == -1 {
		return 0
	}
	node := (*nodes)[node_index]
	return node.val + sumit(nodes, node.left) + sumit(nodes, node.right)
}

func clear(tree *Tree) {
    if tree == nil {
        return
    }
    tree.free = 0
}

func main() {
    tree := Tree{nodes: make([]Node, 3*(1 << uint(20))), free: 0}
	for i := 1; i < 101; i++ {
		for j := 1; j < 21; j++ {
            ind := expand(&tree, 25 - j, j)
			fmt.Printf("%d: %d\n", j, sumit(&tree.nodes, ind))
            clear(&tree)
		}
	}
}
