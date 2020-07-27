package br.com.loadti.catalogomobile.utilis;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.embarcadero.javaandroid.DSRESTConnection;

import br.com.loadti.catalogomobile.DAO.ConfigGeralDAO;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.R;


public class AndroidUtils {

    protected static final String TAG = "AndroidUtils";

    private boolean sdCardAvailable;
    private boolean sdCardWritableReadable;
    private boolean sdCardReadableOnly;

    /* Verificando se a conexao esta disponivel */
    public static boolean isNeworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {

            return false;

        } else {

            NetworkInfo info = connectivity.getActiveNetworkInfo();

            if (info != null && info.isConnected()) {

                return true;

            }

        }

        return false;
    }

    /* AlerDialog passando Recurso R como parametro */
    public static void alertDialog(final Context context, final int mensagem) {

        try {

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.app_name))
                    .setMessage(mensagem)
                    .setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    return;

                                }
                            }).create();

            dialog.show();

        } catch (Exception e) {

            Log.e(TAG, e.getMessage(), e);

        }

    }

    /* Verifica o estado do cartão de memoria do dispositivo */
    public void getStateSDcard() {

        // Pega o status do cartao de memoria
        String status = Environment.getExternalStorageState();

		/* Verifica se o cartao foi removido antes de ser montado */
        if (Environment.MEDIA_BAD_REMOVAL.equals(status)) {

            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;

            Log.d(TAG, "Midia removida");
        }
        /*
         * Verifica se o cartão de memoria esta inserido no dispositivo, e se o
		 * mesmo esta em verificaccao pelo aparelho
		 */
        else if (Environment.MEDIA_CHECKING.equals(status)) {

            sdCardAvailable = true;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;

            Log.d(TAG, "Midia sendo verificada. Aguarde...");
        }
        /*
         * Verifica se o cartao de memoria esta presente e montado no momento
		 * com permissao de escrita e leitura
		 */
        else if (Environment.MEDIA_MOUNTED.equals(status)) {

            sdCardAvailable = true;
            sdCardWritableReadable = true;
            sdCardReadableOnly = false;

            Log.d(TAG, "O cartao esta com permissao de escrita e leitura");
        }
        /*
         * Verifica se cartao esta presente, montado porem com permissao somente
		 * de leitura
		 */
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(status)) {

            sdCardAvailable = true;
            sdCardWritableReadable = false;
            sdCardReadableOnly = true;

            Log.d(TAG,
                    "O cartao esta com permissao somente de leitura no momento");
        }
        /*
         * Verifica se o cartao esta presente e se o sistema de arquivo é
		 * compativel
		 */
        else if (Environment.MEDIA_NOFS.equals(status)) {

            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardWritableReadable = false;

            Log.d(TAG, "Sistema de arquivo não suportado");

        }
        /*
         * verifica se o carta esta presente
		 */
        else if (Environment.MEDIA_REMOVED.equals(status)) {

            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;

            Log.d(TAG, "O dispositivo esta sem o cartao de memoria");
        }
        /*
         * Verifica se o cartao esta presente e se esta compartilhado via USB
		 */
        else if (Environment.MEDIA_SHARED.equals(status)) {

            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;

            Log.d(TAG, "Cartao compartilhado via USB");
        }
        /*
         * Verifica se o cartao esta presente e se nao pode ser montado
		 */
        else if (Environment.MEDIA_UNMOUNTABLE.equals(status)) {

            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;

            Log.d(TAG, "O cartao esta presente, mas nao pode ser montado");
        }

		/*
         * Verifica se o cartao esta no dispositivo e se ta montando
		 */
        else if (Environment.MEDIA_UNMOUNTED.equals(status)) {

            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;

        }
    }

    public boolean isSdCardAvailable() {
        return sdCardAvailable;
    }

    public void setSdCardAvailable(boolean sdCardAvailable) {
        this.sdCardAvailable = sdCardAvailable;
    }

    public boolean isSdCardWritableReadable() {
        return sdCardWritableReadable;
    }

    public void setSdCardWritableReadable(boolean sdCardWritableReadable) {
        this.sdCardWritableReadable = sdCardWritableReadable;
    }

    public boolean isSdCardReadableOnly() {
        return sdCardReadableOnly;
    }

    public void setSdCardReadableOnly(boolean sdCardReadableOnly) {
        this.sdCardReadableOnly = sdCardReadableOnly;
    }

    /* AlertDialog passando String como parametro */
    public static void alertDialog(final Context context, final String mensagem) {

        try {

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.app_name))
                    .setMessage(mensagem)
                    .setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    return;

                                }
                            }).create();

            dialog.show();

        } catch (Exception e) {

            Log.e(TAG, e.getMessage(), e);

        }

    }

    /* Formata data no formato padrao brasileiro */
    public static String setdataPedido(String data) {

        if (data != null && !data.equals("")) {

            SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
            Date d1 = null;
            /*
             * Para formatar datas deve-se criar uma instancia de calendar
			 */

			/*
             * Chama o metodo SimpleDateFormat e passar o modo como quer q a
			 * data seja formatada como parametro
			 */
            try {
                d1 = f.parse(data);
                /* Cria uma String pra receber o retorno da data formatada */
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                data = format.format(d1);

                return data;

            } catch (Exception e) {

                Log.e(TAG, "Erro ao formatar a data", e);
                return null;
            }

        }

        return null;

    }

    /* Formata data no formato padrao americano */
    public static String setdataenEU(String data) {

        if (data != null && !data.equals("")) {

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            Date d1 = null;

			/*
             * Chama o metodo SimpleDateFormat e passar o modo como quer q a
			 * data seja formatada como parametro
			 */
            try {

                d1 = f.parse(data);
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

				/* Cria uma String pra receber o retorno da data formatada */

                data = format.format(d1);

                // return dataformatada;
                return data;

            } catch (Exception e) {

                Log.e(TAG, "Erro ao formatar a data", e);
                return null;
            }

        }

        return null;

    }

    /*
     * Retorna a data atual formatada no padrao portugues brasileiro
     */
    public static String getDate(String formato) {

        Log.d("LoadMobile - AndUtils", "getDate");

        return new SimpleDateFormat(formato).format(new Date());

    }

    /*
     * Retorna a hora atual formatada no padrao portugues brasileiro
     */
    public static String getTime(String formato) {

        Log.d("LoadMobile - AndUtils", "getTime");

        Locale local = new Locale("pt", "BR");
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(formato, local);

        return sdf.format(gc.getTime()).toString();
    }

    /*
     * Método do botão cancelar ou sair
     */
    public static void sairActivity(final Activity act) {

        Log.d("SisBarMobile - Master", "sairActivity");

        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        builder.setMessage("Deseja realmente sair do sistema?")
                .setCancelable(false)
                .setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {

								/* Caso o banco de dados esteja aberto, fecha */
                                fechaBanco(DB.bancoDados);

								/* Fecha a tela desejada */
                                act.finish();

                            }
                        })
                .setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /*
     * Abrir o banco de dados
     */
    public static void abreBanco(Context ctx, SQLiteDatabase banco) {

        Log.d("LoadSystem - Utils", "abrindo o Banco");

       // if (banco == null || !banco.isOpen()) {
           // DB db = new DB(ctx);
           // banco = db.getWritableDatabase();
       // }
    }

    /*
     * Fechar o Banco de dados
     */
    public static void fechaBanco(SQLiteDatabase banco) {

        Log.d("LoadMobile - AndUtils", "fechando o banco");

        if (banco != null) {
            banco.close();
        }
    }

	/* Realiza a soma entre tudas datas */

    /* Formata data no formato padrao brasileiro */
    public static String somarData(int diaSomar) {

        try {
            Date d = new Date();

            Calendar c = Calendar.getInstance();

            c.setTime(d);

            c.add(Calendar.DAY_OF_MONTH, diaSomar);

            Date datavenc = c.getTime();

			/* Cria uma String pra receber o retorno da data formatada */
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String vencimento = format.format(datavenc);

            Log.v("AndroidUtils-SomaData", "Data somada " + vencimento);

            return vencimento;
        } catch (Exception e) {

            throw e;

        }

    }

    /* valida um email passado por parametro */
    public static boolean validarEmail(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;

                Log.d("EnviaEmail-Valida email", "Email é :  "
                        + isEmailIdValid);
            }
        }
        return isEmailIdValid;
    }

    /*Retorna a conexao com o WebService*/
    public static DSRESTConnection getConnection(Context context) {

        Log.v("LoadMobile-Prefere", "getConnection");

		/*
         * Validar se tem um host e porta informado nas configuraçoes
		 */
        ConfigGeralSerial conf = new ConfigGeralSerial();
        ArrayList<ConfigGeralSerial> aConf = new ArrayList<ConfigGeralSerial>();
        aConf = new ConfigGeralDAO(context).pesqConfigGeral();

        for (int i = 0; i < aConf.size(); i++) {

            conf.setId_configGeral(aConf.get(i).getId_configGeral());
            conf.setHost(aConf.get(i).getHost());
            conf.setPortaHost(aConf.get(i).getPortaHost());
        }


        if (!conf.getHost().equals("") && conf.getPortaHost() != 0) {

            DSRESTConnection conexao = new DSRESTConnection();
            conexao.setHost(conf.getHost());
            conexao.setPort(conf.getPortaHost());


            return conexao;

        } else {

            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Atenção!!!");
            alert.setMessage("Não foi definido o host ou a porta nas configurações do sistema. Verifique!");
            alert.setCancelable(false);
            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

					/* Fecha o Dialog */
                    dialog.cancel();

                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();

            return null;
        }
    }

    /*Formtar telefone*/
    public static String formatPhone(String paramString) {
        String str1 = "";
        paramString.replace("(", "");
        paramString.replace(")", "");
        paramString.replace("-", "");
        paramString.replace("_", "");
        paramString.replace(".", "");
        paramString.replace(" ", "");
        String str2 = paramString.trim();
        if (str2.length() == 10) {
            str1 = str1 + "(" + str2.substring(0, 2) + ") ";
        }
        return str1;
    }

    /*calcular o valor de um tamanho de imagem reduzida*/
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Altura e largura da imagem

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calcula as proporções de altura e largura
            // com a altura e largura solicitada
            final int heightRatio = Math.round((float) height / (float) reqHeight);

            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Escolhe qual a melhor proporção para inSampleSize
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(String res, int reqWidth, int reqHeight) {

        // Primeiro faz a decodificação com
        // inJustDecodeBounds = true para verificar as dimensões
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(res, options);

        // Calcula o inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decodifica o bitmap com o inSampleSize configurado
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(res, options);

    }


    public static Bitmap lerArquivo(String arquivo, int with, int heigth) {
        String lstrNomeArq;
        File arq;
        try {
            lstrNomeArq = arquivo;
            arq = new File(arquivo);
            if (!arq.exists()) {
                Log.i("TAG", "Diretorio Não encontrado caminho =" + arq);
                return null;
            } else {
                Log.i("TAG", "Diretorio encontrado =" + arq);
                Bitmap btm = decodeSampledBitmapFromResource(arquivo, with, heigth);
                //btm.createScaledBitmap(btm, with, heigth, false);

                return btm;

            }
        } catch (Exception e) {

            Log.e("AndroidUtils", "Erro ao criar Bitmap " + e.getMessage(), e);
            return null;
        }
    }

    /*Fecha o teclado virtual, se estiver aberto*/
    public static boolean closeVirtualKeyboard(Context context, View view) {

        /*Fecha o teclado virtual*/
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {

            boolean b = imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return b;

        }

        return false;
    }



}
