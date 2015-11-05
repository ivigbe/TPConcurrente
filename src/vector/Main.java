package vector;

public class Main {

	public static void main(String[] args) {
		
		ConcurVector vector = new ConcurVector(10, 5, 0);
		ConcurVector vector2 = new ConcurVector(11, 5, 1);
		
		for(int i=0; i < 10; i++){
			vector.set(i, -i);
		}
		
		for(int i=0; i < 11; i++)
			vector2.set(i, i);
		
		vector.abs();
		//vector.assign(vector2);
		
		for(int i=0; i < 10; i++){
			
			System.out.println(vector.get(i));
		}
	}

}
