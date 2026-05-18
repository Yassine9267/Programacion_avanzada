package view;

import model.SongRepository;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private ListView<String> songListView;
    private ToggleGroup distanceGroup;
    private ToggleGroup recommendationGroup;
    private RadioButton euclidean;
    private RadioButton byFeatures;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.getChildren().addAll(
                createRecommendationTypeSection(),
                createDistanceSection(),
                createSongListSection(),
                createRecommendButton()
        );
        stage.setScene(new Scene(root, 400, 700));
        stage.setTitle("Song Recommender");
        stage.show();
    }

    private VBox createRecommendationTypeSection() {
        Label title = createTitle("Recommendation type");
        RadioButton byGenre = new RadioButton("Recommend based on guessed genre");
        byFeatures = new RadioButton("Recommend based on song features");
        recommendationGroup = new ToggleGroup();
        byGenre.setToggleGroup(recommendationGroup);
        byFeatures.setToggleGroup(recommendationGroup);
        VBox box = new VBox(10, title, byGenre, byFeatures);
        box.setPadding(new Insets(5, 0, 0, 30));
        return box;
    }

    private VBox createDistanceSection() {
        Label title = createTitle("Distance Type");
        euclidean = new RadioButton("Euclidean");
        RadioButton manhattan = new RadioButton("Manhattan");
        distanceGroup = new ToggleGroup();
        euclidean.setToggleGroup(distanceGroup);
        manhattan.setToggleGroup(distanceGroup);
        HBox buttons = new HBox(20, euclidean, manhattan);
        buttons.setAlignment(Pos.CENTER);
        VBox box = new VBox(title, buttons);
        box.setPadding(new Insets(10, 0, 0, 30));
        return box;
    }

    private VBox createSongListSection() {
        Label title = createTitle("Song Titles");
        songListView = new ListView<>(new SongRepository().getSongTitles());
        songListView.setPrefSize(400, 450);
        VBox box = new VBox(title, songListView);
        box.setPadding(new Insets(10, 30, 0, 30));
        return box;
    }

    private HBox createRecommendButton() {
        Button recommend = new Button("Recommend...");
        recommend.setDisable(true);
        setupButtonValidation(recommend);
        recommend.setOnAction(e -> new RecommendationView().show(
                songListView.getSelectionModel().getSelectedItem(),
                byFeatures.isSelected(),
                euclidean.isSelected()
        ));
        HBox box = new HBox(recommend);
        box.setPadding(new Insets(5, 0, 0, 30));
        return box;
    }

    private void setupButtonValidation(Button recommend) {
        Runnable check = () -> recommend.setDisable(
                songListView.getSelectionModel().getSelectedItem() == null ||
                        recommendationGroup.getSelectedToggle() == null ||
                        distanceGroup.getSelectedToggle() == null
        );
        songListView.getSelectionModel().selectedItemProperty().addListener((o, old, n) -> check.run());
        recommendationGroup.selectedToggleProperty().addListener((o, old, n) -> check.run());
        distanceGroup.selectedToggleProperty().addListener((o, old, n) -> check.run());
    }

    private Label createTitle(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        return label;
    }
}