package br.com.loadti.catalogomobile.Adapter;


import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Serializable.ItensCampanhaSerial;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.interfaces.RecyclerViewOnClickListenerHack;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.ImageHelper;
import br.com.loadti.catalogomobile.utilis.Numeric;

import android.view.Display;

/**
 * Created by viniciusthiengo on 4/5/15.
 */
public class CardProdutoAdapter extends RecyclerView.Adapter<CardProdutoAdapter.MyViewHolder> {
    private Context mContext;
    private List<ProdutoSerial> mList;
    private LayoutInflater mLayoutInflater;
    private ConfigGeralSerial config;
    private ArrayList<ItensCampanhaSerial> ItensCampanha;
    private String tela = "";
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;
    private ProdutoSerial mProduto;
    private LruCache<String, Bitmap> mMemoryCache;
    private Bitmap bitmapCache;
    private static BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

    public CardProdutoAdapter(Context c, List<ProdutoSerial> l, ConfigGeralSerial conf) {
        mContext = c;
        mList = l;
        config = conf;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int) (14 * scale + 0.5f);
        height = (width / 16) * 9;
        sBitmapOptions.inSampleSize = 2;

             /*Metodo para realizar o cache do Bitmap*/
        /*Faz cache do bitmap na memoria do dispositivo*/
        /*Faz cache do bitmap na memoria do dispositivo*/
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

    }

    /*Adapter para pintar os itens ja escolhidos na lista de produtos*/
    public CardProdutoAdapter(Context c, List<ProdutoSerial> l, ConfigGeralSerial conf, ArrayList<ItensCampanhaSerial> itens, String tela) {
        mContext = c;
        mList = l;
        config = conf;
        ItensCampanha = itens;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tela = tela;

        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int) (14 * scale + 0.5f);
        height = (width / 16) * 9;
        sBitmapOptions.inSampleSize = 2;


        /*Metodo para realizar o cache do Bitmap*/
        /*Faz cache do bitmap na memoria do dispositivo*/
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        try {

            // if (!tela.equals("camp")) {

            //     if (config.getVisualizacao() == 1) {
            //         RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            //         myViewHolder.ivCardView.setLayoutParams(params);

            //          RelativeLayout.LayoutParams imgparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 450);
            //          myViewHolder.ivCar.setLayoutParams(imgparams);

            //          myViewHolder.tvModel.setGravity(View.TEXT_ALIGNMENT_CENTER);
            //      }

            //}


            /*Seta o nome do produto*/
            myViewHolder.tvModel.setText(mList.get(position).getDescricao());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                /*Pega o bitmap que esta no cache*/
                bitmapCache = getBitmapFromMemCache(mList.get(position).getFotoProd().toString());


                /*Se o bitmapCache for nullo significa que nao exite o bitmap nao cache
                * entao monta o bitmap normalmente
                * */
                if (bitmapCache == null) {

                    bitmapCache = BitmapFactory.decodeFile(mList.get(position).getFotoProd(), sBitmapOptions);
                    addBitmapToMemoryCache(mList.get(position).getFotoProd(), bitmapCache);
                }


                myViewHolder.ivCar.setImageBitmap(bitmapCache);
                myViewHolder.ivCar.setAdjustViewBounds(true);


            } else {

                 /*Pega o bitmap que esta no cache*/
                bitmapCache = getBitmapFromMemCache(mList.get(position).getFotoProd().toString());
                /*Se o bitmapCache for nullo significa que nao exite o bitmap nao cache
                * entao monta o bitmap normalmente
                * */
                if (bitmapCache == null) {

                    bitmapCache = BitmapFactory.decodeFile(mList.get(position).getFotoProd(), sBitmapOptions);
                    addBitmapToMemoryCache(mList.get(position).getFotoProd(), bitmapCache);
                }

                //bitmapCache = Bitmap.createScaledBitmap(bitmapCache, width, height, false);


               /*Arredonda as bordas da imagem*/
                //bitmapCache = ImageHelper.getRoundedCornerBitmap(mContext, bitmapCache, 4, width, height, false, false, true, true);
                myViewHolder.ivCar.setImageBitmap(bitmapCache);
                myViewHolder.ivCar.setAdjustViewBounds(true);

            }

            /*Verifica se o usuario quer exibir o preco de venda do produto*/
            if (config.getMostrarPreco().equals("S")) {

                /*Em caso afirmativo verifica qual tabela de preco e a padrao*/
                if (config.getTabelaPadrao() == 0) {

                    myViewHolder.tvBrand.setText(Numeric.format(mList.get(position).getPrevenda1(), config.getCasaDecimalVenda()));

                }

                if (config.getTabelaPadrao() == 1) {

                    myViewHolder.tvBrand.setText(Numeric.format(mList.get(position).getPrevenda2(), config.getCasaDecimalVenda()));


                }
            }/*Fim do if que verifica se o usuaro quer exibir a tabela de preco*/ else {

                myViewHolder.tvModel.setTextSize(24);

            }


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


    public void addListItem(ProdutoSerial c, int position) {
        mList.add(c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public ProdutoSerial retornaProduto(int positon) {
        mProduto = mList.get(positon);
        //notifyItemChanged(positon);
        return mProduto;
    }


    /*Adiciona o bitmap no cache de memoria*/
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView ivCardView;
        public ImageView ivCar;
        public TextView tvModel;
        public TextView tvBrand;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivCardView = (CardView) itemView.findViewById(R.id.cardFotos);

            ivCar = (ImageView) itemView.findViewById(R.id.iv_car);
            tvModel = (TextView) itemView.findViewById(R.id.tv_model);
            tvBrand = (TextView) itemView.findViewById(R.id.tv_brand);

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
