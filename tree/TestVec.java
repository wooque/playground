import java.util.ArrayList;

final class TestVec {
	  
	int[] data;
	int free;
	
	TestVec(int size) {
		this.data = new int[3*size];
		this.free = 0;
	}
	
	int expand(int init, int level) {

		if (level <= 0)
			return -1;
		
		int left_t = expand(init, level-1);
		int right_t = expand(init, level-1);
		data[free++] = init;
		data[free++] = left_t;
		data[free++] = right_t;
		return free-3;
	}
	
	int sumit(int i) {
  
		if (i == -1)
			return 0;
		
		return data[i] + sumit(data[i+1]) + sumit(data[i+2]);
	}
	
	void clear() {
		free = 0;
	}
	
	public static void main(String[] args) {
    
		TestVec tree = new TestVec(1<<20);
		long start = System.currentTimeMillis();
		for(int j = 1; j < 101; j++) {
			for(int i = 1; i < 21; i++) {
				
				int result = tree.sumit(tree.expand(25 - i, i));
				System.out.println("" + i + ": " + result);
				tree.clear();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end-start)/1000.0 + " s");
	}
}



