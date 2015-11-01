package vector;

public class WorkerAbs extends Thread {
	
	public ConcurVector assignedVector;
	public int elementToAnalize;
	public int cantToAnalize;

	public WorkerAbs(ConcurVector v, int cant) {
		this.assignedVector = v;
		this.elementToAnalize = v.getElementToAnalize();
		this.cantToAnalize = cant;
	}

	@Override
	public void run() {
		this.work();
	}

	public void work() {
		for(int cant = 0; cant == this.cantToAnalize; cant++){
			double element = this.assignedVector.get(this.elementToAnalize);
			double result = Math.abs(element);
			this.assignedVector.set(this.elementToAnalize, result);
			this.elementToAnalize = this.assignedVector.getElementToAnalize();
		}
	}

}
