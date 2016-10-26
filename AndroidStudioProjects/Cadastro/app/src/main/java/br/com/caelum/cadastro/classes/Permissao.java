package br.com.caelum.cadastro.classes;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;

/**
 * Created by android6275 on 26/10/16.
 */

public class Permissao {

    private static final int CODE = 123;
    private static ArrayList<String> listaPermissoes = new ArrayList<>();

    public static void fazPermissao(Activity activity){

        String[] permissoes = {
                Manifest.permission.CALL_PHONE,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.INTERNET
        };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            for(String permissao : permissoes){
                if(activity.checkSelfPermission(permissao) != PackageManager.PERMISSION_GRANTED){
                    listaPermissoes.add(permissao);
                }
            }
            request(activity);
        }


    }

    private static void request(Activity activity) {
        String[] array = listaPermissoes.toArray(new String[]{});

        if(listaPermissoes.size() > 0){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                activity.requestPermissions(array,CODE);
            }
        }
    }

}
