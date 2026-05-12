package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainApp extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage){
        //root principal
        VBox root = new VBox();

        //creacion de titulos
        Label tituloCanciones=new Label("Recommendation Type");
        Label tituloLista=new Label("Song Titles");

        //lista de canciones
        ObservableList<String> canciones =
                FXCollections.observableArrayList("song1", "song2");
        ListView<String> lista = new ListView<>(canciones);
        lista.setPrefWidth(400);
        lista.setPrefHeight(450);
        HBox cajaLista = new HBox(lista);
        cajaLista.setPadding(new Insets(40, 30, 0, 30));

        //botones de distancia
        RadioButton euclidean=new RadioButton("Euclidea");
        RadioButton manhattan=new RadioButton("Manhattan");
        ToggleGroup distancias=new ToggleGroup();
        euclidean.setToggleGroup(distancias);
        manhattan.setToggleGroup(distancias);
        HBox cajaDistancias=new HBox(euclidean, manhattan);
        cajaDistancias.setAlignment(Pos.CENTER);
        cajaDistancias.setPadding(new Insets(180, 30, 0, 30));

        //añadir elementos a root
        root.getChildren().add(cajaDistancias);
        root.getChildren().add(cajaLista);

        Scene scene = new Scene(root, 400, 700);

        stage.setTitle("Song Recommender");
        stage.setScene(scene);
        stage.show();
    }
}