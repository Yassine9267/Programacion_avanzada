package algorithm;

import distance.EuclideanDistance;
import exception.LikedItemNotFoundException;
import javafx.scene.control.Tab;
import model.Table;

import java.util.ArrayList;
import java.util.List;

public class RecSys<T extends Table>{

    private Algorithm<T, List<Double>, Integer> algorithm;
    private Table testData;
    private List<String> testItemNames;
    private List<Integer> predictions;

    public RecSys(Algorithm<T, List<Double>, Integer> algorithm) {
        this.algorithm = algorithm;
        this.predictions = new ArrayList<>();
    }
    public void train(T trainData) {
        try {
            algorithm.train(trainData);
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