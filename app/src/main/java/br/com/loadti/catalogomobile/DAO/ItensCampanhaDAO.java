package br.com.loadti.catalogomobile.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.util.ArrayList;

import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ItensCampanhaSerial;
import br.com.loadti.catalogomobile.utilis.DB;
import br.com.loadti.catalogomobile.utilis.DatabaseManager;

/**
 * Created by TI on 09/01/2016.
 */
public class ItensCampanhaDAO {

    //private DB bd;
    //private SQLiteDatabase bandoDados;
    private Context context;
    //private DatabaseManager bancoDados;
    //private SQLiteDatabase bd;
    private SQLiteDatabase bancoDados;
    private ArrayList<ItensCampanhaSerial> aItens;
    private CampanhaSerial campanha;
    private long result;

    /*Contrutor para salvar os itens da campanha*/
    public ItensCampanhaDAO(Context ctx, ArrayList<ItensCampanhaSerial> itens, SQLiteDatabase banco, CampanhaSerial camp, long idcampanha) {

        //this.bd = dbh;
        // this.bandoDados = banco;

        this.aItens = itens;
        this.context = ctx;
        this.campanha = camp;
        this.result = idcampanha;

        bancoDados = banco;


    }

    /*Construtor para pesquisa de itens do pedido e atualizacao do pedido*/
    public ItensCampanhaDAO(Context ctx, SQLiteDatabase banco, CampanhaSerial camp) {

        //this.bd = dbh;
        //this.bandoDados = banco;
        bancoDados = banco;

        this.context = ctx;
        this.campanha = camp;

    }

    public void salvarItens() throws SQLDataException {


        ContentValues c = new ContentValues();
        ArrayList<ItensCampanhaSerial> aItem = aItens;

        try {

            for (ItensCampanhaSerial campItens : aItem) {

                c.put("ID_CAMPANHA", result);
                c.put("COD_PRODUTO", campItens.getProduto().getcodProd().toString());

                bancoDados.insertOrThrow("ITENSCAMPANHA", null, c);

            }

        } catch (Exception e) {

            throw new SQLDataException("Erro ao salvar os itens da campanha " + e.toString());

        }

    }

    /*Pesquisa os itens da campanha com o mesmo id da campaha*/
    public ArrayList<ItensCampanhaSerial> aItensCampanha() {

        String sql;

        try {

            sql = "select i.*, p.* from itenscampanha i ";
            sql += "left join produto p ";
            sql += "on (i.cod_produto=p.codprod)";
            sql += "where i.id_campanha = " + campanha.getIdcampnha();

            return cursorItemCampanha(bancoDados.rawQuery(sql, null));


        } catch (Exception e) {

            throw e;

        }

    }

    /*Cursor para retornar os itens do pedido para consulta*/
    private ArrayList<ItensCampanhaSerial> cursorItemCampanha(Cursor cItem) {

        ArrayList<ItensCampanhaSerial> aItens = new ArrayList<ItensCampanhaSerial>();

        try {

            if (cItem.moveToFirst()) {

                do {

                    ItensCampanhaSerial itens = new ItensCampanhaSerial();

                    itens.setIdCampanha(cItem.getInt(cItem.getColumnIndex("ID_CAMPANHA")));
                    itens.setIdItemCampanha(cItem.getInt(cItem.getColumnIndex("ID_ITEMCAMPANHA")));
                    itens.getProduto().setCodigoProduto(cItem.getString(cItem.getColumnIndex("CODIGO")));
                    itens.getProduto().setcodProd(cItem.getString(cItem.getColumnIndex("CODPROD")));
                    itens.getProduto().setDescricao(cItem.getString(cItem.getColumnIndex("NOMEPROD")));
                    itens.getProduto().setPrevenda1(new BigDecimal(cItem.getDouble(cItem.getColumnIndex("PREVENDA1"))));
                    itens.getProduto().setPrevenda2(new BigDecimal(cItem.getDouble(cItem.getColumnIndex("PREVENDATAB2"))));
                    itens.getProduto().setEstoque(new BigDecimal(cItem.getDouble(cItem.getColumnIndex("ESTATU"))));
                    itens.getProduto().setNaoVender(cItem.getString(cItem.getColumnIndex("NAOVENDER")));
                    itens.getProduto().setPrecusto(new BigDecimal(cItem.getDouble(cItem.getColumnIndex("PRECUSTO"))));
                    itens.getProduto().setCustomedio(new BigDecimal(cItem.getDouble(cItem.getColumnIndex("CUSTOMEDIO"))));
                    itens.getProduto().setCustoreal(new BigDecimal(cItem.getDouble(cItem.getColumnIndex("CUSTOREAL"))));
                    itens.getProduto().setUnidade(cItem.getString(cItem.getColumnIndex("UNIDADE")));
                    itens.getProduto().setObservacaoProd(cItem.getString(cItem.getColumnIndex("OBSERVACAO_PROD")));
                    itens.getProduto().setFotoProd(cItem.getString(cItem.getColumnIndex("FOTO")));

                    aItens.add(itens);

                } while (cItem.moveToNext());


            }

            return aItens;

        } catch (Exception e) {

            throw e;


        } finally {

            cItem.close();
        }


    }

    /*Metodo utilizado para atualizar os itens do pedido*/
    public void updateItens() throws SQLDataException {

        ContentValues c;

        try {

            bancoDados.delete("itenscampanha", "id_campanha = ? ", new String[]{"" + campanha.getIdcampnha()});


            /* Salva os itens da campanha*/
            c = new ContentValues();
            ArrayList<ItensCampanhaSerial> item = (ArrayList<ItensCampanhaSerial>) campanha.getItensCampanha();

            for (ItensCampanhaSerial campitens : item) {


                c.put("id_campanha", campanha.getIdcampnha());
                c.put("cod_produto", campitens.getProduto().getcodProd());

                bancoDados.insertOrThrow("itenscampanha", null, c);
            }
        } catch (Exception e) {

            throw e;
        }

    }


}
