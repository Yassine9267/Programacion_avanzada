package algorithm;

import model.Table;

public interface Algorithm<T extends Table, M, N> {
    void train(T data) throws Exception;
    N estimate(M sample);
}