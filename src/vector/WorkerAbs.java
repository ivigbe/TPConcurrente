package vector;

public class WorkerAbs extends Worker{ 

	public WorkerAbs(ConcurVector v, int cant) {
		super(v,cant);
	}

	public void work(){
		for(int cant = 0; cant < this.cantToAnalize; cant++){
			int positionToAnalize = this.assignedVector.getPositionToAnalize();
			double value = this.assignedVector.get(positionToAnalize);
			double result = Math.abs(value);
			this.assignedVector.set(positionToAnalize, result);
		}
	}
}
