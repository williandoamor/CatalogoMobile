package br.com.loadti.catalogomobile.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.utilis.DB;
import br.com.loadti.catalogomobile.utilis.DatabaseManager;


public class ProdutoDAO {

    //DB dbhelp;
    //SQLiteDatabase bancoDados;
    private SQLiteDatabase bancoDados;
    //private SQLiteDatabase bd;

    public ProdutoDAO(Context ctx) {

        //dbhelp = new DB(ctx);
        //bancoDados = dbhelp.getWritableDatabase();
        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(ctx);
        bancoDados = DatabaseManager.getInstance().openDatabase();

    }

    /*
     * Buscar os produtos
     */
    public ArrayList<ProdutoSerial> busca(String param, int tipo) {

        Log.d("LoadSystem-ProdutosDao", "busca");
        String sql = "select * from produto p ";

        try {
            /*Traz somente o estoque maior que zero*/
            if (tipo == 0) {

                sql += "where upper(p.nomeprod) like '" + param + "' "
                        + "and p.naovender='N' "
                        + "and p.estatu > 0 "
                        + "and p.inativo='N' "
                        + "order by p.nomeprod";


            } else if (tipo == 1) {

                sql += "where upper(p.nomeprod) like '" + param + "' "
                        + "and p.naovender='N' "
                        + "and p.inativo='N' order by p.nomeprod";


            } else if (tipo == 2) {

                sql += "where upper(p.nomeprod) like '" + param + "' "
                        + "and p.naovender='N' "
                        + "and p.foto !='' "
                        + "and p.inativo='N' order by p.nomeprod";


            }

            return cursorForList(bancoDados.rawQuery(sql, null));
        } catch (Exception e) {

            Log.e("LoadSystem-ProdutosDao", "Erro em \"busca\": " + e.toString());

            throw e;

        } finally {
            //if (bancoDados != null) {


            //     bancoDados.close();
            //     dbhelp.close();
            // }
            DatabaseManager.getInstance().closeDatabase();


        }
    }


    /*
     * Converter o cursor para um ArrayList
     */
    private ArrayList<ProdutoSerial> cursorForList(Cursor c) {

        Log.d("LoadMobile-ProdutosDao", "cursorForList");
        Log.d("LoadMobile-ProdutosDao", "cursorForList - Qtde Registros: " + c.getCount());

        ArrayList<ProdutoSerial> aProd = new ArrayList<ProdutoSerial>();

        try {

            if (c.moveToFirst()) {

                do {

                    ProdutoSerial prod = new ProdutoSerial();

                    prod.setId_produto(c.getInt(c.getColumnIndex("ID_PRODUTO")));
                    prod.setcodProd(c.getString(c.getColumnIndex("CODPROD")));
                    prod.setCodigoProduto(c.getString(c.getColumnIndex("CODIGO")));
                    prod.setDescricao(c.getString(c.getColumnIndex("NOMEPROD")));
                    prod.setPrevenda1(new BigDecimal(c.getDouble(c.getColumnIndex("PREVENDA1"))));
                    prod.setPrevenda2(new BigDecimal(c.getDouble(c.getColumnIndex("PREVENDATAB2"))));
                    prod.setEstoque(new BigDecimal(c.getDouble(c.getColumnIndex("ESTATU"))));
                    prod.setNaoVender(c.getString(c.getColumnIndex("NAOVENDER")));

					/*Campo da versao 3 do banco de dados*/
                    prod.setPrecusto(new BigDecimal(c.getDouble(c.getColumnIndex("PRECUSTO"))));
                    prod.setCustomedio(new BigDecimal(c.getDouble(c.getColumnIndex("CUSTOMEDIO"))));
                    prod.setCustoreal(new BigDecimal(c.getDouble(c.getColumnIndex("CUSTOREAL"))));
                    prod.setObservacaoProd(c.getString(c.getColumnIndex("OBSERVACAO_PROD")));

                    /*Pega a string do campo foto*/
                    prod.setFotoProd(c.getString(c.getColumnIndex("FOTO")));

                    Log.d("ProdutoDAO", "Local da foto " + prod.getFotoProd());
                    prod.setUnidade(c.getString(c.getColumnIndex("UNIDADE")));


                    aProd.add(prod);

                } while (c.moveToNext());
            }

            return aProd;

        } catch (Exception e) {

            Log.e("LoadMobile-ProdutosDao", "Erro em \"cursorForList\": " + e.toString());

            throw e;

        } finally {

            if (!c.isClosed()) {
                c.close();
            }
        }
    }
}
