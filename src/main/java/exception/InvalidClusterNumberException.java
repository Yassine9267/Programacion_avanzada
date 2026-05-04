package exception;

public class InvalidClusterNumberException extends Exception {

    private int numClusters;
    private int numberOfData;

    public InvalidClusterNumberException(int numClusters, int numberOfData) {
        super("Número de clusters inválido: " + numClusters);
        this.numClusters = numClusters;
        this.numberOfData = numberOfData;
    }

    public int getNumberOfCusters() {
        return numClusters;
    }

    public int getNumRows() {
        return numberOfData;
    }
}