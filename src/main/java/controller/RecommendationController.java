package controller;

import algorithm.KMeans;
import algorithm.KNN;
import algorithm.RecSys;
import distance.Distance;
import distance.EuclideanDistance;
import distance.ManhattanDistance;
import model.Table;
import model.TableWithLabels;
import java.util.List;

public class RecommendationController {

    public List<String> recommend(String song, boolean recommendByFeatures,
                                  boolean useEuclidean, int numRecommendations) throws Exception {

        Distance distancia = useEuclidean ? new EuclideanDistance() : new ManhattanDistance();
        SongRepository repo = new SongRepository();
        List<String> songNames = repo.getSongTitles();
        RecSys<?> recSys;

        if (recommendByFeatures) {
            RecSys<TableWithLabels> r = new RecSys<>(new KNN(distancia));
            r.train(repo.leerTablaLabel("recommender/songs_train.csv"));
            r.initialise(repo.leerTablaUnlabel("recommender/songs_test_withoutnames.csv"), songNames);
            recSys = r;
        } else {
            RecSys<Table> r = new RecSys<>(new KMeans(5, 100, 42, distancia));
            r.train(repo.leerTablaUnlabel("recommender/songs_train_withoutnames.csv"));
            r.initialise(repo.leerTablaUnlabel("recommender/songs_test_withoutnames.csv"), songNames);
            recSys = r;
        }

        return recSys.recommend(song, numRecommendations);
    }
}