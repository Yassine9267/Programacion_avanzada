package controller;

import algorithm.KMeans;
import algorithm.KNN;
import algorithm.RecSys;
import distance.Distance;
import distance.EuclideanDistance;
import distance.ManhattanDistance;
import model.SongRepository;
import model.Table;
import model.TableWithLabels;
import java.util.List;

public class RecommendationController {

    private static final int NUM_CLUSTERS = 5;
    private static final int NUM_ITERATIONS = 100;
    private static final long SEED = 42;
    private static final String TRAIN_LABELED = "recommender/songs_train.csv";
    private static final String TRAIN_UNLABELED = "recommender/songs_train_withoutnames.csv";
    private static final String TEST_UNLABELED = "recommender/songs_test_withoutnames.csv";

    public List<String> recommend(String song, boolean recommendByFeatures,
                                  boolean useEuclidean, int numRecommendations) throws Exception {
        Distance distance = useEuclidean ? new EuclideanDistance() : new ManhattanDistance();
        SongRepository repo = new SongRepository();
        List<String> songNames = repo.getSongTitles();
        RecSys<?> recSys;

        if (recommendByFeatures) {
            RecSys<TableWithLabels> r = new RecSys<>(new KNN(distance));
            r.train(repo.readLabeledTable(TRAIN_LABELED));
            r.initialise(repo.readUnlabeledTable(TEST_UNLABELED), songNames);
            recSys = r;
        } else {
            RecSys<Table> r = new RecSys<>(new KMeans(NUM_CLUSTERS, NUM_ITERATIONS, SEED, distance));
            r.train(repo.readUnlabeledTable(TRAIN_UNLABELED));
            r.initialise(repo.readUnlabeledTable(TEST_UNLABELED), songNames);
            recSys = r;
        }
        return recSys.recommend(song, numRecommendations);
    }
}