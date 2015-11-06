package worker;

import vector.Barrier;
import vector.ConcurVector;

public abstract class Worker extends Thread {
	
	public ConcurVector assignedVector;
	public int cantToAnalize;
	public Barrier barrier;

	public Worker(ConcurVector v, int cant, Barrier barrier) {
		this.assignedVector = v;
		this.cantToAnalize = cant;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		this.work();
	}

	public abstract void work();
}