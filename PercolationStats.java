import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
	private int totalSites, trials, count;
	private double [] experiments;

    // Performs T independent experiments (Monte Carlo simulations) on an 
    // N-by-N grid.
    public PercolationStats(int N, int T) {
	totalSites = N*N;
	trials = T;
	experiments = new double[T];
	
	for(int i=0; i<T; i++) {
		Percolation P = new Percolation(N);
		count = 0;
		while(!P.percolates()) {
			int row = StdRandom.uniform(0,N);
			int col = StdRandom.uniform(0,N);
			if(!P.isOpen(row,col)) {
				P.open(row,col);
				count++;
			}
		}
		//Record fraction of opened/total sites
		experiments[i] = (double)count/(double)totalSites;
		}
	}		
	
    
    
    // Returns the sample mean of percolation threshold.
    public double mean() {
	return StdStats.mean(experiments);
    }

    // Returns the standard deviation of percolation threshold.
    public double stddev() {
	return StdStats.stddev(experiments);
    }

    // Returns the low endpoint of the 95% confidence interval.
    public double confidenceLow() {
	return mean() - (1.96*stddev()/Math.sqrt((double)trials));
    }

    // Returns the high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
	return mean() + (1.96*stddev()/Math.sqrt((double)trials));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
