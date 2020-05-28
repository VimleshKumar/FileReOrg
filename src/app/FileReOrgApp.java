package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FileReOrgApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Label label = new Label();
        label.setText("Hello World!");
        pane.getChildren().add(label);
        Scene scene = new Scene(pane, 300,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FileReOrg");
        primaryStage.show();
    }
}
