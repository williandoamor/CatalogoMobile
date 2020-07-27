package br.com.loadti.catalogomobile.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.CampanhaSerial;
import br.com.loadti.catalogomobile.interfaces.RecyclerViewOnClickListenerHack;


/**
 * Created by TI on 14/01/2016.
 */
public class CampanhaAdapter extends RecyclerView.Adapter<CampanhaAdapter.MyViewHolder> {

    private Context mContext;
    private List<CampanhaSerial> mList;
    private CampanhaSerial mCampanha;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public CampanhaAdapter(Context context, List<CampanhaSerial> camp) {

        mCampanha = new CampanhaSerial();
        mContext = context;
        mList = camp;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.campanha_adapter, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            holder.tvNomeCampanha.setText(mList.get(position).getNomeCampanha());

        } catch (Exception e) {


            throw e;

        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public CampanhaSerial retornaCampanha(int positon) {

        mCampanha = mList.get(positon);
        return mCampanha;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView tvNomeCampanha;


        public MyViewHolder(View itemView) {
            super(itemView);


            tvNomeCampanha = (TextView) itemView.findViewById(R.id.tv_pesquisa_campannha_nome_campanha);


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
