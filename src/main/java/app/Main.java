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

    protected static GeolocationGetter geolocationApi;
    @Override
    public void start(Stage primaryStage) throws Exception{


        Cipher cipher=Cipher.getInstance("AES");
        KeyGenerator keyGen=KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey key=keyGen.generateKey();
        cipher.init(Cipher.DECRYPT_MODE,key);

        FileInputStream fileInputStream = new FileInputStream("./GeolocationGetter.bin");

        ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
        Main.geolocationApi=(GeolocationGetter) objectInputStream.readObject();

        Main.httpClient=HttpClient.newBuilder().build();
        Main.geolocationApi.getStateJson(Main.httpClient);

        objectInputStream.close();
        fileInputStream.close();

        Parent root = FXMLLoader.load(getClass().getResource("/Form.fxml"));
        primaryStage.setTitle("LocalQuiz");
        primaryStage.setScene(new Scene(root, 640, 480));
        Main.page=primaryStage;
        Main.page.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
