package practica3;
import java.util.*;

import practica1.Row;
import practica1.Table;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    public CSVUnlabeledFileReader(String s) {
        super(new Table(), s);
    }

    @Override
    void processHeaders(String headers) {
        String[] parts = headers.split(",");

        List<String> headerList = new ArrayList<>();
        for (String h : parts) {
            headerList.add(h);
        }

        table.setHeaders(headerList);
    }

    @Override
    void processData(String data) {
        String[] parts = data.split(",");
        List<Double> row = new ArrayList<>();
        for (int i = 0; i < parts.length; i++) {
            row.add(Double.parseDouble(parts[i]));
        }
        table.addRow(new Row(row));
    }
}