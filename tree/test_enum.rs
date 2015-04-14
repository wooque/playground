enum Tree {
  Leaf,
  Node(uint, Box<Tree>, Box<Tree>)
}

fn expand(val: uint, level: uint) -> Tree {
  
  match level {
    0 => Tree::Leaf,
    _ => Tree::Node(val, box expand(val, level - 1), box expand(val, level - 1))
  }
}

fn sumit(node: &Tree) -> uint {
  
  match node {
    &Tree::Leaf => 0,
    &Tree::Node(val, box ref left, box ref right) => val + sumit(left) + sumit(right)
  }
}

fn main() {
  
  for _ in range(1u8, 101u8) {
    for i in range(1, 21) {
      
      let result = sumit(&expand(25 - i, i));
      println!("{}: {}", i, result);
    }
  }
}
