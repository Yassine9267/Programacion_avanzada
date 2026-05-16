package algorithm;

import distance.Distance;
import model.Table;
import model.TableWithLabels;

import java.util.List;

public interface Algorithm<T extends Table, M, N> {

    void train(T data) throws Exception;

    N estimate(M sample);
}
