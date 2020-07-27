package br.com.loadti.catalogomobile.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.loadti.catalogomobile.Adapter.CardProdutoAdapter;
import br.com.loadti.catalogomobile.Adapter.CardProdutoCampanhaAdapter;
import br.com.loadti.catalogomobile.DAO.CampanhaDAO;
import br.com.loadti.catalogomobile.DAO.ConfigGeralDAO;
import br.com.loadti.catalogomobile.DAO.ProdutoDAO;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Serializable.ItensCampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.interfaces.RecyclerViewOnClickListenerHack;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.views.ContainerViewAlbum;
import br.com.loadti.catalogomobile.views.Principal;

/**
 * Created by TI on 06/01/2016.
 */
public class FragcadastrareditarCampanha extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private List<ProdutoSerial> mList;
    private Menu menu;
    private ArrayList<ConfigGeralSerial> aConfGeral;
    private ConfigGeralSerial configGeral;
    private ArrayList<ItensCampanhaSerial> aItem;
    private ProdutoSerial produto;
    private CampanhaSerial campanha;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cadastrar_campanha, container, false);
        setHasOptionsMenu(true);

            /*Objetos*/
        mList = new ArrayList<ProdutoSerial>();
        configGeral = new ConfigGeralSerial();
        aConfGeral = new ArrayList<>();
        aItem = new ArrayList<ItensCampanhaSerial>();
        campanha = new CampanhaSerial();

        /*Verifica se os parametros nao sao nullos*/
        Bundle args = getArguments();
        if (args != null) {

            campanha = (CampanhaSerial) args.getSerializable("campanha");
            Log.v("FragListaPro - onCreate", " Nome campanha passado por parametro " + campanha.getNomeCampanha());


        }


        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_campanha);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                //CardProdutoCampanhaAdapter adapter = (CardProdutoCampanhaAdapter) mRecyclerView.getAdapter();
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));

        return view;

    }

    private void inicializaRecycledView() {


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(null);


    }

    /*Lista os produtos*/
    private void listarProdutos() {
                /*verifica se a campanha e diferente de null*/
        if (campanha != null) {
            /*verifica se a campanha e diferente de zero*/
            if (campanha.getIdcampnha() == 0) {

                if (!mList.isEmpty()) {

                    mList.clear();
                }
                /*Se a campanha for zero, significa que esta sendo inserida uma nova campanha*/
                mList = new ProdutoDAO(getActivity()).busca("%%", 2);
                CardProdutoCampanhaAdapter adapter = new CardProdutoCampanhaAdapter(getActivity(), mList, configGeral, aItem);
                //adapter.setRecyclerViewOnClickListenerHack(this);
                mRecyclerView.setAdapter(adapter);
                /*Se a campanha for maior que zero, esta editando uma campanha ja existente*/
            } else if (campanha.getIdcampnha() > 0) {

                /*Limpa a lista se ela nao estiver vazia*/
                if (!mList.isEmpty()) {

                    mList.clear();

                }

                //CampanhaSerial campanha = new CampanhaSerial();
                //ArrayList<CampanhaSerial> aCamp = new ArrayList<CampanhaSerial>();
                //CampanhaDAO campanhaDAO = new CampanhaDAO(getActivity());
                //aCamp = campanhaDAO.buscaCampanhaConsulta("%%", 0, campanha.getIdcampnha());

            /*Preenche a campanha com a campanha encontrada*/
                //   for (int i = 0; i < aCamp.size(); i++) {

                //      campanha = aCamp.get(i);

                //   }
          /*Preenche a lista de produtos com os itens da campanha informada*/
                for (ItensCampanhaSerial itens : campanha.getItensCampanha()) {

                    mList.add(itens.getProduto());
                    aItem.add(itens);
                }


                CardProdutoCampanhaAdapter adapter = new CardProdutoCampanhaAdapter(getActivity(), mList, configGeral, aItem);
                mRecyclerView.setAdapter(adapter);

            }

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        preencherConfiguracaoGeral();
        inicializaRecycledView();
        listarProdutos();

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

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        this.menu = menu;
        inflater.inflate(R.menu.menu_campanha, menu);

        MenuItem item = menu.findItem(R.id.menu_search_campanha);
        SearchView sv = new SearchView(getActivity());
        sv.setOnQueryTextListener(new FilterListener());
        item.setActionView(sv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.add_itens_campanha:
                adicionarItensCampanha();

                return true;

            case R.id.salvar_campanha:

                AlertDialog.Builder alerSalvar = new AlertDialog.Builder(getActivity());
                alerSalvar.setTitle("CONFIRMA");
                alerSalvar.setMessage("Confirma gravação da campanha?");
                alerSalvar.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        salvarCamapanha();
                    }
                });
                alerSalvar.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                AlertDialog alert = alerSalvar.create();
                alert.show();

                return true;

            case R.id.cancelar_salvar_campanha:
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void adicionarItensCampanha() {

        if (!mList.isEmpty()) {

            mList.clear();
        }
                /*Se a campanha for zero, significa que esta sendo inserida uma nova campanha*/
        mList = new ProdutoDAO(getActivity()).busca("%%", 2);
        CardProdutoCampanhaAdapter adapter = new CardProdutoCampanhaAdapter(getActivity(), mList, configGeral, aItem);
        //adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);


    }

    /*Salva a campanha*/
    private void salvarCamapanha() {

        if (campanha.getItensCampanha().size() < 0) {

            AndroidUtils.alertDialog(getActivity(), "Informe ao menos um item para esta campanha para continuar.");

        } else {

            try {
                /*Se o id da campanha for maior que zero, esta atualizando*/
                if (campanha.getIdcampnha() > 0) {

                    CampanhaDAO dao = new CampanhaDAO(getActivity(), campanha);
                    dao.updateCampanha();

                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            getActivity());
                    alert.setCancelable(false);
                    alert.setTitle("Informação");
                    alert.setMessage("Campanha incluida com sucesso!");
                    alert.setNeutralButton(
                            "OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {

                                    //startActivity(new Intent(getActivity(), Principal.class).putExtra("tela", "campanha"));
                                    //startActivity(new Intent(getActivity(), Principal.class));
                                    getActivity().finish();


                                }
                            });
                    AlertDialog alerta = alert.create();
                    alerta.show();


                } else if (campanha.getIdcampnha() == 0) {

                    CampanhaDAO campanhaDAO = new CampanhaDAO(getActivity(), campanha);
                    long id = campanhaDAO.salvarCampanha();
                    if (id > 0) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                getActivity());
                        alert.setCancelable(false);
                        alert.setTitle("Informação");
                        alert.setMessage("Pedido incluido com sucesso!");
                        alert.setNeutralButton(
                                "OK",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {

                                        //startActivity(new Intent(getActivity(), Principal.class));
                                        //startActivity(new Intent(getActivity(), Principal.class));
                                        getActivity().finish();


                                    }
                                });
                        AlertDialog alerta = alert.create();
                        alerta.show();
                    }

                }


            } catch (Exception e) {

                AndroidUtils.alertDialog(
                        getActivity(),
                        "Ocorreu o seguinte erro ao gravar a campanha"
                                + e);


            }

        }


    }

    @Override
    public void onClickListener(View view, int position) {

        CardProdutoCampanhaAdapter adapter = (CardProdutoCampanhaAdapter) mRecyclerView.getAdapter();

        ItensCampanhaSerial itens = new ItensCampanhaSerial();
        produto = new ProdutoSerial();
        produto = adapter.retornaProduto(position);

        /*Verifica se o produto ja existe na lista*/
        int pos = existeProduto();

        /*Se o retorno for zero ou maior, significa que o produto ja existe
        * entao remove o item da lista*/
        if (pos >= 0) {

            aItem.remove(pos);

         /*Se o item nao existir acrescenta o mesmo na lista*/
        } else {

            itens.setProduto(produto);
            aItem.add(itens);

        }
        /*seta os itens da campanha*/
        campanha.setItensCampanha(aItem);

        adapter.notifyItemChanged(position);

    }

    @Override
    public void onLongPressClickListener(View view, final int position) {


    }

    /*
     * Verificar se o produto já existe na lista, retorna -1 caso não exista
     */
    private int existeProduto() {

        for (int i = 0; i < aItem.size(); i++) {

            ItensCampanhaSerial it = aItem.get(i);

            if (it.getProduto().getcodProd().equals(produto.getcodProd())) {

                return i;

            }
        }

        return -1;
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
            /*Exibe no RecicleView um Adapter com apenas a lista que fez o filtro*/
                mRecyclerView.setAdapter(new CardProdutoCampanhaAdapter(getActivity(), list, configGeral, aItem));
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
                mList = new ProdutoDAO(getActivity()).busca("%%", 2);
                CardProdutoCampanhaAdapter adapter = new CardProdutoCampanhaAdapter(getActivity(), mList, configGeral, aItem);
                //adapter.setRecyclerViewOnClickListenerHack(this);
                mRecyclerView.setAdapter(adapter);

            }
            return false;
        }
    }

}
