struct Node {
  val: u32, 
  left: Option<Box<Node>>,
  right: Option<Box<Node>>
}

fn expand(val: u32, level: u32) -> Option<Box<Node>> {
  
  match level {
    0 => None,
    _ => Some(Box::new(Node{val: val,
                            left: expand(val, level - 1), 
                            right: expand(val, level - 1)}))
  }
}

fn sumit(node: &Option<Box<Node>>) -> u32 {
  
  match *node {
    None => 0,   
    Some(ref bn) => {
        let n: &Node = bn;
        let &Node{val: v, left: ref l, right: ref r} = n;
        return v + sumit(l) + sumit(r);
    }
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
