package view;

import controller.RecommendationController;
import controller.SongRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        VBox root = new VBox();

        // Títulos
        Label tituloCanciones = new Label("Song Titles");
        tituloCanciones.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        Label tituloDist = new Label("Distance Type");
        tituloDist.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        Label tituloRecomendacion = new Label("Recommendation type");
        tituloRecomendacion.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        HBox cajatituloCanciones = new HBox(tituloCanciones);
        HBox cajatituloDistancias = new HBox(tituloDist);
        HBox cajatituloRecomendacion = new HBox(tituloRecomendacion);
        cajatituloRecomendacion.setPadding(new Insets(5, 0, 0, 30));
        cajatituloDistancias.setPadding(new Insets(10, 0, 0, 30));
        cajatituloCanciones.setPadding(new Insets(10, 0, 0, 30));

        // Lista de canciones
        SongRepository repository = new SongRepository();
        ObservableList<String> canciones = repository.getSongTitles();
        ListView<String> listaViewCanciones = new ListView<>(canciones);
        listaViewCanciones.setPrefWidth(400);
        listaViewCanciones.setPrefHeight(450);
        HBox cajaLista = new HBox(listaViewCanciones);
        cajaLista.setPadding(new Insets(0, 30, 0, 30));

        // Radio buttons
        RadioButton euclidean = new RadioButton("Euclidean");
        RadioButton manhattan = new RadioButton("Manhattan");
        RadioButton recomendacionSongF = new RadioButton("Recommend based on song features");
        RadioButton recomendacionSongG = new RadioButton("Recommend based on guessed genre");

        ToggleGroup distancias = new ToggleGroup();
        ToggleGroup recomendacion = new ToggleGroup();
        euclidean.setToggleGroup(distancias);
        manhattan.setToggleGroup(distancias);
        recomendacionSongF.setToggleGroup(recomendacion);
        recomendacionSongG.setToggleGroup(recomendacion);

        // Botón
        Button recommend = new Button("Recommend...");
        recommend.setDisable(true);

        //habilitar si solo esta seleccionado
        Runnable checkReady = () -> {
            boolean songSelected = listaViewCanciones.getSelectionModel()
                    .getSelectedItem() != null;
            boolean recSelected  = recomendacion.getSelectedToggle() != null;
            boolean distSelected = distancias.getSelectedToggle() != null;
            recommend.setDisable(!(songSelected && recSelected && distSelected));
        };

        listaViewCanciones.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, nuevo) -> checkReady.run());
        recomendacion.selectedToggleProperty()
                .addListener((obs, old, nuevo) -> checkReady.run());
        distancias.selectedToggleProperty()
                .addListener((obs, old, nuevo) -> checkReady.run());

        //abrir segunda ventana
        recommend.setOnAction(e -> {
            String song = listaViewCanciones.getSelectionModel().getSelectedItem();
            boolean byFeatures = recomendacionSongF.isSelected();
            boolean eucl = euclidean.isSelected();
            new RecommendationView().show(song, byFeatures, eucl);
        });

        // Layout
        HBox cajaDistancias = new HBox(euclidean, manhattan);
        VBox cajarecomendacion = new VBox(recomendacionSongG, recomendacionSongF);
        HBox cajaRecomend = new HBox(recommend);
        cajarecomendacion.setSpacing(10);
        cajaDistancias.setAlignment(Pos.CENTER);
        cajaDistancias.setSpacing(20);
        cajarecomendacion.setPadding(new Insets(0, 0, 0, 30));
        cajaRecomend.setPadding(new Insets(5, 0, 0, 30));

        root.getChildren().add(cajatituloRecomendacion);
        root.getChildren().add(cajarecomendacion);
        root.getChildren().add(cajatituloDistancias);
        root.getChildren().add(cajaDistancias);
        root.getChildren().add(cajatituloCanciones);
        root.getChildren().add(cajaLista);
        root.getChildren().add(cajaRecomend);

        Scene scene = new Scene(root, 400, 700);
        stage.setTitle("Song Recommender");
        stage.setScene(scene);
        stage.show();
    }
}