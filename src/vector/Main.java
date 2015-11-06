package vector;

public class Main {

	public static void main(String[] args) {
		
		ConcurVector vector = new ConcurVector(10, 5, 0);		
		ConcurVector vector2 = new ConcurVector(10, 5, 0);
		
		//vector = (-1,-2,-3,-4,-5,-6,-7,-8,-9,-10)
		for(int i=0; i < 10; i++){
			vector.set(i, (-i-1));
		}
		
		//vector = (1,2,3,4,5,6,7,8,9,10)
		for(int i=0; i < 10; i++)
			vector2.set(i, (i+1));
		
		//vector.assign(vector2);
		vector.abs();
		
		for(int i=0; i < 10; i++){
			System.out.println(vector.get(i));
		}
	}
}