package algorithm;

import distance.Distance;
import model.Table;

import java.util.List;

public interface Algorithm<T> {

    void train(Table data, Distance distance) throws Exception;

    Integer estimate(List<T> sample);
}