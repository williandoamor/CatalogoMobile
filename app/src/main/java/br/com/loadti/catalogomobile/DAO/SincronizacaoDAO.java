package br.com.loadti.catalogomobile.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLClientInfoException;
import java.sql.SQLDataException;

import br.com.loadti.catalogomobile.Serializable.SincronizacaoSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.DB;
import br.com.loadti.catalogomobile.utilis.DatabaseManager;

/**
 * Created by TI on 18/02/2016.
 */
public class SincronizacaoDAO {

    //private DB bd;
    private SQLiteDatabase bancoDados;
    private Context context;
    private SincronizacaoSerial sinc;

    /*Construtor utilizado para salvar os dados*/
    public SincronizacaoDAO(Context ctx, SincronizacaoSerial sincronizacao) {

        this.context = ctx;
        // this.bd = dbh;
        // this.bancoDados = banco;
        this.sinc = sincronizacao;

        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(context);
        bancoDados = DatabaseManager.getInstance().openDatabase();

    }

    /*Construtor utlizado para pesquisar os dados de sincronizacao*/
    public SincronizacaoDAO(Context ctx) {

        this.context = ctx;
        //this.bd = dbh;
        //this.bancoDados = banco;
        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(context);
        bancoDados = DatabaseManager.getInstance().openDatabase();

    }

    public long salvarDadosSincronizacao() throws SQLDataException {


        ContentValues c;

        try {

            /*Inicia transacao com o banco*/
            //bancoDados.beginTransaction();

            /*Cria uma nova instancia de contentValue*/
            c = new ContentValues();

            c.put("SINC_DATASINC", sinc.getDataSincronizacao());
            c.put("SINC_HORASINC", sinc.getHoraSincronizacao());

            /*grava os dados da sincronizacao no banco*/
            long result = bancoDados.insertOrThrow("SINCRONIZACAO", null, c);


            bancoDados.setTransactionSuccessful();

            return result;
        } catch (Exception e) {

            //bd.endTransaction();

            throw new SQLDataException("Erro ao gravar dados da sincronizacao " + e.toString());
        } finally {

            //if (bd != null) {

            //    if (bd.inTransaction()) {

            //        bd.endTransaction();
            //    }

                /*Fecha o banco*/
            //AndroidUtils.fechaBanco(bancoDados);
            ///bd.close();
            DatabaseManager.getInstance().closeDatabase();

        }


    }

    /*Pesquisa dados sincronizacao*/
    public SincronizacaoSerial getDadosSincronizacao() {

        try {

            String sql = "select * from sincronizacao";
            return cursor(bancoDados.rawQuery(sql, null));

        } catch (Exception e) {

            throw e;

        } finally {


            //  if (bancoDados != null) {

            // bancoDados.close();
            //bd.close();
            //}
            DatabaseManager.getInstance().closeDatabase();

        }


    }


    /*Transforma os itens vendo do banco em um cursor*/
    private SincronizacaoSerial cursor(Cursor c) {
        Log.d("LoadMobile - BuscaPed", "cursorForList");
        Log.d("LoadMobile - BuscaPed", "cursorForList - Qtde Registros: "
                + c.getCount());


        SincronizacaoSerial sincronizacaoSerial = new SincronizacaoSerial();

        try {

            if (c.moveToFirst()) {

                do {


                    sincronizacaoSerial.setIdsincronizacao(c.getInt(c.getColumnIndex("ID_SINC")));
                    sincronizacaoSerial.setDataSincronizacao(c.getString(c.getColumnIndex("SINC_DATASINC")));
                    sincronizacaoSerial.setHoraSincronizacao(c.getString(c.getColumnIndex("SINC_HORASINC")));


                } while (c.moveToNext());

            }

            return sincronizacaoSerial;
        } catch (Exception e) {

            throw e;

        } finally {

            c.close();

        }


    }


    public void updateDadosSincronizacao() throws SQLDataException {

        ContentValues c;

        try {

            bancoDados.beginTransaction();

            c = new ContentValues();

            c.put("SINC_DATASINC", sinc.getDataSincronizacao());
            c.put("SINC_HORASINC", sinc.getHoraSincronizacao());

            bancoDados.update("sincronizacao", c, "id_sinc = ?", new String[]{"" + sinc.getIdsincronizacao()});


           // bancoDados.setTransactionSuccessful();
        } catch (Exception e) {

            throw new SQLDataException("Erro ao atualizar dados de sincronizacao " + e.toString());

        } finally {

            // if (bancoDados != null) {


            //  if (bancoDados.inTransaction()) {

            //    bancoDados.endTransaction();
            // }
            // }
            //DatabaseManager.getInstance().closeDatabase();
        }


    }
}
