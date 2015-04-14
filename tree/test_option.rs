struct Node {
  val: uint, 
  left: Option<Box<Node>>,
  right: Option<Box<Node>>
}

fn expand(val: uint, level: uint) -> Option<Box<Node>> {
  
  match level {
    0 => None,
    _ => Some(box Node{val: val, left: expand(val, level - 1), right: expand(val, level - 1)})
  }
}

fn sumit(node: &Option<Box<Node>>) -> uint {
  
  match node {
    &None => 0,
    &Some(box Node{val, ref left, ref right}) => val + sumit(left) + sumit(right)
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
