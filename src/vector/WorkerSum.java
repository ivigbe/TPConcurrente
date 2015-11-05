package vector;

import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
public class WorkerSum extends RecursiveTask<Double>{//extends Worker{

	ConcurVector v;
	int start;
	int end;
	
	public WorkerSum(ConcurVector v) {
		this(v, 0, v.dimension());
	}
	
	public WorkerSum(ConcurVector v, int start, int end){
		
		this.v = v;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Double compute() {
		int length = this.end - this.start;
		double sum = 0;
		if(length < this.v.maxThreads()){
			
			for(int i = 0; i < this.end; i++){
				sum = this.v.get(this.v.getPositionToAnalize()) + this.v.get(this.v.getPositionToAnalize());
			}
			return sum;
		}
		else{
			int split = length / 2;
			WorkerSum left = new WorkerSum(this.v, this.start, this.start + split);
			left.fork();
			WorkerSum right = new WorkerSum(this.v, this.start + split, this.end);
			sum = right.compute() + left.join();
			return sum;
		}
	}

	/*
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
	*/
}