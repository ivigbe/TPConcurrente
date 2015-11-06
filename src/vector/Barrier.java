package vector;

public class Barrier {

	public int cantElemsToAnalize;
	
	public Barrier(int cant) {
		this.cantElemsToAnalize = cant;
	}

	public synchronized void finished(){
		while(cantElemsToAnalize != 0){
			try {
				this.wait();
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		this.notifyAll();
	}
	
	public void subCant(){
		this.cantElemsToAnalize--;
	}
}