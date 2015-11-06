package worker;

import vector.Barrier;
import vector.ConcurVector;

public class WorkerSum extends Worker{
	
	public int positionToSave;
	public ConcurVector vectorResultado;
	
	public WorkerSum(ConcurVector v, int cant, Barrier barrier, int i, ConcurVector vectorResultado) {
		super(v,cant, barrier);
		this.positionToSave = i;
		this.vectorResultado = vectorResultado;
	}

	@Override
	public void work(){
		for(int cant = 0; cant < this.cantToAnalize; cant++){
			int positionToAnalize1 = this.assignedVector.getPositionToAnalize();
			int positionToAnalize2 = this.assignedVector.getPositionToAnalize();
			if(positionToAnalize2 == this.assignedVector.dimension()){
				this.vectorResultado.set(this.positionToSave, this.assignedVector.get(positionToAnalize1));
			}else{
				double result = this.assignedVector.get(positionToAnalize1) + this.assignedVector.get(positionToAnalize2);
				this.vectorResultado.set(this.positionToSave, result);
			}				
		}
		barrier.finished();
		return;
	}
}