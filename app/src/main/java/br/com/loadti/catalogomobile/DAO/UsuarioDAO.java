package br.com.loadti.catalogomobile.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.loadti.catalogomobile.Serializable.PerfilUserSerial;
import br.com.loadti.catalogomobile.Serializable.UsuarioSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.DB;
import br.com.loadti.catalogomobile.utilis.DatabaseManager;

/**
 * Created by TI on 19/03/2015.
 */
public class UsuarioDAO {

    //private DB bd;
    //private SQLiteDatabase banco;
    //private DatabaseManager bancoDados;
    private SQLiteDatabase bancoDados;
    private long result;


    public UsuarioDAO(Context context) {

        // this.bd = new DB(context);
        // this.banco = bd.getWritableDatabase();
        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(context);
        bancoDados = DatabaseManager.getInstance().openDatabase();

    }

    public UsuarioSerial usuario(String login) {

        String sql;

        try {

            sql = " select * from usuario where upper(usuario_loginuser) = '" + login + "' ";
            return cursorSerial(bancoDados.rawQuery(sql, null));


        } catch (Exception e) {

            throw e;

        } finally {


            DatabaseManager.getInstance().closeDatabase();

        }

    }

    private UsuarioSerial cursorSerial(Cursor c) {

        UsuarioSerial usuario = new UsuarioSerial();
        PerfilUserSerial perfil = new PerfilUserSerial();


        try {

            if (c.moveToFirst()) {

                do {

                    usuario.setId_usuario(c.getInt(c.getColumnIndex("ID_USUARIO")));
                    usuario.setNomeUser(c.getString(c.getColumnIndex("USUARIO_NOMEUSER")));
                    usuario.setLoginUser(c.getString(c.getColumnIndex("USUARIO_LOGINUSER")));
                    usuario.setSenhaUser(c.getString(c.getColumnIndex("USUARIO_SENHAUSER")));
                    usuario.setConfirmaSenhaUser(c.getString(c.getColumnIndex("USUARIO_CONFIRMASENHAUSER")));
                    usuario.setDataCadUser(c.getString(c.getColumnIndex("USUARIO_DATACAD")));
                    usuario.getPerfil().setId_perfilUser(c.getInt(c.getColumnIndex("ID_PERFIL")));
                    usuario.setStatusUser(c.getString(c.getColumnIndex("USUARIO_STATUSUSER")));

                    /*Busca o perfil do usuario*/
                    int id_perfil = usuario.getPerfil().getId_perfilUser();
                    String buscaPerfil = " select * from perfil where id_perfil = "
                            + id_perfil;

                    Cursor cPer = bancoDados.rawQuery(buscaPerfil, null);

                    if (cPer.moveToFirst()) {

                        do {


                            perfil.setId_perfilUser(cPer.getInt(cPer.getColumnIndex("ID_PERFIL")));
                            perfil.setAcessoPerfil(cPer.getString(cPer.getColumnIndex("ACESSO_PER")));
                            perfil.setAlterarPerfil(cPer.getString(cPer.getColumnIndex("ALTERAR_PER")));
                            perfil.setNomePerfil(cPer.getString(cPer.getColumnIndex("NOME_PER")));
                            perfil.setStatusPerfil(cPer.getString(cPer.getColumnIndex("STATUS_PER")));

                            /*Seta o perfil no objeto usuario*/
                            usuario.setPerfil(perfil);

                        } while (cPer.moveToNext());


                    }


                } while (c.moveToNext());

            }

            return usuario;
        } catch (CursorIndexOutOfBoundsException e) {

            throw e;

        } finally {

            c.close();
        }

    }

    /*Pesquisa usuairo para exibir na pesquisa*/
    public ArrayList<UsuarioSerial> pesquisaUsuario(String nomeUser) {

        String sql;

        try {

            sql = " select * from usuario where upper(usuario_nomeuser) like '" + nomeUser + "'" + "order by usuario_nomeuser";
            return cursorList(bancoDados.rawQuery(sql, null));


        } catch (Exception e) {

            throw e;

        } finally {

            // if (banco != null) {

            //    banco.close();
            //     bd.close();
            //}
            DatabaseManager.getInstance().closeDatabase();

        }

    }

    private ArrayList<UsuarioSerial> cursorList(Cursor c) {

        ArrayList<UsuarioSerial> lusuario = new ArrayList<UsuarioSerial>();


        try {

            if (c.moveToFirst()) {

                do {

                    UsuarioSerial usuario = new UsuarioSerial();

                    usuario.setId_usuario(c.getInt(c.getColumnIndex("ID_USUARIO")));
                    usuario.setNomeUser(c.getString(c.getColumnIndex("USUARIO_NOMEUSER")));
                    usuario.setLoginUser(c.getString(c.getColumnIndex("USUARIO_LOGINUSER")));
                    usuario.setSenhaUser(c.getString(c.getColumnIndex("USUARIO_SENHAUSER")));
                    usuario.setConfirmaSenhaUser(c.getString(c.getColumnIndex("USUARIO_CONFIRMASENHAUSER")));
                    usuario.setDataCadUser(c.getString(c.getColumnIndex("USUARIO_DATACAD")));
                    usuario.getPerfil().setId_perfilUser(c.getInt(c.getColumnIndex("ID_PERFIL")));
                    usuario.setStatusUser(c.getString(c.getColumnIndex("USUARIO_STATUSUSER")));
                    lusuario.add(usuario);


                } while (c.moveToNext());

            }

            return lusuario;
        } catch (CursorIndexOutOfBoundsException e) {

            throw e;

        } finally {

            c.close();
        }

    }

    public long salvaUsuario(UsuarioSerial user) {


        ContentValues c;

        try {

            bancoDados.beginTransaction();

            c = new ContentValues();
            c.put("USUARIO_NOMEUSER", user.getNomeUser());
            c.put("USUARIO_LOGINUSER", user.getLoginUser());
            c.put("USUARIO_SENHAUSER", user.getSenhaUser());
            c.put("USUARIO_CONFIRMASENHAUSER", user.getConfirmaSenhaUser());
            c.put("USUARIO_DATACAD", user.getDataCadUser());
            c.put("ID_PERFIL", user.getPerfil().getId_perfilUser());
            c.put("USUARIO_STATUSUSER", user.getStatusUser());

            result = bancoDados.insertOrThrow("USUARIO", null, c);

            bancoDados.setTransactionSuccessful();

        } catch (Exception e) {

            throw e;

        } finally {

            //if (bd != null) {

            // if (banco.inTransaction()) {

            //    banco.endTransaction();

            //}
            DatabaseManager.getInstance().closeDatabase();

            //}

            /*Fecha o banco*/
            //AndroidUtils.fechaBanco(banco);
            // bd.close();

        }

        return result;

    }

    /*Metodo para atualizar o usuario recebe como parametro um objeto do tipo usuario*/
    public long updateUsuario(Context context, UsuarioSerial user) {

        ContentValues c;

        try {

            bancoDados.beginTransaction();

            c = new ContentValues();
            c.put("USUARIO_NOMEUSER", user.getNomeUser());
            c.put("USUARIO_LOGINUSER", user.getLoginUser());
            c.put("USUARIO_SENHAUSER", user.getSenhaUser());
            c.put("USUARIO_CONFIRMASENHAUSER", user.getConfirmaSenhaUser());
            c.put("USUARIO_DATACAD", user.getDataCadUser());
            c.put("ID_PERFIL", user.getPerfil().getId_perfilUser());
            c.put("USUARIO_STATUSUSER", user.getStatusUser());


            result = bancoDados.update("usuario", c, "id_usuario = ?", new String[]{"" + user.getId_usuario()});

            bancoDados.setTransactionSuccessful();


        } catch (Exception e) {

            throw e;

        } finally {


            // if (bd != null) {

            //    if (banco.inTransaction()) {

            //       banco.endTransaction();
            //    }


            // }

            //AndroidUtils.fechaBanco(banco);
            //banco.close();
            DatabaseManager.getInstance().closeDatabase();


        }

        return result;


    }


}
