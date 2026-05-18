package algorithm;

import distance.Distance;
import distance.EuclideanDistance;
import exception.InvalidClusterNumberException;
import model.Row;
import model.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans implements Algorithm<Table, List<Double>, Integer> {

    private int numClusters;
    private int numIterations;
    private long seed;
    private List<Row> centroids;
    private Distance distance;

    public KMeans(int numClusters, int numIterations, long seed) {
        this(numClusters, numIterations, seed, new EuclideanDistance());
    }

    public KMeans(int numClusters, int numIterations, long seed, Distance distance) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.centroids = new ArrayList<>();
        this.distance = distance;
    }
    @Override
    public void train(Table data) throws InvalidClusterNumberException {
        if (numClusters > data.getRowCount()) {
            throw new InvalidClusterNumberException(numClusters, data.getRowCount());
        }
        initializeCentroids(data);
        for (int iter = 0; iter < numIterations; iter++) {
            List<List<Row>> clusters = assignToClusters(data);
            updateCentroids(clusters);
        }
    }

    private void initializeCentroids(Table data) {
        Random random = new Random(seed);
        List<Integer> used = new ArrayList<>();
        while (centroids.size() < numClusters) {
            int index = random.nextInt(data.getRowCount());
            if (!used.contains(index)) {
                used.add(index);
                centroids.add(new Row(new ArrayList<>(data.getRowAt(index).getData())));
            }
        }
    }

    private List<List<Row>> assignToClusters(Table data) {
        List<List<Row>> clusters = new ArrayList<>();
        for (int i = 0; i < numClusters; i++) clusters.add(new ArrayList<>());
        for (int i = 0; i < data.getRowCount(); i++) {
            Row point = data.getRowAt(i);
            int nearest = findNearestCentroid(point.getData());
            clusters.get(nearest).add(point);
        }
        return clusters;
    }

    private int findNearestCentroid(List<Double> point) {
        double minDist = Double.MAX_VALUE;
        int nearest = -1;
        for (int j = 0; j < centroids.size(); j++) {
            double dist = distance.calculateDistance(point, centroids.get(j).getData());
            if (dist < minDist) { minDist = dist; nearest = j; }
        }
        return nearest;
    }

    private void updateCentroids(List<List<Row>> clusters) {
        for (int i = 0; i < numClusters; i++) {
            if (!clusters.get(i).isEmpty())
                centroids.set(i, calculateMean(clusters.get(i)));
        }
    }

    private Row calculateMean(List<Row> points) {
        int dimensions = points.get(0).getData().size();
        List<Double> sum = new ArrayList<>();
        for (int i = 0; i < dimensions; i++) {
            sum.add(0.0);
        }
        for (Row r : points) {
            for (int i = 0; i < dimensions; i++) {
                sum.set(i, sum.get(i) + r.getData().get(i));
            }
        }
        for (int i = 0; i < dimensions; i++) {
            sum.set(i, sum.get(i) / points.size());
        }
        return new Row(sum);
    }

    public Integer estimate(List<Double> sample) {
        return findNearestCentroid(sample);
    }
}