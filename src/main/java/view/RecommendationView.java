package view;

import controller.RecommendationController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecommendationView {

    public void show(String song, boolean recommendByFeatures, boolean useEuclidean) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Spinner<Integer> spinner = createSpinner();
        ListView<String> resultList = new ListView<>();
        resultList.setPrefSize(460, 250);

        setupUpdater(spinner, resultList, song, recommendByFeatures, useEuclidean);

        root.getChildren().addAll(
                createSpinnerBox(spinner),
                createTitleLabel(song),
                resultList,
                createCloseButton(stage)
        );

        stage.setScene(new Scene(root, 500, 380));
        stage.setTitle("Recommended titles");
        stage.show();
    }

    private Spinner<Integer> createSpinner() {
        Spinner<Integer> spinner = new Spinner<>(1, 50, 5);
        spinner.setEditable(true);
        return spinner;
    }

    private HBox createSpinnerBox(Spinner<Integer> spinner) {
        HBox box = new HBox(10, new Label("Number of recommendations:"), spinner);
        box.setPadding(new Insets(0, 0, 5, 0));
        return box;
    }

    private Label createTitleLabel(String song) {
        Label label = new Label("If you liked \"" + song + "\" you might like");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 13;");
        return label;
    }

    private Button createCloseButton(Stage stage) {
        Button button = new Button("Close");
        button.setOnAction(e -> stage.close());
        return button;
    }

    private void setupUpdater(Spinner<Integer> spinner, ListView<String> resultList,
                              String song, boolean byFeatures, boolean useEuclidean) {
        RecommendationController controller = new RecommendationController();
        Runnable update = () -> {
            try {
                var recs = controller.recommend(song, byFeatures, useEuclidean, spinner.getValue());
                resultList.setItems(FXCollections.observableArrayList(recs));
            } catch (Exception ex) {
                resultList.setItems(FXCollections.observableArrayList("Error: " + ex.getMessage()));
            }
        };
        spinner.valueProperty().addListener((obs, oldVal, newVal) -> update.run());
        update.run();
    }
}