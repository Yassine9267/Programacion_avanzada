package io;

import model.RowWithLabel;
import model.TableWithLabels;

import java.util.ArrayList;
import java.util.List;


public class CSVLabeledFileReader extends FileReader<TableWithLabels> {

    public CSVLabeledFileReader(String s) {
        super(s);
        this.table=new TableWithLabels();
    }

    @Override
    void processData(String data) {

        String[] parts = data.split(",");

        List<Double> row = parseRow(data, parts.length - 1);

        String label = parts[parts.length - 1].trim();

        table.addRow(new RowWithLabel(row, label));
    }


    @Override
    void processHeaders(String headers) {
        String[] labels = headers.split(",");

        List<String> headerList = new ArrayList<>();
        for (int i = 0; i < labels.length - 1; i++) {
            headerList.add(labels[i].trim());
        }

        table.setHeaders(headerList);
    }
}