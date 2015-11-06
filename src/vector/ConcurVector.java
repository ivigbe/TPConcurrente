package vector;

import worker.WorkerAbs;
import worker.WorkerAdd;
import worker.WorkerAssign;
import worker.WorkerAssignMask;
import worker.WorkerMax;
import worker.WorkerMin;
import worker.WorkerMul;
import worker.WorkerSub;
import worker.WorkerSum;

/** This class represents a fixed width vector of floating point numbers. */
public class ConcurVector {

	// An array with the vector elements
	private double[] elements;
	private int maxThreads;
	private int load;
	
	private int positionToAnalize;	
	int d;
	int r;

	/** Constructor for a ConcurVector.
	 * @param size, the width of the vector.
	 * @precondition size > 0. */
	public ConcurVector(int size) {
		this.elements = new double[size];
	}
	
	public ConcurVector(int size, int maxThreads, int load) {
		this.elements = new double[size];
		this.maxThreads = maxThreads;
		this.load = load;
		this.positionToAnalize = 0;
		this.d = this.dimension() / this.maxThreads;
		this.r = this.dimension() % this.maxThreads;
	}
	
	public int cantDeElementosAAnalizar(int i){
		int cantDeElemsAAnalizar = this.d;
		if(i<this.r){
			cantDeElemsAAnalizar = this.d + 1;
		}
		
		return cantDeElemsAAnalizar;
	}
	
	public int load() {
		return this.load;
	}
	
	public int maxThreads() {
		return this.maxThreads;
	}
	
	public synchronized int getPositionToAnalize() {
		int pos = this.positionToAnalize;
		this.positionToAnalize++;
		return pos;
	}
	
	/** Returns the dimension of this vector, that is, its width. */
	public int dimension() {
		return elements.length;
	}
	
	/** Returns the element at position i.
	 * @param i, the position of the element to be returned.
	 * @precondition 0 <= i < dimension(). */
	public synchronized double get(int i) {
		return elements[i];
	}
	
	/** Assigns the value d to the position i of this vector. 
	 * @param i, the position to be set.
	 * @param d, the value to assign at i.
	 * @precondition 0 <= i < dimension. */
	public synchronized void set(int i, double d) {
		elements[i] = d;
	}
	
	/** Assigns the value d to every position of this vector. 
	 * @param d, the value to assigned. */
	public synchronized void set(double d) {
		for (int i = 0; i < dimension(); ++i)
			elements[i] = d;
	}
	
	/** Copies the values from another vector into this vector.
	 * @param v, a vector from which values are to be copied.
	 * @precondition dimension() == v.dimension(). */
	public synchronized void assign(ConcurVector v) {
		Barrier b = new Barrier(this.dimension());
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerAssign(this, this.cantDeElementosAAnalizar(i), b, v).start();
	}
	
	/** Copies the values from another vector into this vector
	 * if a it has a corresponding positive mask.
	 * @param mask, a vector of conditions that indicate whether an element has to be copied or not.
	 * @param v, a vector from which values are to be copied.
	 * @precondition dimension() == mask.dimension() && dimension() == v.dimension(). */
	public synchronized void assign(ConcurVector mask, ConcurVector v) {
		Barrier b = new Barrier(this.dimension());
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerAssignMask(this, this.cantDeElementosAAnalizar(i), b, v, mask).start();
	}
	
	/** Applies the absolute value operation to every element in this vector. */
	public synchronized void abs() {
		Barrier b = new Barrier(this.dimension());
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerAbs(this, this.cantDeElementosAAnalizar(i), b).start();
	}
	
	/** Adds the elements of this vector with the values of another (element-wise).
	 * @param v, a vector from which to get the second operands.
	 * @precondition dimension() == v.dimension(). */
	public synchronized void add(ConcurVector v) {
		Barrier b = new Barrier(this.dimension());
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerAdd(this, this.cantDeElementosAAnalizar(i), b, v).start();
	}
	
	/** Subtracts from the elements of this vector the values of another (element-wise).
	 * @param v, a vector from which to get the second operands.
	 * @precondition dimension() == v.dimension(). */
	public synchronized void sub(ConcurVector v) {
		Barrier b = new Barrier(this.dimension());
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerSub(this, this.cantDeElementosAAnalizar(i), b, v).start();
	}
	
	/** Multiplies the elements of this vector by the values of another (element-wise).
	 * @param v, a vector from which to get the second operands.
	 * @precondition dimension() == v.dimension(). */
	public synchronized void mul(ConcurVector v) {
		Barrier b = new Barrier(this.dimension());
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerMul(this, this.cantDeElementosAAnalizar(i), b, v).start();
	}
	
	/** Divides the elements of this vector by the values of another (element-wise).
	 * @param v, a vector from which to get the second operands.
	 * @precondition dimension() == v.dimension(). */
	public synchronized void div(ConcurVector v) {
		Barrier b = new Barrier(this.dimension());
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerMul(this, this.cantDeElementosAAnalizar(i), b, v).start();
	}
	
	/** Returns the sum of all the elements in this vector. */
	public synchronized double sum() {
		Barrier b = new Barrier(this.dimension());
		int dim;
		ConcurVector vectorResultado = this;
		ConcurVector vectorAAnalizar = this;
		while (! (vectorResultado.dimension() == 1)){
			if(vectorAAnalizar.dimension() % 2 == 0){
				dim = vectorAAnalizar.dimension()/2;
			}else{
				dim = vectorAAnalizar.dimension()/2 + 1;
			}
			vectorResultado = new ConcurVector(dim, this.maxThreads, this.load);
			for(int i = 0; i < this.maxThreads; i++)
				new WorkerSum(vectorAAnalizar, this.cantDeElementosAAnalizar(i), b, i, vectorResultado).start();
			vectorAAnalizar = vectorResultado;
		}
		return vectorResultado.get(0);
	}
	
	/** Returns the dot product between two vectors (this and v).
	 * @param v, second operand of the dot product operation.
	 * @precondition dimension() == v.dimension(). */
	public synchronized double prod(ConcurVector v) {
		ConcurVector aux = new ConcurVector(dimension());
		aux.assign(this);
		aux.mul(v);
		return aux.sum();
	}
	
	/** Returns the norm of this vector. */
	public synchronized double norm() {
		ConcurVector aux = new ConcurVector(dimension());
		aux.assign(this);
		aux.mul(this);
		return Math.sqrt(aux.sum());
	}
	
	/** Normalizes this vector, converting it into a unit vector. */
	public synchronized void normalize() {
		ConcurVector aux = new ConcurVector(dimension());
		aux.set(this.norm());
		div(aux);
	}
	
	/** Applies the max operation element-wise.
	 * @param v, a vector with the second operands for max.
	 * @precondition dimension == v.dimension(). */
	public synchronized void max(ConcurVector v) {
		Barrier b = new Barrier(this.dimension());
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerMax(this, this.cantDeElementosAAnalizar(i), b, v).start();
	}
	
	/** Applies the min operation element-wise.
	 * @param v, a vector with the second operands for min.
	 * @precondition dimension == v.dimension(). */
	public synchronized void min(ConcurVector v) {
		Barrier b = new Barrier(this.dimension());
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerMin(this, this.cantDeElementosAAnalizar(i), b, v).start();
	}
}