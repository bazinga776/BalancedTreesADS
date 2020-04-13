public class MinHeapImpl {
	private Bldg[] Heap; //each node in the heap has buildingnum,executedTime,totalTime
	private int size;  //to update size of heap
	private int maxsize;

	public static final int FRONT = 1;

	//Returns the heap’s size
	public int getSize() {
		return size; //returing size
	}
	
	//constructor 
	public MinHeapImpl(int maxsize) {
		this.maxsize = maxsize;
		this.size = 0;
		Heap = new Bldg[this.maxsize + 1];
		Heap[0] = new Bldg(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	//This method returns the position of the left child 
	//in the node present at pos
	private int leftChildPosition(int pos) {
		return (2 * pos);
	}

	//This method returns the position of the right child in the node present at pos
	private int rightChildPosition(int pos) {
		return (2 * pos) + 1;
	}

	//If the node is a leaf node, returns true.
	private boolean isLeaf(int pos) {
		if (2 * pos > size && 2 * pos + 1 > size && pos <= size) {
			return true;
		}
		return false;
	}

	//to swap two nodes of the heap this method is used 
	private void swap(int fpos, int spos) {
		Bldg tmp;
		tmp = Heap[fpos];
		Heap[fpos] = Heap[spos];
		Heap[spos] = tmp;
	}

	/*MinHeap property is maintained when the root node is being constructed, the rest of the nodes are arranged in the heap accordingly*/
	public void minHeapify(int pos) {

		// If the node is a non-leaf node and greater
		// than any of its child
		if (!isLeaf(pos)) {
			//compare the node with its children
			if (Heap[leftChildPosition(pos)]!=null && (Heap[pos].getExecTime() > Heap[leftChildPosition(pos)].getExecTime())
					|| (Heap[rightChildPosition(pos)]!=null && (Heap[pos].getExecTime() > Heap[rightChildPosition(pos)].getExecTime()))) {

				// Swap with the left child and heapify
				// the left child
				if(Heap[leftChildPosition(pos)]==null) {
					swap(pos, rightChildPosition(pos));
					minHeapify(rightChildPosition(pos));
				}else if (Heap[rightChildPosition(pos)]==null) {
					swap(pos, leftChildPosition(pos));
					minHeapify(leftChildPosition(pos));
				}else {
					if (Heap[leftChildPosition(pos)].getExecTime() < Heap[rightChildPosition(pos)].getExecTime()) {
						swap(pos, leftChildPosition(pos));
						minHeapify(leftChildPosition(pos));
					}
					// Swap with the right child and heapify
					// the right child
					else if (Heap[leftChildPosition(pos)].getExecTime() > Heap[rightChildPosition(pos)].getExecTime()) {
						swap(pos, rightChildPosition(pos));
						minHeapify(rightChildPosition(pos));
					}else {
						int minPos = Math.min(Heap[leftChildPosition(pos)].getBldgID(), Heap[rightChildPosition(pos)].getBldgID());
						if(Heap[leftChildPosition(pos)].getBldgID() == minPos) {
							swap(pos, leftChildPosition(pos));
							minHeapify(leftChildPosition(pos));
						}else {
							swap(pos, rightChildPosition(pos));
							minHeapify(rightChildPosition(pos));
						}
					}
				}
			} else {
				/*if executiontimes of two buidings are equal when comparing leftchild and node position, and buildingNum of left child is less, then swap them*/
				if (Heap[leftChildPosition(pos)]!=null && Heap[pos].getExecTime() == Heap[leftChildPosition(pos)].getExecTime()) {
					if (Heap[pos].getBldgID() > Heap[leftChildPosition(pos)].getBldgID()) {
						swap(pos, leftChildPosition(pos));
						minHeapify(leftChildPosition(pos));
					}
				}
				/*if executiontimes of two buidings are equal when comparing right child and node position, and buildingNum of right child is less, then swap them*/
				if (Heap[rightChildPosition(pos)]!=null && Heap[pos].getExecTime() == Heap[rightChildPosition(pos)].getExecTime()) {
					if (Heap[pos].getBldgID() > Heap[rightChildPosition(pos)].getBldgID()) {
						swap(pos, rightChildPosition(pos));
						minHeapify(rightChildPosition(pos));
					}
				}
			}
		}
	}

	//a node is inserted into the heap using this method 
	public void insert(Bldg element) {
		if (size >= maxsize) {
			return;
		}
		Heap[++size] = element;
		//int current = size;
	}

	// Function to print the contents of the heap
	public void print() {
		for (int i = 1; i <= size / 2; i++) {
			System.out.print(
					" PARENT : " + Heap[i] + " LEFT CHILD : " + Heap[2 * i] + " RIGHT CHILD :" + Heap[2 * i + 1]);
			System.out.println();
		}
	}

	// Function to build the min heap using
	// the minHeapify
	public void minHeaps() {
		for (int pos = (size / 2); pos >= 1; pos--) {
			minHeapify(pos);
		}
	}

	// Method to return the root element of minheap
	public Bldg remove() {
		Bldg popped = Heap[FRONT];
		Heap[FRONT] = Heap[size];
		Heap[size--] = null;
		minHeaps();
		return popped;
	}

	//Method to return the root element of minheap
	public Bldg getMin() {
		return Heap[FRONT];
	}
}

