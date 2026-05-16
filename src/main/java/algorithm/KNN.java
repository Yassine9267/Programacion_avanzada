package algorithm;

import distance.Distance;
import distance.EuclideanDistance;
import model.RowWithLabel;
import model.Table;
import model.TableWithLabels;

import java.util.List;

public class KNN implements Algorithm<TableWithLabels, List<Double>, Integer> {
    private Distance distance;
    private TableWithLabels tabla;

    public KNN(){
        this.distance=new EuclideanDistance();
    }
    public KNN(Distance distance){
        this.distance=distance;
    }

    @Override
    public void train(TableWithLabels data) throws Exception {
        this.tabla = data;
    }
    @Override
    public Integer estimate(List<Double> sample) {

        double minDistance = Double.MAX_VALUE;
        String nearestLabel = "";

        for (int i = 0; i < tabla.getRowCount(); i++) {

            RowWithLabel current = tabla.getRowAt(i);

            double distance = this.distance.calculateDistance(sample, current.getData());

            if (i == 0) {
                minDistance = distance;
                nearestLabel = current.getLabel();
            } else if (distance < minDistance) {
                minDistance = distance;
                nearestLabel = current.getLabel();
            }
        }

        return tabla.getLabelAsInteger(nearestLabel);
    }
}