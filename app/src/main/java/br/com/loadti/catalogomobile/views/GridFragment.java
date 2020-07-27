package br.com.loadti.catalogomobile.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import br.com.loadti.catalogomobile.Adapter.ViewAlbumGridAdapter;
import br.com.loadti.catalogomobile.DAO.CampanhaDAO;
import br.com.loadti.catalogomobile.DAO.ProdutoDAO;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Serializable.ItensCampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.Serializable.UsuarioSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.Sincronizador;

/**
 * Created by TI on 22/02/2016.
 */
public class GridFragment extends Fragment implements Sincronizador.RetornoSincronizador {

    private GridView gridView;
    private ViewAlbumGridAdapter adapter;
    private ArrayList<ProdutoSerial> listaProdutos;
    private Activity activity;
    private ConfigGeralSerial config;
    private UsuarioSerial usuario;
    private callbackgetDados idados;
    private Menu menu;
    private int x;
    private int y;
    private String telaCadastro = "";
    private String texto = "";
    private ArrayList<ProdutoSerial> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        /*infla o layout*/
        view = inflater.inflate(R.layout.containervisualizacao_album_gridview, container, false);

        /*Faz com que o Fragment exiba o menu ao invez da activity*/
        setHasOptionsMenu(true);


        /*Inicializa os componentes da tela*/
        gridView = (GridView) view.findViewById(R.id.grid_view);

        /*Inicializa os objetos*/
        listaProdutos = new ArrayList<ProdutoSerial>();
        usuario = new UsuarioSerial();
        config = new ConfigGeralSerial();

        return view;
    }

    /*Conexao entre a activity e o fragment*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        try {

            if (context instanceof Activity) {


                activity = (Activity) context;
                idados = (callbackgetDados) activity;

            }


        } catch (ClassCastException e) {


            throw new ClassCastException("Falta implementar o médodo callbackgetDados" + e.toString());
        }

    }

    /*Metodo para pegar os dados vindos da activity*/
    public void getDados() {

        /*pega a lista de produtos vinda da activiy*/
        this.listaProdutos = idados.getProdutos();

        /*Pega o usuario vindo da activity*/
        this.usuario = idados.getusuario();

        /*Pega os dados de configuracao vindos da activity*/
        this.config = idados.getconf();

        /*Pega o tamanho X do dispositivo vindo da activity*/
        this.x = idados.getMetricasX();

       /*Pega o tamanho Y do dispositivo vindo da activity*/

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        this.menu = menu;
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView sv = new SearchView(getActivity());
        //sv.setOnQueryTextListener(new FilterListener());
        item.setActionView(sv);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.menu_sincronizar:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {


                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
                        new Sincronizador(getActivity(), this).execute();

                    }
                }

                return true;

            case R.id.menu_configurar:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {

                        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                        startActivityForResult(new Intent(getActivity(), ContainerConfiguracao.class), 2);
                        listaProdutos = null;
                    }
                }

                return true;

            case R.id.menu_cadastrar_campanha:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {

                        startActivity(new Intent(getActivity(), CadastrarCampanha.class));
                    }
                }

                return true;

            case R.id.menu_cadastrar_usuario:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {

                        startActivity(new Intent(getActivity(), ContainerUsuario.class));
                    }
                }

                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getResultSincronizador(Boolean result) {

    }

    /*Metodo para verificar qual qual activity foi invocada*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == getActivity().RESULT_OK) {

            telaCadastro = "configuracao";
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        }

    }

    /*Lista os produtos*/
    private void listarProdutos() {

        /*verifica se o id da campanha padrao e igual a zero*/
        if (config.getCodCampanhaPadrao() == 0) {


            if (texto.equals("")) {

                listartodosProdutos();

            }


        } else if (config.getCodCampanhaPadrao() > 0) {


            if (texto.equals("")) {

                listarItensCampanha();
            }


        }


    }

    /*Lista os itens da campanha*/
    private void listarItensCampanha() {

        CampanhaSerial campanha = new CampanhaSerial();
        ArrayList<CampanhaSerial> aCamp = new ArrayList<CampanhaSerial>();
        CampanhaDAO campanhaDAO = new CampanhaDAO(getActivity());
        aCamp = campanhaDAO.buscaCampanhaConsulta("%%", 0, config.getCodCampanhaPadrao());
        listaProdutos = new ArrayList<ProdutoSerial>();

            /*Preenche a campanha com a campanha encontrada*/
        for (int i = 0; i < aCamp.size(); i++) {

            campanha = aCamp.get(i);

        }
          /*Preenche a lista de produtos com os itens da campanha informada*/
        for (ItensCampanhaSerial itens : campanha.getItensCampanha()) {

            listaProdutos.add(itens.getProduto());
        }

        //adapter = new ViewAlbumAdapter(ContainerViewAlbumGridView.this, aProduto, configGeral, y, x);
        adapter = new ViewAlbumGridAdapter(getActivity(), listaProdutos, config, y, x);
        // viewPager.setAdapter(adapter);
        // viewPager.setOffscreenPageLimit(2);
        gridView.setAdapter(adapter);

    }


    /*Listar todos os itens da campanha*/
    private void listartodosProdutos() {

        if (listaProdutos != null && listaProdutos.size() > 0) {

            adapter = new ViewAlbumGridAdapter(getActivity(), listaProdutos, config, y, x);
            gridView.setAdapter(adapter);

        } else if (listaProdutos == null || listaProdutos.size() == 0) {

            listaProdutos = new ProdutoDAO(getActivity()).busca("%%", 2);
            //adapter = new ViewAlbumAdapter(ContainerViewAlbumGridView.this, aProduto, configGeral, y, x);
            adapter = new ViewAlbumGridAdapter(getActivity(), listaProdutos, config, y, x);
            gridView.setAdapter(adapter);

        }


    }

    /*Classe para pesquisa na lista*/
    private class FilterListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String textoFinal) {
            Log.i("CatalogoMobile", "onQueryTextSubmit: " + textoFinal);
            if (listaProdutos != null) {

                list = new ArrayList<ProdutoSerial>();
                for (ProdutoSerial prod : listaProdutos) {

                    boolean contains = prod.getDescricao().toUpperCase().contains(textoFinal.toUpperCase());
                    if (contains) {

                        list.add(prod);

                    }

                }

                texto = textoFinal;

                gridView.setAdapter(new ViewAlbumGridAdapter(getActivity(), list, config, y, x));
                // AndroidUtils.closeVirtualKeyboard(ContainerViewAlbumGridView.this, viewPager);
                AndroidUtils.closeVirtualKeyboard(getActivity(), gridView);
                //listaProdutos = null;

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
                if (config.getCodCampanhaPadrao() == 0) {

                    /*Se nao houver uma campanha informada
                    *  carrega todos os produtos da lista*/
                    // new CardListaProdutoAdapter(getActivity()).execute();
                    listartodosProdutos();
                    list = null;
                    texto = "";
                    new ListarTodosProdutosTask(getActivity()).execute();


                } else if (config.getCodCampanhaPadrao() > 0) {

                    /*Se houver uma campanha informada
                    *  carrega somente os itens da campanha*/
                    // aProduto = new ProdutoDAO();
                    listarItensCampanha();


                }


            }
            return false;
        }
    }

    /*Classe para preenchear o adapter de forma assincrona */
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

                listaProdutos = new ArrayList<ProdutoSerial>();
                listaProdutos = new ProdutoDAO(getActivity()).busca("%%", 2);


            } catch (Exception e) {

                Log.e(TAG, e.getMessage(), e);
                publishProgress(e.getMessage());
            }


            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<ProdutoSerial> prod) {

            if (prod != null) {

                adapter = new ViewAlbumGridAdapter(getActivity(), listaProdutos, config, y, x);
                gridView.setAdapter(adapter);
            }
        }
    }

    /*Interface para pegar os dados da activity*/
    public interface callbackgetDados {

        public ArrayList<ProdutoSerial> getProdutos();

        public ConfigGeralSerial getconf();

        public UsuarioSerial getusuario();

        public int getMetricasX();

        public int getMetricaY();

    }


}
