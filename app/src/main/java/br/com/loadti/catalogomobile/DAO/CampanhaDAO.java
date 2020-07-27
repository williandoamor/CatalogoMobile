package br.com.loadti.catalogomobile.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLClientInfoException;
import java.sql.SQLDataException;
import java.util.ArrayList;

import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ItensCampanhaSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.DB;
import br.com.loadti.catalogomobile.utilis.DatabaseManager;

/**
 * Created by TI on 08/01/2016.
 */
public class CampanhaDAO {

    // private DB bd;
    // private SQLiteDatabase bancoDados;
    private Context context;
    private CampanhaSerial camp;
    private SQLiteDatabase bancoDados;

    public CampanhaDAO(Context context, CampanhaSerial campanha) {

        this.context = context;
        //this.bd = new DB(context);
        //this.bancoDados = bd.getWritableDatabase();
        this.camp = campanha;

        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(context);
        bancoDados = DatabaseManager.getInstance().openDatabase();
    }

    /*Construtor criado para pesquisa de campanha*/
    public CampanhaDAO(Context context) {

        this.context = context;
        //this.bd = new DB(context);
        //this.bancoDados = bd.getWritableDatabase();
        //bancoDados = DatabaseManager.getInstance();
        //bancoDados.setDatabaseHelper(context);
        // bd = bancoDados.openDatabase();
        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(context);
        bancoDados = DatabaseManager.getInstance().openDatabase();

    }

    public long salvarCampanha() throws SQLDataException {

        ContentValues c;

        try {

            bancoDados.beginTransaction();

            /*Cria uma nova instancia de contentValue*/
            c = new ContentValues();

            c.put("NOME_CAMPANHA", camp.getNomeCampanha());
            c.put("SITU_CAMPANHA", camp.getSituacao());


            /*Grava a campanha no banco*/
            long result = bancoDados.insertOrThrow("CAMPANHA", null, c);

            /*Cria o DAO para salvar os itens da campanha*/
            ItensCampanhaDAO daoItens = new ItensCampanhaDAO(context, camp.getItensCampanha(), bancoDados, camp, result);
            daoItens.salvarItens();

            bancoDados.setTransactionSuccessful();

            return result;

        } catch (Exception e) {

            //bancoDados.endTransaction();
            throw new SQLDataException("Erro ao gravar campanha " + e.toString());

        } finally {

            //if (bancoDados != null) {

            //    if (bancoDados.inTransaction()) {
//
            // bancoDados.endTransaction();
            //    }

                /*Fecha o banco*/
            //  AndroidUtils.fechaBanco(bancoDados);
            //    bd.close();
            DatabaseManager.getInstance().closeDatabase();


        }


    }

    /*Pesquisa campanha*/
    public ArrayList<CampanhaSerial> buscaCampanhaConsulta(String nomeCampanha, int tipo, int idcompanha) {

        String sql = "select * from campanha";

        try {
            /*Pesquisa a campanha pelo codigo*/
            if (tipo == 0) {

                sql += " where id_campanha = " + idcompanha;

             /*Pesquisa a campanha pelo nome*/
            } else if (tipo == 1) {

                sql += " where upper (nome_campanha) like '" + nomeCampanha + "'";

            }


            return cursor(bancoDados.rawQuery(sql, null));

        } catch (Exception e) {

            throw e;
        } finally {

            //if (bancoDados != null) {

            //      bancoDados.close();
            //      bd.close();
            //  }

            DatabaseManager.getInstance().closeDatabase();
        }

    }

    /*retorna pesquisa de campanha para visualizar a consulta de campanha*/
    private ArrayList<CampanhaSerial> cursor(Cursor c) {

        Log.d("LoadMobile - BuscaPed", "cursorForList");
        Log.d("LoadMobile - BuscaPed", "cursorForList - Qtde Registros: "
                + c.getCount());

        ArrayList<CampanhaSerial> aCampanha = new ArrayList<CampanhaSerial>();

        try {

            if (c.moveToFirst()) {

                do {

                    CampanhaSerial campanha = new CampanhaSerial();
                    ArrayList<ItensCampanhaSerial> aItens = new ArrayList<>();

                    campanha.setIdcampnha(c.getInt(c.getColumnIndex("ID_CAMPANHA")));
                    campanha.setNomeCampanha(c.getString(c.getColumnIndex("NOME_CAMPANHA")));
                    campanha.setSituacao(c.getString(c.getColumnIndex("SITU_CAMPANHA")));

                    /*Busca os itens da campanha*/
                    ItensCampanhaDAO daoItens = new ItensCampanhaDAO(context, bancoDados, campanha);
                    aItens = daoItens.aItensCampanha();

                    campanha.setItensCampanha(aItens);


                    aCampanha.add(campanha);


                } while (c.moveToNext());

            }

            return aCampanha;
        } catch (Exception e) {

            throw e;

        } finally {

            c.close();
        }

    }

    public void updateCampanha() throws SQLDataException {

        ContentValues c;

        try {

            bancoDados.beginTransaction();

            c = new ContentValues();

            c.put("nome_campanha", camp.getNomeCampanha());
            c.put("situ_campanha", camp.getSituacao());


            bancoDados.update("campanha", c, "id_campanha = ?", new String[]{"" + camp.getIdcampnha()});

            ItensCampanhaDAO daoUpdateitens = new ItensCampanhaDAO(context, bancoDados, camp);
            daoUpdateitens.updateItens();

            bancoDados.setTransactionSuccessful();

        } catch (Exception e) {

            throw new SQLDataException("Erro ao atualizar a campanha " + e.toString());

        } finally {

            //if (bancoDados != null) {


            //   if (bancoDados.inTransaction()) {

            //       bancoDados.endTransaction();
            //    }
            // }
            DatabaseManager.getInstance().closeDatabase();
        }

    }
}
