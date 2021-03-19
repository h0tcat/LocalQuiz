package app.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeolocationGetter implements Serializable {

    private String appID="";

    public GeolocationGetter(String apiKey){
        this.appID=apiKey;
    }


    private String[] getLatitudeAndLongitude(HttpClient client) throws IOException, InterruptedException {
        String publicIPAddress=client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create("https://ifconfig.io/ip"))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        ).body();

        //改行コードを取り除く(そうしないとエラー。
        publicIPAddress=publicIPAddress.substring(0,publicIPAddress.length()-1);
        String locationMapURI=String.format("https://ipwhois.app/json/%s",publicIPAddress);
        HttpResponse<String> clientResponse = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(locationMapURI))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );



        String body=clientResponse.body();
        String[] resultLatitudeAndLongitude=new String[2];

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root=mapper.readTree(body);
        resultLatitudeAndLongitude[0]=root.get("latitude").toString();
        resultLatitudeAndLongitude[1]=root.get("longitude").toString();
        for(int i=0;i<2;i++)
            resultLatitudeAndLongitude[i]=resultLatitudeAndLongitude[i].replace("\"","");
        return resultLatitudeAndLongitude;
    }

    public String getStateJson(HttpClient client) throws IOException, InterruptedException {
        String[] latudeAndLongitude=this.getLatitudeAndLongitude(client);

        String uriFormat=String.format("https://map.yahooapis.jp/placeinfo/V1/get?appid=%s&lat=%s&lon=%s&output=json",
                this.appID,
                latudeAndLongitude[0],
                latudeAndLongitude[1]);
        HttpResponse<String> httpResponse=client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(uriFormat))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
        return httpResponse.body();
    }

}