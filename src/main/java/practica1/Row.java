package practica1;

import java.util.List;

public class Row {
    protected List<Double> data;

    public Row(List<Double> data) {
        this.data = data;
    }

    public List<Double> getData() {
        return data;
    }
}