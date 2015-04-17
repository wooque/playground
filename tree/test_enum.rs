enum Tree {
  Leaf,
  Node(u32, Box<Tree>, Box<Tree>)
}

fn expand(val: u32, level: u32) -> Tree {
  
  match level {
    0 => Tree::Leaf,
    _ => Tree::Node(val, 
                    Box::new(expand(val, level - 1)), 
                    Box::new(expand(val, level - 1)))
  }
}

fn sumit(node: &Tree) -> u32 {
  
  match node {
    &Tree::Leaf => 0,
    &Tree::Node(val, ref left, ref right) => 
        val + sumit(left) + sumit(right)
  }
}

fn main() {
  
  for _ in 1..101 {
    for i in 1..21 {
      
      let result = sumit(&expand(25 - i, i));
      println!("{}: {}", i, result);
    }
  }
}
