sealed abstract class Tree
case object Empty extends Tree
case class Node(value: Int, left: Tree, right: Tree) extends Tree

object MainFunc {

def expand(init: Int, level: Int): Tree = level match {
	case 0 => Empty
	case _ => Node(init, expand(init, level - 1), expand(init, level - 1))
}

def sumit(node: Tree): Int = node match {
	case Empty => 0
	case Node(value, left, right) => value + sumit(left) + sumit(right)
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
