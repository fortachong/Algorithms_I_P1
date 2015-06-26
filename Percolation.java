/**
 * Percolation
 * Jorge Chong Chang
 * 2015-06-26
 */

public class Percolation {
    
    /** Size of a Row */
    private int R;
    /** Union-Find Structure */
    private WeightedQuickUnionUF S;
    /** Union-Find Structure */
    private WeightedQuickUnionUF F;
    /** Data structure containing the sites 
      * sites is an array representing a 2D 
      * representation of the N x N square grid
      * 
      */
    private char[] sites;
    /** Number of sites open */
    private int numberOfOpenSites;
    
    /** Creates N by N grid with all sites blocked */
    public Percolation(int N)
    {
        /***
          * Grid:
          * # # #
          * # # #
          * # # #
          */
        if (N <= 0)
            throw new IllegalArgumentException();
        R = N;
        numberOfOpenSites = 0;
        /** The number of sites is R x R plus one site for the top and 
          * one for the bottom site
          */
        int numberofsites = R * R + 2;
        
        S = new WeightedQuickUnionUF(numberofsites);  
        F = new WeightedQuickUnionUF(numberofsites);  
        
        /*
         * Calculate the Indexes:
         *
         * 0 = the first site to determine percolation
         * N*N + 1 = the last site
         */
        sites = new char[numberofsites];
        for (int i = 0; i < numberofsites; i++) {
            /** 0 represents closed, 1 represents opened */
            sites[i] = 0;
        }
    }
    
    /** Opens the site located in row i, column j, if not already opened */
    public void open(int i, int j)
    {
        if (i < 1 || i > R) {
            throw new IndexOutOfBoundsException();
        }
        
        if (j < 1 || j > R) {
            throw new IndexOutOfBoundsException();
        }
        
        /** Calculation of the index in sites array */
        int index = (R * (i - 1)) + j;
        
        /** If the site is closed, opens the site */
        if (sites[index] == 0)
        {
            numberOfOpenSites++;
            sites[index] = 1;

            /** if in first row use the union-find structure to join to the 0-site */
            if (i == 1)
            {
                S.union(0, index);
                F.union(0, index);
            }
            
            /** if last row only use the S union-find structure */
            if (i == R)
            {
                S.union(index, R * R + 1);
            }
            
            /** Connect with the adjacents open sites */
            /** Connects with the top index*/
            int ti = i - 1;
            if (ti > 0) {
                /** Calculates the index */
                int tindex = (ti - 1) * R + j;
                
                if (sites[tindex] == 1)
                {
                    /** Connects index with tindex */
                    S.union(index, tindex);
                    F.union(index, tindex);
                }                
            }
            /** Connects with the down index */
            int di = i + 1;
            if (di <= R) {
                /** Calculates the index */
                int dindex = (di - 1) * R + j;
                
                if (sites[dindex] == 1)
                {
                    /** Connects index with dindex */
                    S.union(index, dindex);
                    F.union(index, dindex);
                }
            }
            /** Connects with left index */
            int li = j - 1;
            if (li > 0) {
                /** Calculates index */
                int lindex = (i - 1) * R + li;
                
                if (sites[lindex] == 1)
                {
                    /** Connects index with lindex */
                    S.union(index, lindex);
                    F.union(index, lindex);
                }
            }
            /** Connects with right index */
            int ri = j + 1;
            if (ri <= R) {
                /** Calculates the index */
                int rindex = (i - 1) * R + ri;
                
                if (sites[rindex] == 1)
                {
                    /** Connects index with rindex */
                    S.union(index, rindex);
                    F.union(index, rindex);
                }
            }
         }         
     }
     
    /** Test if site row i, column j is open */
    public boolean isOpen(int i, int j)
    {
        if (i < 1 || i > R) {
            throw new IndexOutOfBoundsException();
        }         
        if (j < 1 || j > R) {
            throw new IndexOutOfBoundsException();
        }         
        int index = R * (i - 1) + j;
        return sites[index] == 1;
    }
     
    /** A site is full if there is a path from the top to the site */
    public boolean isFull(int i, int j)
    {
        if (i < 1 || i > R) {
            throw new IndexOutOfBoundsException();
        }         
        if (j < 1 || j > R) {
            throw new IndexOutOfBoundsException();
        }
        int index = (i - 1) * R  + j;       
        if (sites[index] == 1)
        {
            return F.connected(0, index);
        }
        return false;
     }
     
    /** Does the system percolates? */
    public boolean percolates()
    {
        /** Determines if 0 is connected with R*R+1 */
        return S.connected(0, R * R + 1);
    }
     
    /** Imprimir */
    private void print()
    {
        /** Print the status of the matrix */
        for (int i = 1; i < sites.length - 1; i++)
        {
            StdOut.print(sites[i] + " ");
            if (i % R == 0) {
                 StdOut.printf("\n");
            }
        }
        return;
    }
     
    private int opensites()
    {
        return numberOfOpenSites;
    }
     
     
    public static void main(String[] args)
    {
         Percolation p = new Percolation(3);
         StdOut.println(p.percolates());
         StdOut.println(p.isFull(1, 1));
         
         p.open(1, 3);
         p.open(2, 3);
         p.open(3, 3);
         p.open(3, 1);
         p.open(2, 1);
         p.open(1, 1);
         p.print();
         StdOut.println(p.opensites());
    }
}
