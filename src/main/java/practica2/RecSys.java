package practica2;

import practica1.Table;
import practica3.EuclideanDistance;

import java.util.*;

public class RecSys {

    private Algorithm<Double> algorithm;
    private Table testData;
    private List<String> testItemNames;
    private List<Integer> predictions;
    public RecSys(Algorithm<Double> algorithm) {
        this.algorithm = algorithm;
        this.predictions = new ArrayList<>();
    }
    public void train(Table trainData) {
        try {
            algorithm.train(trainData, new EuclideanDistance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void initialise(Table testData, List<String> testItemNames) {
        this.testData = testData;
        this.testItemNames = testItemNames;
        this.predictions = new ArrayList<>();
        for (int i = 0; i < testData.getRowCount(); i++) {
            List<Double> fila = testData.getRowAt(i).getData();
            int cluster = algorithm.estimate(fila);
            predictions.add(cluster);
        }
    }
    public List<String> recommend(String nameLikedItem, int numRecommendations)
            throws LikedItemNotFoundException {
        if (!testItemNames.contains(nameLikedItem)) {
            throw new LikedItemNotFoundException(nameLikedItem);
        }
        List<String> result = new ArrayList<>();
        int index = testItemNames.indexOf(nameLikedItem);
        int clusterObjetivo = predictions.get(index);
        for (int i = 0; i < predictions.size(); i++) {
            if (i == index) continue;
            if (predictions.get(i) == clusterObjetivo) {
                result.add(testItemNames.get(i));
            }
            if (result.size() == numRecommendations) break;
        }
        return result;
    }
}