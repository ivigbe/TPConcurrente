package vector;

public class WorkerSum extends Worker{

	public WorkerSum(ConcurVector v, int cant) {
		super(v,cant);
	}

	@Override
	public void work(){
		for(int cant = 0; cant < this.cantToAnalize; cant++){
			int positionToAnalize1 = this.assignedVector.getPositionToAnalize();
			int positionToAnalize2 = this.assignedVector.getPositionToAnalize();
			if(positionToAnalize2 == this.assignedVector.dimension()){
				this.assignedVector.listaSum.add(this.assignedVector.get(positionToAnalize1));
			}else{
				double result = this.assignedVector.get(positionToAnalize1) + this.assignedVector.get(positionToAnalize2);
				this.assignedVector.listaSum.add(result);
			}				
		}
	}
}