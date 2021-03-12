package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage page;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Form.fxml"));
        primaryStage.setTitle("LocalQuiz");
        primaryStage.setScene(new Scene(root, 640, 480));
        Main.page=primaryStage;
        Main.page.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
