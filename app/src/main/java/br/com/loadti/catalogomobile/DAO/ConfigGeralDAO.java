package br.com.loadti.catalogomobile.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLDataException;
import java.util.ArrayList;

import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.utilis.DB;
import br.com.loadti.catalogomobile.utilis.DatabaseManager;


/**
 * Created by TI on 22/04/2015.
 */
public class ConfigGeralDAO {

    //private DB bd;
    // private SQLiteDatabase banco;
    private Context ctx;
    private ConfigGeralSerial config;
    //private DatabaseManager bd;
    private SQLiteDatabase bancoDados;


    public ConfigGeralDAO(Context context, ConfigGeralSerial conf) {

        this.ctx = context;
        ///this.bd = dbh;
        //this.banco = bancoDados;
        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(context);
        bancoDados = DatabaseManager.getInstance().openDatabase();

        // this.bd = manager;
        this.config = conf;

    }

    /*Construtor para pesquisa*/
    public ConfigGeralDAO(Context ctx) {

        this.ctx = ctx;
        //this.bd = new DB(ctx);
        //this.bancoDados = bd.getWritableDatabase();
        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(ctx);
        bancoDados = DatabaseManager.getInstance().openDatabase();
    }


    public ArrayList<ConfigGeralSerial> pesqConfigGeral() {

        String sql;

        try {

            sql = " select * from confgeral";
            return cursorList(bancoDados.rawQuery(sql, null));
        } catch (Exception e) {

            throw e;
        } finally {


            DatabaseManager.getInstance().closeDatabase();

        }

    }


    private ArrayList<ConfigGeralSerial> cursorList(Cursor c) {

        ArrayList<ConfigGeralSerial> aconfig = new ArrayList<ConfigGeralSerial>();

        try {

            if (c.moveToFirst()) {

                do {

                    ConfigGeralSerial config = new ConfigGeralSerial();

                    config.setId_configGeral(c.getInt(c.getColumnIndex("ID_CONFGERAL")));
                    config.setPortaHost(c.getInt(c.getColumnIndex("CONFGERAL_PORTA")));
                    config.setHost(c.getString(c.getColumnIndex("CONFGERAL_HOST")));
                    config.setMostrarPreco(c.getString(c.getColumnIndex("CONFGERAL_MOSTRARPRECO")));
                    config.setTabelaPadrao(c.getShort(c.getColumnIndex("CONFIGERAL_TABVENDAPRADAO")));
                    config.setCasaDecimalVenda(c.getInt(c.getColumnIndex("CONFGERAL_CASADECVENDA")));
                    config.setVisualizacao(c.getInt(c.getColumnIndex("CONFGERAL_VISUALIZAR")));
                    config.setCodCampanhaPadrao(c.getInt(c.getColumnIndex("CONFGERAL_ID_CAMPANHA_PADRAO")));
                    config.setCodTipoVisualizacao(c.getInt(c.getColumnIndex("CONFGERAL_TIPOVISUALIZACAO")));


                    Log.d("PesquisaConfigGeral", "Mostrar preço " + config.getMostrarPreco());

                    aconfig.add(config);

                } while (c.moveToNext());

            }

            return aconfig;

        } catch (Exception e) {

            throw e;

        } finally {


            c.close();
        }


    }

    public void updateConfigGeral() throws SQLDataException {

        ContentValues c;

        try {

            c = new ContentValues();
            c.put("CONFGERAL_PORTA", config.getPortaHost());
            c.put("CONFGERAL_HOST", config.getHost());
            c.put("CONFIGERAL_TABVENDAPRADAO", config.getTabelaPadrao());
            c.put("CONFGERAL_MOSTRARPRECO ", config.getMostrarPreco());
            c.put("CONFGERAL_CASADECVENDA", config.getCasaDecimalVenda());
            c.put("CONFGERAL_VISUALIZAR", config.getVisualizacao());
            c.put("CONFGERAL_ID_CAMPANHA_PADRAO", config.getCodCampanhaPadrao());
            c.put("CONFGERAL_TIPOVISUALIZACAO", config.getCodTipoVisualizacao());


               /*Chama o metodo updata do banco de dados para atualizar as informacoes na tabela*/
            bancoDados.update("confgeral", c, "id_confgeral = ?", new String[]{"" + config.getId_configGeral()});


        } catch (Exception e) {

            throw e;

        } finally {

            DatabaseManager.getInstance().closeDatabase();


        }
    }

    public void SalvarConfiguracao() throws SQLDataException {


        try {

            bancoDados.beginTransaction();

            /*Salva a configuracao geral*/
            ConfigGeralDAO daoGeral = new ConfigGeralDAO(ctx, config);
            daoGeral.updateConfigGeral();
            bancoDados.setTransactionSuccessful();


        } catch (Exception e) {

            throw new SQLDataException("Erro ao atualizar configuracao " + e.toString());


        } finally {


            // if (bancoDados.inTransaction()) {

            //     bancoDados.endTransaction();

            //}
            DatabaseManager.getInstance().closeDatabase();
        }

       /*Se o banco na for nullo, fecha o banco*/
        // if (bancoDados != null) {

        //      bancoDados.close();
        //      bd.close();
        //  }


    }

}
