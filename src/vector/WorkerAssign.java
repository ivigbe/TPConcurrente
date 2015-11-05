package vector;

public class WorkerAssign extends Worker{
	
	private ConcurVector v2;

	public WorkerAssign(ConcurVector v, int cant, ConcurVector v2) {
		super(v,cant);
		this.v2 = v2;
	}

	public void work(){
		for(int cant = 0; cant < this.cantToAnalize; cant++){
			int positionToAnalize = this.assignedVector.getPositionToAnalize();
			this.assignedVector.set(positionToAnalize, this.v2.get(positionToAnalize));
		}
	}

}