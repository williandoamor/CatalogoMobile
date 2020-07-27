package br.com.loadti.catalogomobile.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import br.com.loadti.catalogomobile.Adapter.CampanhaAdapter;
import br.com.loadti.catalogomobile.Adapter.UsuarioAdapter;
import br.com.loadti.catalogomobile.DAO.CampanhaDAO;
import br.com.loadti.catalogomobile.DAO.UsuarioDAO;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.UsuarioSerial;
import br.com.loadti.catalogomobile.interfaces.RecyclerViewOnClickListenerHack;

/**
 * Created by TI on 14/01/2016.
 */
public class PesquisaUsuario extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private ArrayList<UsuarioSerial> aUser;
    private RecyclerView mReciclePesquisaUsuario;
    private UsuarioSerial usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actpesquisa_geral);

        /*View da tela*/
        mReciclePesquisaUsuario = (RecyclerView) findViewById(R.id.rv_list_pesquisa);

        /*Objetos*/
        usuario = new UsuarioSerial();

        /*Listas*/
        aUser = new ArrayList<UsuarioSerial>();

        /*Metodos*/
        inicializarRecycledVeiw();
        listarUsuario();

    }

    private void inicializarRecycledVeiw() {

        mReciclePesquisaUsuario.setHasFixedSize(true);
        mReciclePesquisaUsuario.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(PesquisaUsuario.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mReciclePesquisaUsuario.setLayoutManager(llm);
    }

    private void listarUsuario() {

        aUser = new UsuarioDAO(PesquisaUsuario.this).pesquisaUsuario("%%");
        UsuarioAdapter adapter = new UsuarioAdapter(PesquisaUsuario.this, aUser, usuario);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mReciclePesquisaUsuario.setAdapter(adapter);

    }

    @Override
    public void onClickListener(View view, int position) {

        UsuarioAdapter adapter = (UsuarioAdapter) mReciclePesquisaUsuario.getAdapter();
        usuario = adapter.retornaUsuario(position);

        setResult(RESULT_OK, new Intent().putExtra("usuario", usuario));
        finish();
    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }
}
