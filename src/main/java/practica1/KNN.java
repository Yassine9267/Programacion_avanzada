package practica1;
import practica2.Algorithm;
import practica3.Distance;

import java.util.List;

public class KNN implements Algorithm<Double> {
    private Distance distance;
    private TableWithLabels tabla;

    @Override
    public void train(Table data, Distance distance) throws Exception {
        this.tabla = (TableWithLabels) data;
        this.distance=distance;
    }

    public Integer estimate(List<Double> sample) {

        double minDistance = Double.MAX_VALUE;
        String nearestLabel = "";

        for (int i=0; i<tabla.getRowCount(); i++) {

            RowWithLabel current =tabla.getRowAt(i);

            double distance=this.distance.calculateDistance(sample, current.getData());

            if (i==0){
                minDistance=distance;
                nearestLabel=current.getLabel();
            }else if (distance<minDistance){
                minDistance=distance;
                nearestLabel=current.getLabel();
            }
        }

        return tabla.getLabelAsInteger(nearestLabel);
    }
}