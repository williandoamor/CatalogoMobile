package br.com.loadti.catalogomobile.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Serializable.PerfilUserSerial;
import br.com.loadti.catalogomobile.utilis.DB;
import br.com.loadti.catalogomobile.utilis.DatabaseManager;

/**
 * Created by TI on 31/01/2016.
 */
public class PerfilDAO {

    // DB dbhelp;
    //  SQLiteDatabase bancoDados;

    //rivate DatabaseManager bancoDados;
    private SQLiteDatabase bancoDados;

    /*Construtor para salvar o perfil*/
    public PerfilDAO(Context context) {

        //dbhelp = new DB(context);
        //bancoDados = dbhelp.getWritableDatabase();
        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(context);
        bancoDados = DatabaseManager.getInstance().openDatabase();
    }

    public ArrayList<PerfilUserSerial> buscarPerfil(int tipo, String nomePerfil, int idPerfil) {

        String sql = "select * from perfil p";

        try {
            if (tipo == 0) {

                sql += " where upper(p.nome_per) like '" + nomePerfil + "' ";

            } else if (tipo == 1) {

                sql += " where p.id_perfil = " + idPerfil;
            }


            return cursorForList(bancoDados.rawQuery(sql, null));

        } catch (Exception e) {

            Log.e("LoadTI - BuscarPerfil", "Erro em \"busca\": " + e.toString());

            throw e;
        } finally {

            /// if (bancoDados != null) {


            //   bancoDados.close();
            //   dbhelp.close();
            //}
            DatabaseManager.getInstance().closeDatabase();
        }


    }

    /*
    * Converter o cursor para um ArrayList
    */
    private ArrayList<PerfilUserSerial> cursorForList(Cursor c) {


        Log.d("LoadTI-PerfilDao", "cursorForList");
        Log.d("LoadTI-PerfilDao", "cursorForList - Qtde Registros: " + c.getCount());

        ArrayList<PerfilUserSerial> aPerfil = new ArrayList<PerfilUserSerial>();

        try {

            if (c.moveToFirst()) {

                do {

                    PerfilUserSerial perfil = new PerfilUserSerial();

                    perfil.setId_perfilUser(c.getInt(c.getColumnIndex("ID_PERFIL")));
                    perfil.setNomePerfil(c.getString(c.getColumnIndex("NOME_PER")));
                    perfil.setAcessoPerfil(c.getString(c.getColumnIndex("ACESSO_PER")));
                    perfil.setAlterarPerfil(c.getString(c.getColumnIndex("ALTERAR_PER")));
                    perfil.setStatusPerfil(c.getString(c.getColumnIndex("STATUS_PER")));

                    aPerfil.add(perfil);

                } while (c.moveToNext());

            }

            return aPerfil;

        } catch (Exception e) {

            Log.e("LoadTI-PefilDao", "Erro em \"cursorForList\": " + e.toString());

            throw e;

        } finally {
            if (!c.isClosed()) {
                c.close();
            }
        }

    }


}
