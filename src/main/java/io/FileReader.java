package io;

import model.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class FileReader<T extends Table> extends ReaderTemplate<T> {

    protected Scanner scanner;

    public FileReader(T table, String source) {
        super(table, source);
    }

    @Override
    void openSource(String source) {
        try {
            scanner = new Scanner(new File(source));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
}