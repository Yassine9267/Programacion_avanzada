package practica1;
import java.util.ArrayList;
import java.util.List;

public class Table {
    protected List<String> headers;
    protected List<Row> rows;

    public Table() {
        headers = new ArrayList<>();
        rows = new ArrayList<>();
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    public Row getRowAt(int rowNumber) {
        return rows.get(rowNumber);
    }

    public List<Double> getColumnAt(int index) {
        List<Double> column = new ArrayList<>();

        for (Row row : rows) {
            column.add(row.getData().get(index));
        }

        return column;
    }
    public int getRowCount() {
        return rows.size();
    }
    public List<String> getHeaders() {
        return headers;
    }
}