package br.com.loadti.catalogomobile.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Serializable.ItensCampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.interfaces.RecyclerViewOnClickListenerHack;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.ImageHelper;

/**
 * Created by viniciusthiengo on 4/5/15.
 */
public class CardProdutoCampanhaAdapter extends RecyclerView.Adapter<CardProdutoCampanhaAdapter.MyViewHolder> {
    private Context mContext;
    private List<ProdutoSerial> mList;
    private LayoutInflater mLayoutInflater;
    private ConfigGeralSerial config;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;
    private ArrayList<ItensCampanhaSerial> aItensCampanha;
    private ProdutoSerial mProduto;
    private int posicao;


    public CardProdutoCampanhaAdapter(Context c, List<ProdutoSerial> l, ConfigGeralSerial conf, ArrayList<ItensCampanhaSerial> itens) {
        mContext = c;
        mList = l;
        config = conf;
        aItensCampanha = itens;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int) (14 * scale + 0.5f);
        height = (width / 16) * 9;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_campanha, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        if (isIncludeProduto(mList.get(position).getcodProd())) {

            myViewHolder.tvModel.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            //myViewHolder.tvModel.setText(mList.get(position).getDescricao());

        } else {

            myViewHolder.tvModel.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimarytext));
        }

        myViewHolder.tvModel.setText(mList.get(position).getDescricao());
        //tvModel.setText(mList.get(position).getDescricao());
        //myViewHolder.tvBrand.setText(mList.get(position).getFotoProd());

       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
       //     Bitmap bitmap = AndroidUtils.lerArquivo(mList.get(position).getFotoProd(), width, height);
       //     myViewHolder.ivCar.setImageBitmap(bitmap);
            //ivCar.setImageBitmap(bitmap);

       // } else {
      //      Bitmap bitmap = AndroidUtils.lerArquivo(mList.get(position).getFotoProd(), width, height);
       //     bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

            /*Arredonda as bordas da imagem*/
       //     bitmap = ImageHelper.getRoundedCornerBitmap(mContext, bitmap, 4, width, height, false, false, true, true);
       //     myViewHolder.ivCar.setImageBitmap(bitmap);
            //ivCar.setImageBitmap(bitmap);
        //}

       // try {
            // YoYo.with(Techniques.Tada)
            //         .duration(700)
            //        .playOn(myViewHolder.itemView);
       // } catch (Exception e) {
       // }

        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true;
        //bitmap = BitmapFactory.decodeFile(mList.get(position).getFotoProd(), options);
        // int i = options.outWidth;
        //if (i > width) {

        //    int widthRatio = Math.round((float) width / (float) i);
        //    options.inSampleSize = widthRatio;

        // }

        options.inJustDecodeBounds = false;
        Bitmap scaledBitmap = BitmapFactory.decodeFile(mList.get(position).getFotoProd(), options);
        //bitmap.createScaledBitmap(bitmap, width, height, false);
        //bitmap = ImageHelper.getRoundedCornerBitmap(mContext, bitmap, 4, width, height, false, false, true, true);
        // bitmap = AndroidUtils.lerArquivo(mList.get(position).getFotoProd(), width, height);
        //scaledBitmap = scaledBitmap.createScaledBitmap(scaledBitmap, width, height, true);
        myViewHolder.ivCar.setImageBitmap(scaledBitmap);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }


    public void addListItem(ProdutoSerial c, int position) {
        mList.add(c);
        notifyItemInserted(position);
    }

    public ProdutoSerial retornaProduto(int positon) {
        mProduto = mList.get(positon);
        //notifyItemChanged(positon);
        return mProduto;
    }

    public int getPosition() {
        return posicao;
    }

    public void setPosition(int position) {
        this.posicao = position;
    }

    /*Metodo para verifica se ja existe o item no pedido*/
    public boolean isIncludeProduto(String idProduto) {

        if (aItensCampanha != null) {

             /*Varrea a lista de itens ja inclusos no pedido*/
            for (int i = 0; i < aItensCampanha.size(); i++) {

            /*Verifica se exite algump produto na lista pelo id*/
                ProdutoSerial prod = aItensCampanha.get(i).getProduto();

            /*Se ouver retorna true*/
                if (prod.getcodProd().equals(idProduto)) {


                    return true;
                    //tvModel.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

                }/*Fim do fi que verifica se o id do produto é igual ao ID passado como parametro*/


            }/*Fim do for que varrea a lista de itens*/
        }
        return false;

    }


    public void removeListItem(int position) {
        //mList.remove(position);
        aItensCampanha.remove(position);
        //notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivCar;
        public TextView tvModel;
        public TextView tvBrand;

        public MyViewHolder(View itemView) {
            super(itemView);


            ivCar = (ImageView) itemView.findViewById(R.id.iv_car_campanha);
            tvModel = (TextView) itemView.findViewById(R.id.tv_model_campanha);
            //tvBrand = (TextView) itemView.findViewById(R.id.tv_brand_campanha);


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
