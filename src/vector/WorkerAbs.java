package vector;

public class WorkerAbs extends Worker{ 

	public WorkerAbs(ConcurVector v, int cant) {
		super(v,cant);
	}

	public void work(){
		for(int cant = 0; cant < this.cantToAnalize; cant++){
			double element = this.assignedVector.get(this.elementToAnalize);
			double result = Math.abs(element);
			this.assignedVector.set(this.elementToAnalize, result);
			this.elementToAnalize = this.assignedVector.getElementToAnalize();
		}
	}
}
