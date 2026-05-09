package algorithm;

import distance.Distance;
import model.Table;
import model.TableWithLabels;

import java.util.List;

public interface Algorithm<T> {

    void train(Table data) throws Exception;

    Integer estimate(List<T> sample);
}
