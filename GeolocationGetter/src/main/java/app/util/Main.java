package app.util;

import javax.crypto.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.http.HttpClient;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) {
        GeolocationGetter api = new GeolocationGetter(args[0]);
        HttpClient client= HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

        try {
            String stateJson=api.getStateJson(client);
            //暗号化して位置情報取得プログラムをシリアライズする。

            FileOutputStream fileOutputStream=new FileOutputStream("./GeolocationGetter.bin");

            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(api);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}