package br.com.caelum.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.R;

/**
 * Created by android6275 on 27/10/16.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Pegamos todas as mensagens recebidas
        Object[] mensagens = (Object[]) intent.getSerializableExtra("pdus");

        //Pegamos apenas a mensagem inicial (0)
        byte[] mensagem = (byte[]) mensagens[0];

        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage sms = SmsMessage.createFromPdu(mensagem,formato);

        //Pegamos o telefone de quem enviou a mensagem
        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDAO alunoDAO = new AlunoDAO(context);

        //Validamos se o telefone está cadastrado na aplicação
        if(alunoDAO.isAluno(telefone)){

            //Atribuimos um som para quando recebermos a mensagem de um aluno
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();

            Toast.makeText(context,"Chegou SMS de "+telefone,Toast.LENGTH_LONG).show();
        };
    }

}
