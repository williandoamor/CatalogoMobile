package br.com.loadti.catalogomobile.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.loadti.catalogomobile.DAO.CampanhaDAO;
import br.com.loadti.catalogomobile.DAO.ConfigGeralDAO;
import br.com.loadti.catalogomobile.DAO.ProdutoDAO;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Serializable.ItensCampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.Adapter.CardProdutoAdapter;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.UsuarioSerial;
import br.com.loadti.catalogomobile.interfaces.RecyclerViewOnClickListenerHack;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.Sincronizador;
import br.com.loadti.catalogomobile.views.CadastrarCampanha;
import br.com.loadti.catalogomobile.views.ContainerConfiguracao;
import br.com.loadti.catalogomobile.utilis.Sincronizador.RetornoSincronizador;
import br.com.loadti.catalogomobile.views.ContainerUsuario;


public class FragFotoProduto extends Fragment implements RecyclerViewOnClickListenerHack, RetornoSincronizador {

    private RecyclerView mRecyclerView;
    private List<ProdutoSerial> mList;
    private Menu menu;
    private ArrayList<ConfigGeralSerial> aConfGeral;
    private ConfigGeralSerial configGeral;
    private CardView viewProdutos;
    private ImageView imageView;
    private String texto = "";
    private String tela = "";
    private UsuarioSerial usuario;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);


        usuario = new UsuarioSerial();

        if (savedInstanceState != null) {

            usuario = (UsuarioSerial) savedInstanceState.getSerializable("user");

        } else {

                   /*Verifica se os parametros nao sao nullos*/
            Bundle args = getArguments();
            if (args != null) {

                usuario = (UsuarioSerial) args.getSerializable("usuario");

            }

        }





        /*Informa que o fragment deseja inflar um menu*/
        setHasOptionsMenu(true);


        /*Objetos*/
        mList = new ArrayList<ProdutoSerial>();
        configGeral = new ConfigGeralSerial();
        aConfGeral = new ArrayList<>();


        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                GridLayoutManager llm = (GridLayoutManager) mRecyclerView.getLayoutManager();


                //CardProdutoAdapter adapter = (CardProdutoAdapter) mRecyclerView.getAdapter();

                //if (mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {

                //    List<ProdutoSerial> p = new ProdutoDAO(getActivity()).busca("%%", 2);

                //    List<ProdutoSerial> listAux = p;

                //    for (int i = 0; i < listAux.size(); i++) {

                //       adapter.addListItem(listAux.get(i), mList.size());
                //    }


                // }

                //StaggeredGridLayoutManager llm = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
                //int[] aux = llm.findLastCompletelyVisibleItemPositions(null);
                //int max = -1;
                //for (int i = 0; i < aux.length; i++) {
                //    max = aux[i] > max ? aux[i] : max;
                // }

                //CardProdutoAdapter adapter = (CardProdutoAdapter) mRecyclerView.getAdapter();

                //if (mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {
                //if (mList.size() == max + 1) {
                //List<ProdutoSerial> listAux = ((Principal) getActivity()).getSetCarList(10);

                // for (int i = 0; i < listAux.size(); i++) {
                //  adapter.addListItem(listAux.get(i), mList.size());
                //  }
                //}
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));

        //LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        // llm.setOrientation(LinearLayoutManager.VERTICAL);
        //llm.setReverseLayout(true);
        //mRecyclerView.setLayoutManager(llm);

        //if (configGeral.getVisualizacao() == 1) {
        //    GridLayoutManager llm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        //    mRecyclerView.setLayoutManager(llm);

        // } else if (configGeral.getVisualizacao() == 2) {

        //     GridLayoutManager llm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        //     mRecyclerView.setLayoutManager(llm);

        // }
        //GridLayoutManager llm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        // mRecyclerView.setLayoutManager(llm);
        // inicializarRecicleView();

        /*Layout escalonado*/
        //StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        //llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        //mRecyclerView.setLayoutManager(llm);

        //mList = ((Principal) getActivity()).getSetCarList();
        //mList = new ProdutoDAO(getActivity()).busca("%%", 2);
        //CardProdutoAdapter adapter = new CardProdutoAdapter(getActivity(), mList, configGeral);
        //adapter.setRecyclerViewOnClickListenerHack(this);
        // mRecyclerView.setAdapter(adapter);


        return view;
    }


    /*Inicializa o recicleview*/
    private void inicializarRecicleView() {

        if (configGeral.getVisualizacao() == 1) {
            GridLayoutManager llm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(llm);

        } else if (configGeral.getVisualizacao() == 2) {

            GridLayoutManager llm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(llm);
        }

    }

    /*Lista os produtos*/
    private void listarProdutos() {

        /*verifica se o id da campanha padrao e igual a zero*/
        if (configGeral.getCodCampanhaPadrao() == 0) {

            /*Limpa a lista se ela nao estiver vazia*/
            if (!mList.isEmpty()) {

                mList.clear();

            }

            /*Executa a task para preencher a lista de produtos*/
            //  new CardListaProdutoAdapter(getActivity()).execute();
            if (texto.equals("")) {

                listartodosProdutos();

            }


        } else if (configGeral.getCodCampanhaPadrao() > 0) {

            if (!mList.isEmpty()) {

                mList.clear();

            }

            /*Chama a task para preencher os dados da campanha padrao*/
            //new CardListaProdutoCampanhaAdapter(getActivity()).execute();
            if (texto.equals("")) {

                listarItensCampanha();
            }


        }


    }

    @Override
    public void onResume() {
        super.onResume();
        preencherConfiguracaoGeral();


        if (!tela.equals("fullScreem")) {
            inicializarRecicleView();
            listarProdutos();

        }

        tela = "";


    }

    /*Preencher o objeto configuracao Geral*/

    private void preencherConfiguracaoGeral() {

        aConfGeral = new ConfigGeralDAO(getActivity()).pesqConfigGeral();

        for (int i = 0; i < aConfGeral.size(); i++) {

            configGeral.setId_configGeral(aConfGeral.get(i).getId_configGeral());
            configGeral.setHost(aConfGeral.get(i).getHost());
            configGeral.setPortaHost(aConfGeral.get(i).getPortaHost());
            configGeral.setCasaDecimalVenda(aConfGeral.get(i).getCasaDecimalVenda());
            configGeral.setTabelaPadrao(aConfGeral.get(i).getTabelaPadrao());
            configGeral.setMostrarPreco(aConfGeral.get(i).getMostrarPreco());
            configGeral.setVisualizacao(aConfGeral.get(i).getVisualizacao());
            configGeral.setCodCampanhaPadrao(aConfGeral.get(i).getCodCampanhaPadrao());


        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        this.menu = menu;
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView sv = new SearchView(getActivity());
        sv.setOnQueryTextListener(new FilterListener());
        item.setActionView(sv);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (outState == null) {

            outState = new Bundle();

        }

        outState.putSerializable("user", usuario);

        Log.d("Principal - SavedInsta", "usuario " + outState.getSerializable("user"));



    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.menu_sincronizar:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {

                        new Sincronizador(getActivity(), this).execute();
                    }
                }

                return true;

            case R.id.menu_configurar:

                if (usuario != null) {

                    if (usuario.getPerfil().getId_perfilUser() == 1) {

                        startActivity(new Intent(getActivity(), ContainerConfiguracao.class));
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
    public void onClickListener(View view, int position) {

        //ProdutoSerial produto = new ProdutoSerial();
        //CardProdutoAdapter adapter = (CardProdutoAdapter) mRecyclerView.getAdapter();
        //produto = adapter.retornaProduto(position);

        //Intent intent = new Intent();
        //intent.setAction(Intent.ACTION_VIEW);
        //intent.setDataAndType(Uri.parse("file://" + produto.getFotoProd()), "image/*");


        //tela = "fullScreem";
        //startActivity(intent);
    }


    @Override
    public void onLongPressClickListener(View view, int position) {
        ProdutoSerial produto = new ProdutoSerial();
        CardProdutoAdapter adapter = (CardProdutoAdapter) mRecyclerView.getAdapter();
        produto = adapter.retornaProduto(position);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + produto.getFotoProd()), "image/*");


        tela = "fullScreem";
        startActivity(intent);
    }

    /*Quando a sincronizacao dos dados terminar, recarrega a lista de produtos*/
    @Override
    public void getResultSincronizador(Boolean result) {

        if (result) {

            listarProdutos();

        }

    }

    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvoclh) {
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if (cv != null && mRecyclerViewOnClickListenerHack != null) {
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(cv,
                                rv.getChildAdapterPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if (cv != null && mRecyclerViewOnClickListenerHack != null) {
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                rv.getChildAdapterPosition(cv));
                    }

                    return (true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    /*Classe para pesquisa na lista*/
    private class FilterListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String textoFinal) {
            Log.i("CatalogoMobile", "onQueryTextSubmit: " + textoFinal);
            if (mList != null) {

                List<ProdutoSerial> list = new ArrayList<ProdutoSerial>();
                for (ProdutoSerial prod : mList) {

                    boolean contains = prod.getDescricao().toUpperCase().contains(textoFinal.toUpperCase());
                    if (contains) {

                        list.add(prod);

                    }

                }

                texto = textoFinal;

            /*Exibe no RecicleView um Adapter com apenas a lista que fez o filtro*/
                mRecyclerView.setAdapter(new CardProdutoAdapter(getActivity(), list, configGeral));
                AndroidUtils.closeVirtualKeyboard(getActivity(), mRecyclerView);

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

                } else if (configGeral.getCodCampanhaPadrao() > 0) {

                    /*Se houver uma campanha informada
                    *  carrega somente os itens da campanha*/
                    //new CardListaProdutoCampanhaAdapter(getActivity()).execute();
                    listarItensCampanha();

                }


            }
            return false;
        }
    }

    /*Lista os itens da campanha*/
    private void listarItensCampanha() {

        CampanhaSerial campanha = new CampanhaSerial();
        ArrayList<CampanhaSerial> aCamp = new ArrayList<CampanhaSerial>();
        CampanhaDAO campanhaDAO = new CampanhaDAO(getActivity());
        aCamp = campanhaDAO.buscaCampanhaConsulta("%%", 0, configGeral.getCodCampanhaPadrao());

            /*Preenche a campanha com a campanha encontrada*/
        for (int i = 0; i < aCamp.size(); i++) {

            campanha = aCamp.get(i);

        }
          /*Preenche a lista de produtos com os itens da campanha informada*/
        for (ItensCampanhaSerial itens : campanha.getItensCampanha()) {

            mList.add(itens.getProduto());
        }

        CardProdutoAdapter adapter = new CardProdutoAdapter(getActivity(), mList, configGeral);
        mRecyclerView.setAdapter(adapter);

    }


    /*Listar todos os itens da campanha*/
    private void listartodosProdutos() {

        mList = new ProdutoDAO(getActivity()).busca("%%", 2);
        CardProdutoAdapter adapter = new CardProdutoAdapter(getActivity(), mList, configGeral);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("FragFotoProduto", "onDestroy");
    }

    public interface callbackupusuario {

        public UsuarioSerial getUsuario();


    }

}
