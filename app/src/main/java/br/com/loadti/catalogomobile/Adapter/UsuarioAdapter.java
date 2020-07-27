package br.com.loadti.catalogomobile.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.UsuarioSerial;
import br.com.loadti.catalogomobile.interfaces.RecyclerViewOnClickListenerHack;

/**
 * Created by TI on 14/04/2015.
 */
public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.MyViewHolder> {


    private List<UsuarioSerial> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private UsuarioSerial mUsuario;

    public UsuarioAdapter(Context c, List<UsuarioSerial> l, UsuarioSerial user) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mUsuario = user;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        View v = mLayoutInflater.inflate(R.layout.usuario_adapter, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {


        Log.i("LOG", "onBindViewHolder()");
        //myViewHolder.tvLogin.setText(mList.get(position).getLoginUser());
        myViewHolder.tvNome.setText(mList.get(position).getNomeUser());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public void addListItem(UsuarioSerial u, int positon) {
        mList.add(u);
        notifyItemInserted(positon);
    }

    public UsuarioSerial retornaUsuario(int positon) {

        mUsuario = mList.get(positon);
        return mUsuario;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvNome;

        public MyViewHolder(View itemView) {
            super(itemView);


            tvNome = (TextView) itemView.findViewById(R.id.tv_pesquisa_nome_usuario);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(v, getAdapterPosition());
            }
        }
    }


}
