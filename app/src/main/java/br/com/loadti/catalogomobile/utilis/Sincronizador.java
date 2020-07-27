package br.com.loadti.catalogomobile.utilis;

import com.embarcadero.javaandroid.Base64;
import com.embarcadero.javaandroid.DSProxy.TServerMethodos1;
import com.embarcadero.javaandroid.DSRESTConnection;
import com.embarcadero.javaandroid.TDataSet;


import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;


import android.os.PowerManager;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.loadti.catalogomobile.DAO.ProdutoDAO;
import br.com.loadti.catalogomobile.DAO.SincronizacaoDAO;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.Serializable.SincronizacaoSerial;

import static android.os.PowerManager.SCREEN_BRIGHT_WAKE_LOCK;

public class Sincronizador extends AsyncTask<Void, String, Boolean> {

    /* Context */
    private Context context;

    /* Objetos de conexao */
    private DSRESTConnection conn;
    private TServerMethodos1 serv;

    /* ProgressDialog */
    private ProgressDialog dialog;

    //private DB dbh;
    //private SQLiteDatabase bancoDados;
    //private SQLiteDatabase database;
    // private DatabaseManager mDatabaseManager;
    private SQLiteDatabase bancoDados;
    private ArrayList<ProdutoSerial> aProd;

    SincronizacaoSerial dadosSinc;

    RetornoSincronizador iRetornoSincronizador;

    private PowerManager pm;
    private PowerManager.WakeLock wl;


    public Sincronizador(Context ctx, RetornoSincronizador iRetornoSincronizador) {

        Log.v("LoadMobile-Sincro", "Sincronizador");

        this.context = ctx;

//        dbh = new DB(ctx);
        //database = dbh.getWritableDatabase();

        //database = DatabaseManager.getInstance().openDatabase();

        //dbh = new DB(ctx);
        //bancoDados = dbh.getWritableDatabase();
        //mDatabaseManager = DatabaseManager.getInstance();
        // mDatabaseManager.setDatabaseHelper(context);

        //openDataBase();
        DatabaseManager.getInstance();
        DatabaseManager.initializeInstance(context);
        bancoDados = DatabaseManager.getInstance().openDatabase();


        conn = AndroidUtils.getConnection(ctx);
        serv = new TServerMethodos1(conn);

        this.iRetornoSincronizador = iRetornoSincronizador;

        /*Impede o android de bloquear a tela antes da sincronizacao acabar*/
        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Sincronizador");
        wl.acquire();


        aProd = new ArrayList<ProdutoSerial>();
        aProd = new ProdutoDAO(context).busca("%%", 2);


        /*Busca a ultima data de atualizcao do dispositovo*/
        dadosSinc = new SincronizacaoSerial();
        dadosSinc = new SincronizacaoDAO(context).getDadosSincronizacao();


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("LoadMobile - Sincroniza", "onPreExecute");


        dialog = ProgressDialog.show(context, "Aguarde...",
                "Buscando registros no servidor...");


    }

    @Override
    protected Boolean doInBackground(Void... arg0) {

        Log.v("LoadMobile - Sincroniza", "doInBackground");

        buscaEInsere();

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.v("LoadMobile - Sincroniza", "onPostExecute");

        if (dialog != null) {
            dialog.hide();
            dialog.dismiss();
            dialog = null;
        }

        if (iRetornoSincronizador != null) {

            iRetornoSincronizador.getResultSincronizador(result);

            Log.d("Sincronizador", "Valor da variavel result: " + result);
        }

        wl.release();

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d("AtSincronizacao", "onProgressUpdate");

        AlertDialog.Builder build = new AlertDialog.Builder(context);
        build.setTitle("Atenção");
        build.setMessage("Erro ao Sincronizar os dados: " + values[0]);
        build.setNeutralButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        AlertDialog alert = build.create();
        alert.show();
    }

    /*
     * Buscar os registros no banco de dados do servidor e inserir no banco de
     * dados local
     */
    private void buscaEInsere() {

        Log.d("AtSincronizacao", "buscaEInsere");

        TDataSet ds;

        ContentValues ct;

        try {



			/*
             * Abre a transacao no banco de dados
			 */
            //bancoDados.beginTransaction();
            bancoDados.beginTransaction();

			/*
             * *****************************************
			 * ************** PRODUTO *****************
			 * *****************************************
			 */


            /* Buscar os registros no servidor */
            ds = serv.GetProdutoFoto(dadosSinc.getDataSincronizacao());

			/* Inicializar o content values */
            ct = new ContentValues();


            apagaTabela("PRODUTO");
            Log.d("Apaga tabela", "Apaga tabela produto");

			/* Percorrer o DataSet e gravar os registros no banco de dados local */
            while (ds.next()) {

                /*Verifica se o produto ja existe, se ja existir deleta antes de criar novamente*/
                //isIncludeProduto(ds.getValue("CODPROD").GetAsAnsiString()
                //        .trim());


                ct.put("CODPROD", ds.getValue("CODPROD").GetAsAnsiString()
                        .trim());
                ct.put("CODIGO", ds.getValue("CODIGO").GetAsAnsiString().trim());
                ct.put("NOMEPROD", ds.getValue("NOMEPROD").GetAsString().trim());
                ct.put("PREVENDA1", ds.getValue("PREVENDA").GetAsDouble());
                ct.put("PREVENDATAB2", ds.getValue("PREVENDATAB2")
                        .GetAsDouble());
                ct.put("ESTATU", ds.getValue("ESTATU").GetAsDouble());
                ct.put("NAOVENDER", ds.getValue("NAOVENDER").GetAsString()
                        .trim());
                ct.put("INATIVO", ds.getValue("INATIVO").GetAsString().trim());

				/*Campo da versao 3 do bando de dados*/
                ct.put("PRECUSTO", ds.getValue("PRECUSTO").GetAsDouble());
                ct.put("CUSTOMEDIO", ds.getValue("CUSTOMEDIO").GetAsDouble());
                ct.put("CUSTOREAL", ds.getValue("CUSTOREAL").GetAsDouble());
                /*Campo da versão 5 do banco*/
                ct.put("OBSERVACAO_PROD", ds.getValue("OBSERVACAO").GetAsString());
                ct.put("UNIDADE", ds.getValue("UNIDADE").GetAsString());

                /*Chama o metodo no servidor para buscar_on a foto do produto*/
                String fotoBase64 = serv.EnviarFotoTablet(ds.getValue("CODPROD").GetAsString().trim());
                // String fotoBase64 = (ds.getValue("FOTO").GetAsString().trim());

                if (!fotoBase64.equals("")) {

                    String prodfoto = convertJsonParaFoto(fotoBase64, ds.getValue("CODPROD").GetAsString(), ds.getValue("NOMEPROD").GetAsString().trim());
                    ct.put("FOTO", prodfoto);

                }// else {

                //     ct.put("FOTO", "");

                // }


                Log.v("Sincronizador", "OBS do produto " + ds.getValue("OBSERVACAO").GetAsString().trim());

				/* Insere o registro no banco */
                bancoDados.insertOrThrow("PRODUTO", null, ct);
                //database.insertOrThrow("PRODUTO", null, ct);


            }

			/*
             * *****************************************
			 * ************** EMPRESA *****************
			 * *****************************************
			 */

			/* Buscar os registros no servidor */
            ds = serv.GetEmpresa();

			/* Inicializar o content values */
            ct = new ContentValues();

			/* limpar tabela */
            apagaTabela("CONFEMP");
            Log.d("Apaga tabela", "Apaga tabela empresa");

			/* Percorrer o DataSet e gravar os registros no banco de dados local */
            while (ds.next()) {

                ct.put("CONFEMP_RAZA_EMP", ds.getValue("RAZAOSOCIAL").GetAsString()
                        .trim());
                ct.put("CONFEMP_FANT_EMP", ds.getValue("FANTASIA").GetAsString().trim());
                ct.put("CONFEMP_ESTADO_EMP", ds.getValue("ESTADO").GetAsString().trim());
                ct.put("CONFEMP_END_EMP", ds.getValue("ENDERECO").GetAsString().trim());
                ct.put("CONFEMP_FONE_EMP", ds.getValue("TELEFONE").GetAsString().trim());
                ct.put("CONFEMP_CNPJCPF_EMP", ds.getValue("CNPJ").GetAsString().trim());
                ct.put("CONFEMP_INSCRG_EMP", ds.getValue("INSC_ESTADUAL").GetAsString()
                        .trim());
                ct.put("CONFEMP_INSCMUN_EMP", ds.getValue("INSC_MUNI").GetAsString()
                        .trim());
                ct.put("CONFEMP_SITE_EMP", ds.getValue("SITE").GetAsString().trim());
                //  ct.put("CONFEMP_LOGO_EMP", ds.getValue("LOGOS").GetAsBlob().asByteArray());

				/* Insere o registro no banco */
                bancoDados.insertOrThrow("CONFEMP", null, ct);
                //database.insertOrThrow("CONFEMP", null, ct);

            }


            ct = new ContentValues();
            // apagaTabela("sincronizacao");
            Log.d("Apaga tabela", "Apaga tabela sincronizacao");



             /*Pega os dados da ultima atualizacao do aplicativo*/
             dadosSinc = new SincronizacaoSerial();
             dadosSinc.setDataSincronizacao(AndroidUtils.getDate("yyyy/MM/dd"));
             dadosSinc.setHoraSincronizacao(AndroidUtils.getTime("HH:mm"));

              ct.put("SINC_DATASINC", dadosSinc.getDataSincronizacao());
              ct.put("SINC_HORASINC", dadosSinc.getHoraSincronizacao());

             bancoDados.insertOrThrow("sincronizacao", null, ct);

            /* Informa ao bd que obteve sucesso na transacao
             */
            //AndroidUtils.abreBanco(context, bancoDados);
            bancoDados.setTransactionSuccessful();


        } catch (Exception e) {

            Log.e("LoadMobile - Sincroniza", "Erro em \"buscaEInsere\": "
                    + e.toString());

            publishProgress(e.getMessage());

			/*
             * Fecha a transacao no banco de dados
			 */
            // bancoDados.endTransaction();

        } finally {

			/*
             * Fecha a transacao no banco de dados
			 */
            // if (bancoDados.inTransaction()) {
            //     bancoDados.endTransaction();
            //  }



			/*
             * Fecha o banco
			 */
            // AndroidUtils.fechaBanco(bancoDados);
            // dbh.close();
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    /*
     * Remover os registros da tabela
     */
    private void apagaTabela(String tabela) {

        Log.d("LoadMobile-Sincroniza", "apagaTabela");

        bancoDados.delete(tabela, null, null);
        //database.delete(tabela, null, null);

    }


    public String convertJsonParaFoto(String srtFoto, String codProd, String nomePro) throws FileNotFoundException {


        try {

            /*remove a / no nome do produto*/
            nomePro = nomePro.replaceAll("/", "");
            nomePro = nomePro.replaceAll("[*]", "");
            File caminhofoto = new File(android.os.Environment.getExternalStorageDirectory() + "/loadmobile/FOTOS/PRODUTOS");
            File foto = new File(caminhofoto, nomePro + ".PNG");

            byte[] decodedString = android.util.Base64.decode(srtFoto, android.util.Base64.DEFAULT);
            Log.d("Sincronizador", "String da foto " + srtFoto);

            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


            /*Se o diretorio nao existir cria o diretorio*/
            if (!caminhofoto.exists()) {

                caminhofoto.mkdirs();
                    /* Cria a pasta se ela nao existir */
                caminhofoto.createNewFile();
            }

            if (!foto.exists()) {

                foto.delete();

            }

            FileOutputStream outputStream = new FileOutputStream(foto);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            decodedByte.compress(Bitmap.CompressFormat.PNG, 100, bos);

            bos.flush();
            bos.close();

            outputStream.close();
            decodedByte.recycle();

            return foto.toString();
        } catch (Exception e) {

            Log.e("JSONFOTO", "Erro ao converter foto ", e);


        }

        return "";
    }


    /*Metodo para verifica se ja existe o item no pedido*/
    public void isIncludeProduto(String idProduto) {

        if (aProd != null) {

             /*Varrea a lista de itens ja inclusos no pedido*/
            for (int i = 0; i < aProd.size(); i++) {

            /*Verifica se exite algump produto na lista pelo id*/
                ProdutoSerial prod = aProd.get(i);

            /*Se ouver retorna true*/
                if (prod.getcodProd().equals(idProduto)) {


                    //return true;
                    bancoDados.delete("produto", "codprod = ? ", new String[]{"" + idProduto});


                }/*Fim do fi que verifica se o id do produto é igual ao ID passado como parametro*/


            }/*Fim do for que varrea a lista de itens*/
        }
        //return false;

    }

    /*Interface para retornar o resultado da execucao do sincronizador*/
    public interface RetornoSincronizador {

        public void getResultSincronizador(Boolean result);

    }

}
