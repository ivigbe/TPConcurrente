package vector;


/** This class represents a fixed width vector of floating point numbers. */
public class ConcurVector {

	
	// An array with the vector elements
	private double[] elements;
	private int maxThreads;
	private int load;
	
	private int elementToAnalize;
	
	
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
		this.elementToAnalize = 0;
	}
	
	public int cantDeElementosAAnalizar(){
		return (this.dimension() - this.load()) / this.maxThreads();
	}
	
	public int load() {
		return this.load;
	}
	
	public int maxThreads() {
		return this.maxThreads;
	}
	
	public synchronized int getElementToAnalize() {
		int elem = this.elementToAnalize;
		this.elementToAnalize++;
		return elem;
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
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerAssign(this, this.cantDeElementosAAnalizar(), v).start();
	}
	
	
	/** Copies the values from another vector into this vector
	 * if a it has a corresponding positive mask.
	 * @param mask, a vector of conditions that indicate whether an element has to be copied or not.
	 * @param v, a vector from which values are to be copied.
	 * @precondition dimension() == mask.dimension() && dimension() == v.dimension(). */
	public synchronized void assign(ConcurVector mask, ConcurVector v) {
		for (int i = 0; i < dimension(); ++i)
			if (mask.get(i) >= 0)
				set(i, v.get(i));
	}
	
	
	/** Applies the absolute value operation to every element in this vector. */
	public synchronized void abs() {
		for(int i = 0; i < this.maxThreads; i++)
			new WorkerAbs(this, this.cantDeElementosAAnalizar()).start();
	}
	
	
	/** Adds the elements of this vector with the values of another (element-wise).
	 * @param v, a vector from which to get the second operands.
	 * @precondition dimension() == v.dimension(). */
	public void add(ConcurVector v) {
		for (int i = 0; i < dimension(); ++i)
			set(i, get(i) + v.get(i));
	}
	
	
	/** Subtracts from the elements of this vector the values of another (element-wise).
	 * @param v, a vector from which to get the second operands.
	 * @precondition dimension() == v.dimension(). */
	public void sub(ConcurVector v) {
		for (int i = 0; i < dimension(); ++i)
			set(i, get(i) - v.get(i));
	}
	
	
	/** Multiplies the elements of this vector by the values of another (element-wise).
	 * @param v, a vector from which to get the second operands.
	 * @precondition dimension() == v.dimension(). */
	public void mul(ConcurVector v) {
		for (int i = 0; i < dimension(); ++i)
			set(i, get(i) * v.get(i));
	}
	
	
	/** Divides the elements of this vector by the values of another (element-wise).
	 * @param v, a vector from which to get the second operands.
	 * @precondition dimension() == v.dimension(). */
	public void div(ConcurVector v) {
		for (int i = 0; i < dimension(); ++i)
			set(i, get(i) / v.get(i));
	}
	
	
	/** Returns the sum of all the elements in this vector. */
	public double sum() {
		double result = 0;
		for (int i = 0; i < dimension(); ++i)
			result += get(i);
		return result;
	}
	
	
	/** Returns the dot product between two vectors (this and v).
	 * @param v, second operand of the dot product operation.
	 * @precondition dimension() == v.dimension(). */
	public double prod(ConcurVector v) {
		ConcurVector aux = new ConcurVector(dimension());
		aux.assign(this);
		aux.mul(v);
		return aux.sum();
	}
	
	
	/** Returns the norm of this vector. */
	public double norm() {
		ConcurVector aux = new ConcurVector(dimension());
		aux.assign(this);
		aux.mul(this);
		return Math.sqrt(aux.sum());
	}
	
	
	/** Normalizes this vector, converting it into a unit vector. */
	public void normalize() {
		ConcurVector aux = new ConcurVector(dimension());
		aux.set(this.norm());
		div(aux);
	}
	
	
	/** Applies the max operation element-wise.
	 * @param v, a vector with the second operands for max.
	 * @precondition dimension == v.dimension(). */
	public void max(ConcurVector v) {
		for (int i = 0; i < dimension(); ++i)
			set(i, Math.max(get(i), v.get(i)));
	}
	
	
	/** Applies the min operation element-wise.
	 * @param v, a vector with the second operands for min.
	 * @precondition dimension == v.dimension(). */
	public void min(ConcurVector v) {
		for (int i = 0; i < dimension(); ++i)
			set(i, Math.min(get(i), v.get(i)));
	}
}
