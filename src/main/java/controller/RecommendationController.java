package controller;

import algorithm.Algorithm;
import algorithm.KMeans;
import algorithm.KNN;
import distance.Distance;
import distance.EuclideanDistance;
import distance.ManhattanDistance;
import model.Table;
import model.TableWithLabels;

import java.util.ArrayList;
import java.util.List;

public class RecommendationController {

    public List<String> recommend(String song, boolean recommendByFeatures,
                                  boolean useEuclidean, int numRecommendations) throws Exception {

        Distance distancia = useEuclidean ? new EuclideanDistance() : new ManhattanDistance();
        SongRepository repo = new SongRepository();

        List<String> allSongs = new ArrayList<>(repo.getSongTitles());
        int songIndex = allSongs.indexOf(song);
        if (songIndex == -1) throw new Exception("Canción no encontrada: " + song);

        if (recommendByFeatures) {
            // KNN: recomendar canciones del mismo género
            Algorithm<TableWithLabels, List<Double>, Integer> algorithm = new KNN(distancia);
            TableWithLabels tabla = repo.leerTablaLabel("recommender/songs_train.csv");
            algorithm.train(tabla);

            List<Double> songFeatures = tabla.getRowAt(songIndex).getData();
            Integer targetGenre = algorithm.estimate(songFeatures);

            List<String> recommendations = new ArrayList<>();
            for (int i = 0; i < tabla.getRowCount()
                    && recommendations.size() < numRecommendations; i++) {
                if (i == songIndex) continue;
                Integer genre = algorithm.estimate(tabla.getRowAt(i).getData());
                if (genre.equals(targetGenre)) {
                    recommendations.add(allSongs.get(i));
                }
            }
            return recommendations;

        } else {
            // KMeans: recomendar canciones del mismo cluster
            KMeans algorithm = new KMeans(5, 100, 42, distancia);
            Table tabla = repo.leerTablaUnlabel("recommender/songs_train_withoutnames.csv");
            algorithm.train(tabla);

            List<Double> songFeatures = tabla.getRowAt(songIndex).getData();
            Integer targetCluster = algorithm.estimate(songFeatures);

            List<String> recommendations = new ArrayList<>();
            for (int i = 0; i < tabla.getRowCount()
                    && recommendations.size() < numRecommendations; i++) {
                if (i == songIndex) continue;
                Integer cluster = algorithm.estimate(tabla.getRowAt(i).getData());
                if (cluster.equals(targetCluster)) {
                    recommendations.add(allSongs.get(i));
                }
            }
            return recommendations;
        }
    }
}