package vector;

public class WorkerAssignMask extends Worker{
	
	private ConcurVector v2;
	private ConcurVector mask;

	public WorkerAssignMask(ConcurVector v, int cant, ConcurVector v2, ConcurVector mask) {
		super(v,cant);
		this.v2 = v2;
		this.mask = mask;
	}

	@Override
	public void work(){
		for(int cant = 0; cant < this.cantToAnalize; cant++){
			int positionToAnalize = this.assignedVector.getPositionToAnalize();
			if (this.mask.get(positionToAnalize) >= 0)
				this.assignedVector.set(positionToAnalize, v2.get(positionToAnalize));
		}
	}

}
