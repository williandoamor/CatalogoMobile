package br.com.loadti.catalogomobile.utilis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by TI on 16/10/2015.
 */
public class DB extends SQLiteOpenHelper {


    private static String NOME_BANCO = "CatalogoMobile";
    private static int VERSAO_BANCO = 1;
    public static SQLiteDatabase bancoDados;


    public DB(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("Catalogo - DBHelper","Metodo onCreate");
        try {
            // Instancia a classe tabela e cria um objeto dessa classe
            CriaTabelas tab = new CriaTabelas();

            // cria uma variavel int i e verifica, enquanto i for menor que o
            // metodo para cariar
            // as tabelas da classe cria_tabelas cria tabelas e incrementa a
            // variavel i

            for (int i = 0; i < tab.pegaTabelas().size(); i++) {

                db.execSQL(tab.pegaTabelas().get(i));

            }

            Log.d("LoadTI - DBHelper",
                    "Metodo onCreate. As tabelas foram criadas com sucesso");



        }catch (Exception e) {

            Log.e("LoadTI - DBHelper", "Metodo onCreate. Falha " + e.toString()
                    + " ao criar as tabelas do banco", e);

            throw e;

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
