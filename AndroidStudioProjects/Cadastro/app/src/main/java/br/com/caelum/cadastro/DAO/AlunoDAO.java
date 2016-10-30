package br.com.caelum.cadastro.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.classes.Aluno;

/**
 * Created by android6275 on 25/10/16.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    private static final String nomeBanco = "CadastroCaelum";
    private static final int versao = 1;
    private static final String tabela = "alunos";

    public AlunoDAO(Context context) {
        super(context,nomeBanco, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table "+tabela+"(" +
                        "id integer primary key," +
                        "nome text not null," +
                        "telefone text," +
                        "endereco text," +
                        "email text," +
                        "nota Real," +
                        "caminhofoto text);";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {

        String sql = "DROP TABLE IF EXISTS "+tabela+";";
        //String sql = "ALTER TABLE "+tabela+" ADD COLUMN caminhofoto TEXT;"; //Alteramos no exercício 7.6
        sqLiteDatabase.execSQL(sql);
        //onCreate(sqLiteDatabase); //Removido no exercício 7.6

        /*
        * Uma opção para que não se perca as informações inseridas no banco
        * com a execução do DROP TABLE, podemos utilizar um SWITCH verificando
        * a versão atual do sistema e aplicando as atualizações da base deste versão
        * em diante, utilizando os cases sem "BRAKE", sendo assim todos os cases após
        * a versão atual serão executados
        */

    }

    public void insereAlunoDB(Aluno aluno){

        /*
           Extraimos as ações de carregar os dados para um método,
           pois utilizaremos a mesma ação no "insere" e no "altera"
        */
        ContentValues valores = getContentValuesAluno( aluno );
        getWritableDatabase().insert(tabela,null, valores);
    }

    //Nosso Metodo para pegar as informaçoes do aluno
    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues valores = new ContentValues();
        valores.put("nome",aluno.getNome());
        valores.put("telefone",aluno.getTelefone());
        valores.put("email",aluno.getSite());
        valores.put("endereco",aluno.getEndereco());
        valores.put("nota",aluno.getNota());
        valores.put("caminhofoto",aluno.getCaminhoFoto());
        return valores;
    }

    public List<Aluno> getLista(){
        List<Aluno> lista = new ArrayList<Aluno>();

        //Executa a consulta de todos os dados da tabela e gera um arquivo como um bloco de notas
        Cursor c = getReadableDatabase().rawQuery("select * from "+tabela+";",null);

        //percore nosso bloco de texto e enquanto houver nova linha executa
        while(c.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("email")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhofoto")));

            lista.add(aluno);
        }
        c.close(); //Lembra-se de sempre fechar
        return lista;
    }

    public void deletar(Aluno a){
        String[] args = {String.valueOf(a.getId())};
        getWritableDatabase().delete("alunos", "id=?", args);
    }

    public void alteraAluno(Aluno aluno){
        ContentValues valores = getContentValuesAluno(aluno);
        String[] idAluno = {String.valueOf(aluno.getId())};
        getWritableDatabase().update("alunos",valores,"id = ?",idAluno);
    }

    public boolean isAluno(String telefone){

        String[] parametros = {telefone};

        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT telefone FROM "+tabela+" WHERE TELEFONE=?", parametros);

        int total = rawQuery.getCount();
        rawQuery.close();

        return total > 0;
    }
}