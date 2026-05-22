package com.example.maps;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class MainActivity extends AppCompatActivity {
    LocationManager _LocationManager;
    TextView textAddress;
    MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    LocationListener _locationListner = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if(location != null) {
                mapView.getMap().move(
                        new CameraPosition(
                                new Point(location.getLatitude(), location.getLongitude()), 15, 0,0));
                mapView.getMap().getMapObjects().clear();
                mapView.getMap().getMapObjects().addPlacemark(
                        new Point(location.getLatitude(), location.getLongitude()),
                        ImageProvider.fromResource(location.getLongitude()) + "," + String.valueOf(location.getLatitude()),
                        textAddress
                );
                getAddressByGPS.execute();
            }
        }
    };
}