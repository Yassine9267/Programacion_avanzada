package app;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.Label;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainApp extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        Label label=new Label("Pulsa uno de los botones");
        Button botonHola=new Button("Hola");
        Button botonAdios=new Button("Adios");

        botonHola.setOnAction(event -> abrirNuevaVentana());
        botonAdios.setOnAction((event -> System.out.println("Adios Pulsado")));
        VBox vbox=new VBox(label, botonHola, botonAdios);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 640, 480);


        stage.setScene(scene);
        stage.show();


    }

    private void abrirNuevaVentana(){
        Stage newStage=new Stage();
        Pane pane=new Pane();
        Label label=new Label("nueva ventana");
        label.setLayoutX(180);
        label.setLayoutY(180);
        pane.getChildren().addAll(label);
        Scene scene=new Scene(pane, 400, 400);
        newStage.setScene(scene);
        newStage.show();
    }
}