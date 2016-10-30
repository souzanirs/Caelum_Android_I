package br.com.caelum.cadastro.location;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by solzanir on 29/10/16.
 */

public class Configurador implements GoogleApiClient.ConnectionCallbacks {


    private AtualizadorDeLocalizacao atualizador;

    public Configurador(AtualizadorDeLocalizacao atualizador){
        this.atualizador = atualizador;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = LocationRequest.create();
        request.setInterval(2000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setSmallestDisplacement(50);

        atualizador.inicia(request);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
