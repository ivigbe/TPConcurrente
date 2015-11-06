package worker;

import vector.Barrier;
import vector.ConcurVector;

public class WorkerAbs extends Worker{ 

	public WorkerAbs(ConcurVector v, int cant, Barrier barrier) {
		super(v,cant, barrier);
	}

	public void work(){
		for(int cant = 0; cant < this.cantToAnalize; cant++){
			int positionToAnalize = this.assignedVector.getPositionToAnalize();
			double value = this.assignedVector.get(positionToAnalize);
			double result = Math.abs(value);
			this.assignedVector.set(positionToAnalize, result);
			this.barrier.subCant();
		}
		return;
	}
}