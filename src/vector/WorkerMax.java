package vector;

public class WorkerMax extends Worker{
	
	private ConcurVector v2;

	public WorkerMax(ConcurVector v, int cant, ConcurVector v2) {
		super(v,cant);
		this.v2 = v2;
	}

	@Override
	public void work(){
		for(int cant = 0; cant < this.cantToAnalize; cant++){
				int positionToAnalize = this.assignedVector.getPositionToAnalize();
				double result = Math.max(this.assignedVector.get(positionToAnalize), v2.get(positionToAnalize));
				this.assignedVector.set(positionToAnalize, result);
		}
	}
}
