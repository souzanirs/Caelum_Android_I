package br.com.caelum.cadastro.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.contextmanager.ContextData;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import br.com.caelum.cadastro.fragment.MapaFragment;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

/**
 * Created by solzanir on 29/10/16.
 */

public class AtualizadorDeLocalizacao implements com.google.android.gms.location.LocationListener {

    private MapaFragment mapaFragment;
    private GoogleApiClient client;
    private GoogleMap googleMap;

    public AtualizadorDeLocalizacao(Context context, MapaFragment mapaFragment, GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.mapaFragment = mapaFragment;

        Configurador configurador = new Configurador(this);

        this.client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(configurador)
                .build();

        this.client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng local = new LatLng(latitude, longitude);

        this.mapaFragment.centralizaNo(local, googleMap);
    }

    public void inicia(LocationRequest request) {
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    public void cancela(){
        LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        this.client.disconnect();
    }
}
