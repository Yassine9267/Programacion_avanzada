package distance;

import java.util.List;

public class ManhattanDistance implements Distance {

    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double sum = 0;
        for (int i = 0; i < p.size(); i++) {
            double diff = p.get(i) - q.get(i);
            sum += Math.abs(diff);
        }
        return sum;
    }


}
