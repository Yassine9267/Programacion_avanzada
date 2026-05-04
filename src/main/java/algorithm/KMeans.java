package algorithm;

import distance.Distance;
import exception.InvalidClusterNumberException;
import model.Row;
import model.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans implements Algorithm<Double> {

    private int numClusters;
    private int numIteracions;
    private long seed;
    private List<Row> centroides;
    private Distance distance;

    public KMeans(int numClusters, int numIteracions, long seed, Distance distance) {
        this.numClusters = numClusters;
        this.numIteracions = numIteracions;
        this.seed = seed;
        this.centroides = new ArrayList<>();
        this.distance = distance;
    }

    @Override
    public void train(Table datos, Distance distance) throws InvalidClusterNumberException {
        if (numClusters > datos.getRowCount()) {
            throw new InvalidClusterNumberException(numClusters, datos.getRowCount());
        }
        Random random = new Random(seed);
        List<Integer> usados = new ArrayList<>();
        while (centroides.size() < numClusters) {
            int indice = random.nextInt(datos.getRowCount());
            if (!usados.contains(indice)) {
                usados.add(indice);
                centroides.add(new Row(new ArrayList<>(datos.getRowAt(indice).getData())));
            }
        }
        for (int iter = 0; iter < numIteracions; iter++) {
            List<List<Row>> clusters = new ArrayList<>();
            for (int i = 0; i < numClusters; i++) {
                clusters.add(new ArrayList<>());
            }
            for (int i = 0; i < datos.getRowCount(); i++) {
                Row punto = datos.getRowAt(i);
                double minDist = Double.MAX_VALUE;
                int mejorCluster = -1;
                for (int j = 0; j < centroides.size(); j++) {
                    double distancia = this.distance.calculateDistance(punto.getData(), centroides.get(j).getData());
                    if (distancia < minDist) {
                        minDist = distancia;
                        mejorCluster = j;
                    }
                }

                clusters.get(mejorCluster).add(punto);
            }
            for (int i = 0; i < numClusters; i++) {
                List<Row> cluster = clusters.get(i);
                if (cluster.isEmpty()) continue;
                centroides.set(i, calcularMedia(cluster));
            }
        }
    }

    private double distancia(List<Double> a, List<Double> b) {
        double suma = 0;
        for (int i = 0; i < a.size(); i++) {
            double diff = a.get(i) - b.get(i);
            suma += diff * diff;
        }
        return Math.sqrt(suma);
    }

    private Row calcularMedia(List<Row> puntos) {
        int dimensiones = puntos.get(0).getData().size();
        List<Double> suma = new ArrayList<>();
        for (int i = 0; i < dimensiones; i++) {
            suma.add(0.0);
        }
        for (Row r : puntos) {
            for (int i = 0; i < dimensiones; i++) {
                suma.set(i, suma.get(i) + r.getData().get(i));
            }
        }
        for (int i = 0; i < dimensiones; i++) {
            suma.set(i, suma.get(i) / puntos.size());
        }
        return new Row(suma);
    }

    public Integer estimate(List<Double> dato) {
        double minDist = Double.MAX_VALUE;
        int mejorCluster = -1;
        for (int i = 0; i < centroides.size(); i++) {
            double distancia = distancia(dato, centroides.get(i).getData());
            if (distancia < minDist) {
                minDist = distancia;
                mejorCluster = i;
            }
        }
        return mejorCluster;
    }
}