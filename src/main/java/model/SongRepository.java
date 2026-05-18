package model;

import io.CSVLabeledFileReader;
import io.CSVUnlabeledFileReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SongRepository {
    public ObservableList<String> getSongTitles() {
        try {return FXCollections.observableArrayList(Files.readAllLines(Path.of("src/main/resources/recommender/songs_test_names.csv")));
        } catch (IOException e) {e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    public TableWithLabels readLabeledTable(String ruta){
        CSVLabeledFileReader reader=new CSVLabeledFileReader(ruta);
        return reader.readTableFromSource();
    }
    public Table readUnlabeledTable(String ruta){
        CSVUnlabeledFileReader reader=new CSVUnlabeledFileReader(ruta);
        return reader.readTableFromSource();
    }

}
