package nori.app.sample;

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

            Cipher cipher=Cipher.getInstance("AES");
            KeyGenerator keyGen=KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey key=keyGen.generateKey();
            cipher.init(Cipher.ENCRYPT_MODE,key);

            FileOutputStream fileOutputStream=new FileOutputStream("./GeolocationGetter.bin");
            CipherOutputStream cipherOutputStream=new CipherOutputStream(fileOutputStream,cipher);

            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(api);

            objectOutputStream.close();
            cipherOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}