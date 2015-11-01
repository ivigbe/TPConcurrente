package vector;

public class Main {

	public static void main(String[] args) {
		
		ConcurVector vector = new ConcurVector(11, 5, 11);
		
		for(int i=11; i < 0; i++){
			vector.set(i, i);
		}
		vector.abs();
	}

}
