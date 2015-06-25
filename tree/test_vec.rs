struct Node {
  val: i32,
  left: i32,
  right: i32,
}

fn expand(tree: &mut Vec<Node>, val: i32, level: i32) -> i32 {
	
  if level <= 0 {
	return -1;
  }

  let left = expand(tree, val, level - 1);
  let right = expand(tree, val, level - 1);
  tree.push(Node{val: val, left: left, right: right});

  return tree.len() as i32 - 1;
}

fn sum(tree: &Vec<Node>, node_index: i32) -> i32 {
  
  if node_index == -1 {
	  return 0;
  }
  
  let node = &tree[node_index as usize];
  return node.val + sum(tree, node.left) + sum(tree, node.right)
}

fn main() {
  
  let mut tree = Vec::with_capacity(1<<20);
  for _ in 1..101 {
    for i in 1..21 {
      
      let index = expand(&mut tree, 25 - i, i);
      let result = sum(&tree, index);
      println!("{}: {}", i, result);
      tree.clear();
    }
  }
}
