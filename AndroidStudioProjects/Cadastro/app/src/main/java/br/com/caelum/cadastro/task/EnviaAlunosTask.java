package br.com.caelum.cadastro.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.classes.Aluno;
import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.support.WebClient;

/**
 * Created by android6275 on 27/10/16.
 */

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private ProgressDialog progress;

    public EnviaAlunosTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... objects) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        String json = new AlunoConverter().toJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resp) {
        progress.dismiss();
        Toast.makeText(context,"Media: "+resp,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(context, "Aguardar...", "Enviando dados para a web", false, true);

    }
}
