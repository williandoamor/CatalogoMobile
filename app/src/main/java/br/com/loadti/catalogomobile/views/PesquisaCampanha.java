package br.com.loadti.catalogomobile.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import br.com.loadti.catalogomobile.Adapter.CampanhaAdapter;
import br.com.loadti.catalogomobile.DAO.CampanhaDAO;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.interfaces.RecyclerViewOnClickListenerHack;

/**
 * Created by TI on 14/01/2016.
 */
public class PesquisaCampanha extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private ArrayList<CampanhaSerial> aCampanha;
    private RecyclerView mReciclePesquisaCampanha;
    private CampanhaSerial campanha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actpesquisa_geral);

        /*View da tela*/
        mReciclePesquisaCampanha = (RecyclerView) findViewById(R.id.rv_list_pesquisa);

        /*Objetos*/
        campanha = new CampanhaSerial();

        /*Listas*/
        aCampanha = new ArrayList<CampanhaSerial>();

        /*Metodos*/
        inicializarRecycledVeiw();
        listarCampanha();

    }

    private void inicializarRecycledVeiw() {

        mReciclePesquisaCampanha.setHasFixedSize(true);
        mReciclePesquisaCampanha.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(PesquisaCampanha.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mReciclePesquisaCampanha.setLayoutManager(llm);
    }

    private void listarCampanha() {

        aCampanha = new CampanhaDAO(PesquisaCampanha.this).buscaCampanhaConsulta("%%", 1, 0);
        CampanhaAdapter adapter = new CampanhaAdapter(PesquisaCampanha.this, aCampanha);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mReciclePesquisaCampanha.setAdapter(adapter);

    }

    @Override
    public void onClickListener(View view, int position) {

        CampanhaAdapter adapter = (CampanhaAdapter) mReciclePesquisaCampanha.getAdapter();
        campanha = adapter.retornaCampanha(position);

        setResult(RESULT_OK, new Intent().putExtra("campanha", campanha));
        finish();
    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }
}
