package vector;

public abstract class Worker extends Thread {
	
	public ConcurVector assignedVector;
	public int elementToAnalize;
	public int cantToAnalize;

	public Worker(ConcurVector v, int cant) {
		this.assignedVector = v;
		this.elementToAnalize = v.getElementToAnalize();
		this.cantToAnalize = cant;
	}

	@Override
	public void run() {
		this.work();
	}

	public abstract void work();
}
