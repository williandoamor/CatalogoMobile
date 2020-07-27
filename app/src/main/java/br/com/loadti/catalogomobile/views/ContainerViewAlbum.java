package br.com.loadti.catalogomobile.views;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;


import br.com.loadti.catalogomobile.Adapter.ViewAlbumAdapter;
import br.com.loadti.catalogomobile.DAO.CampanhaDAO;
import br.com.loadti.catalogomobile.DAO.ConfigGeralDAO;
import br.com.loadti.catalogomobile.DAO.ProdutoDAO;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Serializable.ItensCampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.Serializable.UsuarioSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.Sincronizador;
import br.com.loadti.catalogomobile.utilis.Sincronizador.RetornoSincronizador;

/**
 * Created by TI on 08/02/2016.
 */
public class ContainerViewAlbum extends AppCompatActivity implements RetornoSincronizador {

    ViewPager viewPager;
    // GridView gridView;
    private static String TAG = "ContainerAlbum";
    private Toolbar mToolbar;
    ViewAlbumAdapter adapter;
    //ViewAlbumGridAdapter adapter;
    private ArrayList<ProdutoSerial> aProduto;
    private ArrayList<ConfigGeralSerial> aConfGeral;
    private ConfigGeralSerial configGeral;
    private String texto = "";
    private String tela = "";
    private UsuarioSerial usuario;
    private MenuInflater menuInflater;
    ArrayList<ProdutoSerial> list;
    private int x, y;
    private String telaCadastro = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.containervisualizacao_album);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageMargin(15);
        viewPager.setClipChildren(false);



        /*Listas*/
        //aProduto = new ArrayList<ProdutoSerial>();
        // aProduto = new ProdutoDAO(ContainerViewAlbum.this).busca("%%", 2);
        aConfGeral = new ArrayList<>();

        /*Objetos*/
        usuario = new UsuarioSerial();
        configGeral = new ConfigGeralSerial();

        //adapter = new ViewAlbumAdapter(ContainerViewAlbum.this, aProduto);

        // viewPager.setAdapter(adapter);

        /*Faz com que o Android nao bloqueie a tela*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mToolbar = (Toolbar) findViewById(R.id.tb_main_album);
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (getIntent() != null) {

            usuario = (UsuarioSerial) getIntent().getSerializableExtra("usuario");

        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        y = metrics.heightPixels;
        x = metrics.widthPixels;
        Log.d("Tamanho da tela", "Tela X " + x + " Tela Y " + y);


    }


    @Override
    protected void onResume() {
        super.onResume();
        preencherConfiguracaoGeral();
        listarProdutos();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        /*Se o usuario tiver feito uma pesquisa, a lista vai estar preenchido*/
        if (list != null && list.size() > 0) {

            outState.putSerializable("list", list);

        } else if (aProduto != null && aProduto.size() > 0) {

            outState.putSerializable("produtos", aProduto);
        }

        if (usuario != null) {

            outState.putSerializable("usuario", usuario);

        }


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState != null) {

            if (telaCadastro.equals("configuracao")) {

                if (configGeral.getCodCampanhaPadrao() == 0) {

                    //aProduto = (ArrayList<ProdutoSerial>) savedInstanceState.getSerializable("produtos");
                    new ListarTodosProdutosTask(ContainerViewAlbum.this).execute();
                } else {

                    list = (ArrayList<ProdutoSerial>) savedInstanceState.getSerializable("list");

                    if (list != null && list.size() > 0) {

                        aProduto = list;

                    }


                }


            }

            usuario = (UsuarioSerial) savedInstanceState.getSerializable("usuario");


        }


        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 2 && resultCode == RESULT_OK) {

            telaCadastro = "configuracao";
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView sv = new SearchView(ContainerViewAlbum.this);
        sv.setOnQueryTextListener(new FilterListener());
        item.setActionView(sv);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.menu_sincronizar:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {


                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
                        new Sincronizador(ContainerViewAlbum.this, this).execute();

                    }
                }

                return true;

            case R.id.menu_configurar:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {

                        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                        startActivityForResult(new Intent(ContainerViewAlbum.this, ContainerConfiguracao.class), 2);
                        aProduto = null;
                    }
                }

                return true;

            case R.id.menu_cadastrar_campanha:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {

                        startActivity(new Intent(ContainerViewAlbum.this, CadastrarCampanha.class));
                    }
                }

                return true;

            case R.id.menu_cadastrar_usuario:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {

                        startActivity(new Intent(ContainerViewAlbum.this, ContainerUsuario.class));
                    }
                }

                return true;


        }

        return super.onOptionsItemSelected(item);
    }


    /*Lista os produtos*/
    private void listarProdutos() {

        /*verifica se o id da campanha padrao e igual a zero*/
        if (configGeral.getCodCampanhaPadrao() == 0) {

            /*Limpa a lista se ela nao estiver vazia*/
            // if (!aProduto.isEmpty()) {

            //     aProduto.clear();

            // }

            if (texto.equals("")) {

                listartodosProdutos();

            }


        } else if (configGeral.getCodCampanhaPadrao() > 0) {

            // if (!aProduto.isEmpty()) {

            //     aProduto.clear();

            // }

            /*Chama a task para preencher os dados da campanha padrao*/
            //new CardListaProdutoCampanhaAdapter(getActivity()).execute();
            if (texto.equals("")) {

                listarItensCampanha();
            }


        }


    }

        /*Preencher o objeto configuracao Geral*/

    private void preencherConfiguracaoGeral() {

        aConfGeral = new ConfigGeralDAO(ContainerViewAlbum.this).pesqConfigGeral();

        for (int i = 0; i < aConfGeral.size(); i++) {

            configGeral.setId_configGeral(aConfGeral.get(i).getId_configGeral());
            configGeral.setHost(aConfGeral.get(i).getHost());
            configGeral.setPortaHost(aConfGeral.get(i).getPortaHost());
            configGeral.setCasaDecimalVenda(aConfGeral.get(i).getCasaDecimalVenda());
            configGeral.setTabelaPadrao(aConfGeral.get(i).getTabelaPadrao());
            configGeral.setMostrarPreco(aConfGeral.get(i).getMostrarPreco());
            configGeral.setVisualizacao(aConfGeral.get(i).getVisualizacao());
            configGeral.setCodCampanhaPadrao(aConfGeral.get(i).getCodCampanhaPadrao());
            configGeral.setCodTipoVisualizacao(aConfGeral.get(i).getCodTipoVisualizacao());


        }

    }

    /*Lista os itens da campanha*/
    private void listarItensCampanha() {

        CampanhaSerial campanha = new CampanhaSerial();
        ArrayList<CampanhaSerial> aCamp = new ArrayList<CampanhaSerial>();
        CampanhaDAO campanhaDAO = new CampanhaDAO(ContainerViewAlbum.this);
        aCamp = campanhaDAO.buscaCampanhaConsulta("%%", 0, configGeral.getCodCampanhaPadrao());
        aProduto = new ArrayList<ProdutoSerial>();

            /*Preenche a campanha com a campanha encontrada*/
        for (int i = 0; i < aCamp.size(); i++) {

            campanha = aCamp.get(i);

        }
          /*Preenche a lista de produtos com os itens da campanha informada*/
        for (ItensCampanhaSerial itens : campanha.getItensCampanha()) {

            aProduto.add(itens.getProduto());
        }

        adapter = new ViewAlbumAdapter(ContainerViewAlbum.this, aProduto, configGeral, y, x);
        //adapter = new ViewAlbumGridAdapter(ContainerViewAlbum.this, aProduto, configGeral, y, x);
        viewPager.setAdapter(adapter);
        // viewPager.setOffscreenPageLimit(2);
        //gridView.setAdapter(adapter);

    }


    /*Listar todos os itens da campanha*/
    private void listartodosProdutos() {

        /*Recupera o estado */
        //aProduto = (ArrayList<ProdutoSerial>) getLastCustomNonConfigurationInstance();

        if (aProduto != null && aProduto.size() > 0) {
            adapter = new ViewAlbumAdapter(ContainerViewAlbum.this, aProduto, configGeral, y, x);
            //adapter = new ViewAlbumGridAdapter(ContainerViewAlbum.this, aProduto, configGeral, y, x);
            viewPager.setAdapter(adapter);
            //viewPager.setOffscreenPageLimit(2);
            //gridView.setAdapter(adapter);

        } else if (aProduto == null || aProduto.size() == 0) {

            aProduto = new ProdutoDAO(ContainerViewAlbum.this).busca("%%", 2);
            adapter = new ViewAlbumAdapter(ContainerViewAlbum.this, aProduto, configGeral, y, x);
            //adapter = new ViewAlbumGridAdapter(ContainerViewAlbum.this, aProduto, configGeral, y, x);
            //gridView.setAdapter(adapter);
            viewPager.setAdapter(adapter);
            //viewPager.setOffscreenPageLimit(2);
            //new ListarTodosProdutosTask(ContainerViewAlbum.this).execute();

        }


    }

    /*Quando a sincronizacao dos dados terminar, recarrega a lista de produtos*/
    @Override
    public void getResultSincronizador(Boolean result) {
        if (result) {

            listarProdutos();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            AndroidUtils.sairActivity(ContainerViewAlbum.this);
        }

        return super.onKeyDown(keyCode, event);
    }

    /*Classe para pesquisa na lista*/
    private class FilterListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String textoFinal) {
            Log.i("CatalogoMobile", "onQueryTextSubmit: " + textoFinal);
            if (aProduto != null) {

                list = new ArrayList<ProdutoSerial>();
                for (ProdutoSerial prod : aProduto) {

                    boolean contains = prod.getDescricao().toUpperCase().contains(textoFinal.toUpperCase());
                    if (contains) {

                        list.add(prod);

                    }

                }

                texto = textoFinal;

            /*Exibe no RecicleView um Adapter com apenas a lista que fez o filtro*/
                viewPager.setAdapter(new ViewAlbumAdapter(ContainerViewAlbum.this, list, configGeral, y, x));
                //viewPager.setOffscreenPageLimit(2);
                //gridView.setAdapter(new ViewAlbumGridAdapter(ContainerViewAlbum.this, list, configGeral, y, x));
                AndroidUtils.closeVirtualKeyboard(ContainerViewAlbum.this, viewPager);
                //AndroidUtils.closeVirtualKeyboard(ContainerViewAlbum.this, gridView);
                aProduto = null;

            }


            return false;
        }


        @Override
        public boolean onQueryTextChange(String textoParcial) {
            Log.i("CatalogoMobile", "onQueryTextChange: " + textoParcial);
            if ("".equals(textoParcial)) {
                //Se vazio, volta a lista original
                // atualizarView();
                //mList = new ProdutoDAO(getActivity()).busca("%%", 2);
                //CardProdutoAdapter adapter = new CardProdutoAdapter(getActivity(), mList, configGeral);
                //adapter.setRecyclerViewOnClickListenerHack(this);
                // mRecyclerView.setAdapter(adapter);

                /*Verifica se o id da campanha e zero
                * se for zero nao existe uma campanha padrao*/
                texto = "";
                if (configGeral.getCodCampanhaPadrao() == 0) {

                    /*Se nao houver uma campanha informada
                    *  carrega todos os produtos da lista*/
                    // new CardListaProdutoAdapter(getActivity()).execute();
                    listartodosProdutos();
                    list = null;
                    texto = "";
                    new ListarTodosProdutosTask(ContainerViewAlbum.this).execute();


                } else if (configGeral.getCodCampanhaPadrao() > 0) {

                    /*Se houver uma campanha informada
                    *  carrega somente os itens da campanha*/
                    // aProduto = new ProdutoDAO();
                    listarItensCampanha();


                }


            }
            return false;
        }
    }

    public class ListarTodosProdutosTask extends AsyncTask<Void, String, ArrayList<ProdutoSerial>> {

        private static final String TAG = "TaskPesquisaProduto";
        private Context ctx;

        public ListarTodosProdutosTask(Context context) {

            this.ctx = context;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            Log.d(TAG, "onProgressUpdate");

            AlertDialog.Builder build = new AlertDialog.Builder(ctx);
            build.setTitle("Atenção");
            build.setMessage("Erro ao listar produtos: " + values[0]);
            build.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });

            build.create().show();
        }

        @Override
        protected ArrayList<ProdutoSerial> doInBackground(Void... params) {

            try {

                aProduto = new ArrayList<ProdutoSerial>();
                aProduto = new ProdutoDAO(ContainerViewAlbum.this).busca("%%", 2);


            } catch (Exception e) {

                Log.e(TAG, e.getMessage(), e);
                publishProgress(e.getMessage());
            }


            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<ProdutoSerial> prod) {

            if (prod != null) {

                adapter = new ViewAlbumAdapter(ContainerViewAlbum.this, aProduto, configGeral, y, x);
                viewPager.setAdapter(adapter);
                //viewPager.setOffscreenPageLimit(2);
                //adapter = new ViewAlbumGridAdapter(ContainerViewAlbum.this, aProduto, configGeral, y, x);
                //gridView.setAdapter(adapter);
            }
        }
    }


}

