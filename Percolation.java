import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    	private boolean [][] array;
	private WeightedQuickUnionUF Qf;
	int size;
	int top = 0;  //Source
	int bottom;
   	int count = 0;
	 
    // Creates an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
	size = N;
	bottom = size * size + 1; //virtual sink beyond Nth row. N*N+1 element in unionfind structure
	array = new boolean[size][size];
	Qf = new WeightedQuickUnionUF(N*N+2);  //+2 for top & bottom	 
    }

    // Opens site (i, j) if it is not open already.
    public void open(int i, int j) {
	if(i >= 0 && i < size && j >= 0 && j < size){
	array[i][j] = true;
	count++;

	if(i == 0)				//Top row
		Qf.union(encode(i,j),top);
	if(i == size-1)				//Last row
		Qf.union(encode(i,j),bottom);
	if(j > 0 && isOpen(i,j-1))		//Left Site
		Qf.union(encode(i,j), encode(i,j-1));
	if(j < size-1 && j != size-1 && isOpen(i, j+1))
		Qf.union(encode(i,j), encode(i,j+1));
	if(i > 0 && isOpen(i-1,j))              //Above 
		Qf.union(encode(i,j), encode(i-1,j));
	if(i < size && i != size-1 && isOpen(i+1,j))		//Below
		Qf.union(encode(i,j), encode(i+1,j));
	    		
		}
	}
    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
	return array[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
	
	return Qf.connected(encode(i,j), top);
    }

    // Returns number of open sites.
    public int numberOfOpenSites() {
	return count;	
    }

    // Returns true if the system percolates, and false otherwise.
    public boolean percolates() {
	return Qf.connected(top,bottom);
    }

    // Returns an integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
	return i*size+j+1;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation perc = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }            
    }
}
