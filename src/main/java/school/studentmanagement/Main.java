package school.studentmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private double x, y;
    public  Stage window;
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        window.setScene(new Scene(root));
        window.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(e ->{
            x = e.getSceneX();
            y = e.getSceneY();
        });

        root.setOnMouseDragged(e -> {
            window.setX(e.getScreenX() - x);
            window.setY(e.getScreenY() - y);
        });
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}