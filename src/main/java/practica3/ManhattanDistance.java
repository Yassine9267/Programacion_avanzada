package practica3;
import java.util.*;

public class ManhattanDistance implements Distance{

    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double suma=0;
        for(int i=0; i< p.size(); i++){
            double diferencia=p.get(i)-q.get(i);
            suma+=Math.abs(diferencia);
        }
        return suma;
    }



}
