package app;

import app.util.GeolocationGetter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.crypto.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main extends Application {

    protected static Stage page;
    protected static Quiz quiz;

    protected static GeolocationGetter geolocationApi;
    @Override
    public void start(Stage primaryStage) throws Exception{


        Cipher cipher=Cipher.getInstance("AES");
        KeyGenerator keyGen=KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey key=keyGen.generateKey();
        cipher.init(Cipher.ENCRYPT_MODE,key);

        FileInputStream fileInputStream=new FileInputStream(getClass().getResource("/GeolocationGetter.bin").toURI().toString());;
        CipherInputStream cipherInputStream=new CipherInputStream(fileInputStream,cipher);

        ObjectInputStream objectInputStream=new ObjectInputStream(cipherInputStream);
        Main.geolocationApi=(GeolocationGetter) objectInputStream.readObject();

        objectInputStream.close();
        cipherInputStream.close();
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
