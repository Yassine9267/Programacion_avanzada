package io;

import model.Row;
import model.Table;

import java.util.ArrayList;
import java.util.List;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    public CSVUnlabeledFileReader(String s) {
        super(s);
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

        List<String> headerList = new ArrayList<>();
        for (String h : parts) {
            headerList.add(h);
        }

        table.setHeaders(headerList);
    }
}