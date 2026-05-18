package exception;

public class InvalidClusterNumberException extends Exception {

    private int numClusters;
    private int numberOfData;

    public InvalidClusterNumberException(int numClusters, int numberOfData) {
        super("Invalid number of clusters: " + numClusters);
        this.numClusters = numClusters;
        this.numberOfData = numberOfData;
    }

    public int getNumberOfClusters() {
        return numClusters;
    }

    public int getNumRows() {
        return numberOfData;
    }
}