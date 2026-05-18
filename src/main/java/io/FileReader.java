package io;

import model.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class FileReader<T extends Table> extends ReaderTemplate<T> {

    protected Scanner scanner;

    public FileReader(String source) {
        super(source);
    }

    @Override
    void openSource(String source) {
        try {
            var url = getClass().getClassLoader().getResource(source);
            scanner = new Scanner(new File(url.toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    boolean hasMoreData() {
        return scanner.hasNextLine();
    }

    @Override
    String getNextData() {
        return scanner.nextLine();
    }

    @Override
    void closeSource() {
        scanner.close();
    }
    protected List<Double> parseRow(String data, int limit) {
        String[] parts = data.split(",");
        List<Double> row = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            row.add(Double.parseDouble(parts[i].trim()));
        }

        return row;
    }
}