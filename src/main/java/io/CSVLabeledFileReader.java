package io;

import model.RowWithLabel;
import model.TableWithLabels;

import java.util.ArrayList;
import java.util.List;


public class CSVLabeledFileReader extends FileReader<TableWithLabels> {

    public CSVLabeledFileReader(String s) {
        super(new TableWithLabels(), s);
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

    @Override
    void processData(String data) {
        String[] parts = data.split(",");

        List<Double> row = new ArrayList<>();

        for (int i = 0; i < parts.length - 1; i++) {
            row.add(Double.parseDouble(parts[i]));
        }

        String label = parts[parts.length - 1]; // 👈 clase

        table.addRow(new RowWithLabel(row, label)); // 🔥 clave
    }
}