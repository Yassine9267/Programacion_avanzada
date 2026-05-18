package io;

import model.Row;
import model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    public CSVUnlabeledFileReader(String source) {
        super(source);
        this.table=new Table();
    }

    @Override
    void processData(String data) {
        String[] parts = data.split(",");
        List<Double> row = parseRow(data, parts.length);

        table.addRow(new Row(row));
    }

    @Override
    void processHeaders(String headers) {
        String[] parts = headers.split(",");
        table.setHeaders(new ArrayList<>(Arrays.asList(parts)));
    }
}