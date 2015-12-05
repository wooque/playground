class Node {
    let left, right: Node?
    let value: Int

    init(value: Int, left: Node?, right: Node?) {
        self.value = value
        self.left = left
        self.right = right
    }
}

func sumit(node: Node?) -> Int {
    if node == nil {
        return 0
    }
    return node!.value + sumit(node!.left) + sumit(node!.right)
}

func expand(value: Int, _ lvl: Int) -> Node? {
    if lvl == 0 {
        return nil
    }
    let new_level = lvl - 1
    let left = expand(value, new_level)
    let right = expand(value, new_level)
    return Node(value: value, left: left, right: right)
}

func main() {
    for _ in 1...100 {
        for i in 1...20 {
            let tree = expand(25 - i, i)
            let s = sumit(tree)
            print("\(i): \(s)")
        }
    }    
}

main()
