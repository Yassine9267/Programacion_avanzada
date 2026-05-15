package controller;


import algorithm.Algorithm;
import algorithm.KMeans;
import algorithm.KNN;
import distance.Distance;
import distance.EuclideanDistance;
import distance.ManhattanDistance;
import model.Table;
import model.TableWithLabels;

public class RecommendationController {
    public void recommend(String song, boolean recommendByFeatures, boolean useEuclidean) {
        Distance distancia;
        SongRepository repo=new SongRepository();
        if(useEuclidean){
            distancia=new EuclideanDistance();
        }else{
            distancia=new ManhattanDistance();
        }
        Algorithm<Double> algorithm;
        if(recommendByFeatures){
            algorithm=new KNN(distancia);
            TableWithLabels tabla=repo.leerTablaLabel("src/main/resources/recommender/songs_train.csv");
            algorithm.train(tabla);
        }else{
            algorithm=new KMeans(5, 100, 42, distancia);
            Table tabla=repo.leerTablaUnlabel("src/main/resources/recommender/songs_train_withoutnames.csv");
            algorithm.train(tabla);
        }


    }

}
