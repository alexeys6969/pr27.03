package com.example.maps;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.io.IOException;

public class GetAddressByGPS extends AsyncTask<Void, Void, Void> {
    TextView textAddress;
    String coordinats;
    String token = "fa57033a-e8c1-4cdc-84e0-399dd7e1b281";
    AddressResponse Response = null;
    public GetAddressByGPS(String coordinats, TextView TextAddress) {
        this.coordinats = coordinats;
        textAddress = TextAddress;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try{
            Document document = Jsoup.connect("https://geocode-maps.yandex.ru/v1/?apikey=" + token + "&geocode=" + coordinats + "&format=json")
                    .ignoreContentType(true)
                    .get();
            GsonBuilder builder = new GsonBuilder();
            Response = builder.create().fromJson(document.text(), AddressResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void voids) {
        textAddress.setText(Response.response.GeoObjectCollection.featureMember.get(0).GeoObject.metaDataProperty.GeocoderMetaData.text);
    }
}
