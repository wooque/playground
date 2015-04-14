class Tree(var value: Int, var left: Tree, var right: Tree)

object Main {

def expand(init: Int, level: Int): Tree = {
	if(0 == level)
		return null
	new Tree(init, expand(init, level - 1), expand(init, level - 1))
}

def sumit(node: Tree): Int = {
	if(null == node)
		return 0
	node.value + sumit(node.left) + sumit(node.right)
}

def main(args: Array[String]) {
	val start = System.currentTimeMillis
	for(j <- 1 to 100; i <- 1 to 20) {
		println(i + ": " + sumit(expand(25 - i, i)))
	}
	val end = System.currentTimeMillis
	println("Time: " + (end - start) + " ms")
}
}
