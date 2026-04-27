package practica3;
import java.util.*;
public class EuclideanDistance implements Distance{
    @Override
    public double calculateDistance(List<Double> p, List<Double> q){
        double resultado=0;
        for(int i=0; i< p.size(); i++){
            resultado+=Math.pow(p.get(i)-q.get(i), 2);
        }
        return Math.sqrt(resultado);
    }
}
