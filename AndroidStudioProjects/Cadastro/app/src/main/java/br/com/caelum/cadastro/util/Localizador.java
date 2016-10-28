package br.com.caelum.cadastro.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by android6275 on 28/10/16.
 */

public class Localizador {

    private Geocoder geo;

    public Localizador(Context context){
        geo = new Geocoder( context );
    }

    public LatLng getCoordenada(String endereco){

        try {
            List<Address> enderecos = geo.getFromLocationName( endereco, 1 );
            if(enderecos != null){
                Address end = enderecos.get( 0 );
                Double latitude = end.getLatitude();
                Double longitude = end.getLongitude();
                LatLng latLng = new LatLng( latitude, longitude );
                return  latLng;

            } else {
                return  null;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
