package app;
import app.util.GeolocationGetter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.crypto.*;
import java.io.*;
import java.net.http.HttpClient;

public class Main extends Application {

    protected static Stage page;
    protected static Quiz quiz;
    protected static HttpClient httpClient;
    protected static String jsonData;

    protected static GeolocationGetter geolocationApi;
    protected static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FileInputStream fileInputStream = new FileInputStream("./GeolocationGetter.bin");

        ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
        Main.geolocationApi=(GeolocationGetter) objectInputStream.readObject();

        Main.httpClient=HttpClient.newBuilder().build();
        Main.jsonData=Main.geolocationApi.getStateJson(Main.httpClient);
        System.out.println(Main.jsonData);

        /*   位置情報を取得するプログラムはできたが、Jsonからほしい値を探すコードがまだできていないのでそれはいつか気が向いたらで...
        Quiz.areaCode=Quiz.mapper.readTree(Main.jsonData).path("/ResultSet/Govcode").asText();
        System.out.println(Quiz.areaCode);
        */

        objectInputStream.close();
        fileInputStream.close();

        Parent root = FXMLLoader.load(getClass().getResource("/Form.fxml"));
        Main.scene=new Scene(root, 640, 480);
        primaryStage.setTitle("LocalQuiz");
        primaryStage.setScene(Main.scene);
        Main.page=primaryStage;
        Main.page.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
