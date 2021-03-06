package br.com.caelum.cadastro;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.sax.StartElementListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.adapter.ListaAlunoAdapter;
import br.com.caelum.cadastro.classes.Aluno;
import br.com.caelum.cadastro.classes.Permissao;
import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.support.WebClient;
import br.com.caelum.cadastro.task.EnviaAlunosTask;

import static android.content.Intent.ACTION_CALL;
import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_SENDTO;
import static android.content.Intent.ACTION_VIEW;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView lista;
    private List<Aluno> listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Permissao.fazPermissao(this);

        //Pega a ListView no arquivo XML
        this.lista = (ListView) findViewById(R.id.lista_alunos);
        carregaLista();

        //Escutando um click simples
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Aluno alunoSelecionado = (Aluno) adapterView.getItemAtPosition(i);
                Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                edicao.putExtra("aluno", alunoSelecionado);
                startActivity(edicao);
            }
        });

        //Escutando um click longo
//        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(ListaAlunosActivity.this,"Clique Longo no item "+(i+1),Toast.LENGTH_SHORT).show();
//
//                /*
//                *No caso do click longo é possivel executar, após a execução do mesmo,
//                * a ação do click simples, por isso é necessário um retorno boleano
//                * "TRUE" executa apenas a ação definida na ação do click longo
//                * "FALSE" executa a ação definida no click longo e posteriormente
//                * a ação do click simples
//                */
//
//                return true;
//            }
//        });

        registerForContextMenu(lista);

        Button btnAdicionar = (Button) findViewById(R.id.lista_alunos_novo);

        //Escutando o clique no botão
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(ListaAlunosActivity.this,"Botão adicionar clicado",Toast.LENGTH_SHORT).show();*/

                //Para carregarmos uma nova Activity devemos criar uma intenção, e informa-la
                Intent viewCadastro = new Intent(ListaAlunosActivity.this,FormularioActivity.class);

                //Pedimos ao Android para carregar a nova Activity
                startActivity(viewCadastro);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.carregaLista();
    }

    private void carregaLista() {
        //Instanciando o alunoDAO e extraindo a lista de Alunos da base de dados
        AlunoDAO dao = new AlunoDAO(this);
        listaAlunos = dao.getLista();

        //sempre que abrir conexão, após utiliza-lo, fechar a conexão
        dao.close();

        /* Removemos o antigo Adapter, agora utilizaremos nosso Adapter "ListaAlunoAdapter"" */


        ListaAlunoAdapter adapter = new ListaAlunoAdapter(this, listaAlunos);
        lista.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) lista.getAdapter().getItem( info.position);

        MenuItem ligar = menu.add("Ligar");
        MenuItem SMS = menu.add("Enviar SMS");
        MenuItem mapa = menu.add("Localizar no Mapa");
        MenuItem site = menu.add("Acessar Home");
        MenuItem deletar = menu.add("Remover");
        MenuItem compartiplhar = menu.add("Compartilhar");

        deletar.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new AlertDialog.Builder(ListaAlunosActivity.this).
                        setIcon(android.R.drawable.ic_dialog_alert).
                        setTitle("Deletar").
                        setMessage("Deseja realmente deletar o registro?").
                        setPositiveButton( "Quero", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                                dao.deletar(alunoSelecionado);
                                dao.close();
                                carregaLista();
                            }
                        } ).setNegativeButton("Não",null).show();


                return false;
            }
        } );

        //Opção Ligar
        Intent intentLigar = new Intent(ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:19991940603"));
        ligar.setIntent(intentLigar);

        //Opção enviar SMS
        Intent intentSMS = new Intent(ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:"+alunoSelecionado.getTelefone()));
        intentSMS.putExtra("sms_body","Digite sua mensagem");
        SMS.setIntent(intentSMS);

        //Abrir endereço do aluno no mapa
        Intent intentMapa = new Intent(ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?z=14&q="+alunoSelecionado.getEndereco()));
        mapa.setIntent(intentMapa);

        //Enviar email para o aluno
        Intent intentSite = new Intent(ACTION_VIEW);
        String alunoSite = alunoSelecionado.getSite();

        if(!alunoSite.startsWith("http://")){
            alunoSite = "http://"+alunoSite;
        }
        intentSite.setData(Uri.parse(alunoSite));
        site.setIntent(intentSite);

        compartiplhar.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intentCompartilhar = new Intent(ACTION_SEND);
                intentCompartilhar.setType("text/plain");
                intentCompartilhar.putExtra(intentCompartilhar.EXTRA_SUBJECT,"assunto compartilhado");
                intentCompartilhar.putExtra(intentCompartilhar.EXTRA_TEXT,"texto compartilhado");
                startActivity(intentCompartilhar.createChooser(intentCompartilhar,"Escolha a aplicação"));
                return false;
            }
        } );
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.menu_enviar_notas:

                new EnviaAlunosTask(this).execute();
                return true;

            case R.id.menu_receber_provas:

                Intent provas = new Intent(this, ProvasActivity.class);
                startActivity(provas);
                return true;

            case R.id.menu_mapa:

                Intent mapa = new Intent(this, MostraAlunosActivity.class);
                startActivity(mapa);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}