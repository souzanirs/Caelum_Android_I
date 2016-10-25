package br.com.caelum.cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.classes.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView lista;
    private List<Aluno> listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        //Pega a ListView no arquivo XML
        this.lista = (ListView) findViewById(R.id.lista_alunos);
        carregaLista();

        //Escutando um click simples
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Aluno aluno = (Aluno) adapterView.getItemAtPosition(i);
                Toast.makeText(ListaAlunosActivity.this,"Clique simples "+aluno.getNome(),Toast.LENGTH_SHORT).show();
            }
        });

        //Escutando um click longo
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListaAlunosActivity.this,"Clique Longo no item "+(i+1),Toast.LENGTH_SHORT).show();

                /*
                *No caso do click longo é possivel executar, após a execução do mesmo,
                * a ação do click simples, por isso é necessário um retorno boleano
                * "TRUE" executa apenas a ação definida na ação do click longo
                * "FALSE" executa a ação definida no click longo e posteriormente
                * a ação do click simples
                */

                return true;
            }
        });

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

        //Para preenchermos a lista precisamos adapta-la a um layout, carregando as informações passadas (no nosso caso os nomes)
        final ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, listaAlunos);
        lista.setAdapter(adapter);
    }
}
