/**
 * PercolationStats
 * Jorge Chong Chang
 * 2015-06-26
 */
public class PercolationStats {
    private double[] percolationRatios;
    private int numberOfExperiments;
    private int size;
    public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        
        numberOfExperiments = T;
        size = N;
        percolationRatios = new double[numberOfExperiments];
        
        /** this perform the experiments */
        for (int k = 0; k < numberOfExperiments; k++)
        {
            int openSites = 0;
            Percolation P = new Percolation(size);
            boolean percolates = false;
            while (!percolates) {
                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;
                
                if (!P.isOpen(i, j))
                {
                    P.open(i, j);
                    openSites++;
                }
                
                if (P.percolates()) percolates = true;
            }
            //P.print();
            percolationRatios[k] = ((double) openSites) / ((double) (size * size));
        }
        
    }
    
    public double mean()                     // sample mean of percolation threshold
    {
        return StdStats.mean(percolationRatios);
    }
        
    public double stddev()                   // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(percolationRatios);
    }
    public double confidenceLo()             // returns lower bound of the 95% confidence interval
    {
       return mean() - ((1.96 * stddev()) / (Math.sqrt(numberOfExperiments)));
    }
    public double confidenceHi()             // returns upper bound of the 95% confidence interval
    {
        return mean() + ((1.96 * stddev()) / (Math.sqrt(numberOfExperiments)));
    }
    
    public static void main(String[] args)   // test client, described below    
    {
        int N = Integer.parseInt(args[0]); /** Number of rows */
        int T = Integer.parseInt(args[1]); /** Number of experiments */
        
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        
        
        PercolationStats PS = new PercolationStats(N, T);
        
        StdOut.printf("mean = %f\n", PS.mean());
        StdOut.printf("stddev = %f\n", PS.stddev());
        StdOut.printf("95%% confidence interval = %.20f , %.20f", PS.confidenceLo(), PS.confidenceHi());
        
    }
}
