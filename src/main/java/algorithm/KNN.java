package algorithm;

import distance.Distance;
import distance.EuclideanDistance;
import model.RowWithLabel;
import model.TableWithLabels;

import java.util.List;

public class KNN implements Algorithm<TableWithLabels, List<Double>, Integer> {
    private Distance dist;
    private TableWithLabels table;

    public KNN(){
        this.dist =new EuclideanDistance();
    }
    public KNN(Distance dist){
        this.dist = dist;
    }

    @Override
    public void train(TableWithLabels data) throws Exception {
        this.table = data;
    }
    @Override
    public Integer estimate(List<Double> sample) {
        double minDistance = Double.MAX_VALUE;
        String nearestLabel = "";

        for (int i = 0; i < table.getRowCount(); i++) {
            RowWithLabel current = table.getRowAt(i);
            double dist = this.dist.calculateDistance(sample, current.getData());
            if (dist < minDistance) {
                minDistance = dist;
                nearestLabel = current.getLabel();
            }
        }
        return table.getLabelAsInteger(nearestLabel);
    }
}