package worker;

import vector.Barrier;
import vector.ConcurVector;

public class WorkerMin extends Worker{
	
	private ConcurVector v2;

	public WorkerMin(ConcurVector v, int cant, Barrier barrier, ConcurVector v2) {
		super(v,cant, barrier);
		this.v2 = v2;
	}

	@Override
	public void work(){
		for(int cant = 0; cant < this.cantToAnalize; cant++){
				int positionToAnalize = this.assignedVector.getPositionToAnalize();
				double result = Math.min(this.assignedVector.get(positionToAnalize), v2.get(positionToAnalize));
				this.assignedVector.set(positionToAnalize, result);
				this.barrier.subCant();
		}
		return;
	}
}