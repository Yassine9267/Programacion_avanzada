package practica2;

import practica1.Table;
import java.util.List;

public interface Algorithm<T> {

    void train(Table data) throws Exception;

    Integer estimate(List<T> sample);
}