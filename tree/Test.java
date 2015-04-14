final class Test {
	  
	int value;
	Test left, right;
	
	Test(int value, Test left, Test right) {
		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	static Test expand(int init, int level) {

		if (0 >= level)
			return null;
		
		return new Test(init, expand(init, level - 1), expand(init, level - 1));
	}
	
	static int sumit(Test node) {
  
		if (null == node)
			return 0;
		
		return node.value + sumit(node.left) + sumit(node.right);
	}
	
	public static void main(String[] args) {
    
		long start = System.currentTimeMillis();
		for(int j = 1; j < 101; j++) {
			for(int i = 1; i < 21; i++) {
		  
				int result = sumit(expand(25 - i, i));
				System.out.println("" + i + ": " + result);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end-start)/1000.0 + " s");
	}
}



