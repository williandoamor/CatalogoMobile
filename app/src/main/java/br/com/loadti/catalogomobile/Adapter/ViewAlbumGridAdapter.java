package br.com.loadti.catalogomobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.utilis.Numeric;
import br.com.loadti.catalogomobile.utilis.OrientacaoUtils;

/**
 * Created by TI on 17/02/2016.
 */
public class ViewAlbumGridAdapter extends BaseAdapter {

    private Context context;
    private List<ProdutoSerial> mLProduto;
    private LayoutInflater layoutInflater;
    private ConfigGeralSerial conf;
    private float scale;
    private Bitmap bitmapCache;

    private LruCache<String, Bitmap> mMemoryCache;


    private static BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

    private static float IMAGE_WIDTH = 100;
    private static float IMAGE_HEIGHT = 100;
    private static float IMAGE_PADDING = 6;
    private int mImageWidth;
    private int mImageHeight;
    private int mImagePadding;

    public ViewAlbumGridAdapter(Context ctx, List<ProdutoSerial> prod, ConfigGeralSerial config, int hei, int wid) {

        context = ctx;
        mLProduto = prod;
        this.conf = config;
        layoutInflater = LayoutInflater.from(context);


        scale = context.getResources().getDisplayMetrics().density;
        mImageWidth = (int) (IMAGE_WIDTH * scale);
        mImageHeight = (int) (IMAGE_HEIGHT * scale);
        mImagePadding = (int) (IMAGE_PADDING * scale);
        sBitmapOptions.inSampleSize = 2;

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
    public int getCount() {
        return mLProduto.size();
    }

    @Override
    public Object getItem(int position) {
        return mLProduto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        ImageView imageView;
        TextView tvProduto;
        TextView tvCifrao;
        TextView tvPreco;

        if (v == null) {

            v = layoutInflater.inflate(R.layout.item_album_gridview, parent, false);
            v.setTag(R.id.image_view, v.findViewById(R.id.image_view));
            v.setTag(R.id.tvItemAlbumProduto, v.findViewById(R.id.tvItemAlbumProduto));
            v.setTag(R.id.tvCifraoAlbum, v.findViewById(R.id.tvCifraoAlbum));
            v.setTag(R.id.tvValorItemAlbum, v.findViewById(R.id.tvValorItemAlbum));
        }

        imageView = (ImageView) v.getTag(R.id.image_view);
        tvProduto = (TextView) v.getTag(R.id.tvItemAlbumProduto);
        //tvCifrao = (TextView) v.getTag(R.id.tvCifraoAlbum);
        //tvPreco = (TextView) v.getTag(R.id.tvValorItemAlbum);

        /*Seta a imagem*/
        bitmapCache = getBitmapFromMemCache(mLProduto.get(position).getFotoProd());
        if (bitmapCache == null) {

            bitmapCache = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd(), sBitmapOptions);
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 190));
        //imageView.setLayoutParams(new LinearLayout.LayoutParams(mImageWidth, mImageHeight));
        imageView.setPadding(mImagePadding, mImagePadding, mImagePadding, mImagePadding);
        imageView.setImageBitmap(bitmapCache);
        imageView.setAdjustViewBounds(false);


        /*Exibe a descricao do produto*/
        tvProduto.setText(mLProduto.get(position).getDescricao().toString());


        /*Exibe o valor do produto*/
        if (conf.getMostrarPreco().equals("S")) {

            if (conf.getTabelaPadrao() == 0) {

                //tvCifrao.setVisibility(View.VISIBLE);
                //tvPreco.setVisibility(View.VISIBLE);
                // tvPreco.setText(Numeric.format(mLProduto.get(position).getPrevenda1(), conf.getCasaDecimalVenda()));

            } else if (conf.getTabelaPadrao() == 1) {

                // tvCifrao.setVisibility(View.VISIBLE);
                // tvPreco.setVisibility(View.VISIBLE);
                // tvPreco.setText(Numeric.format(mLProduto.get(position).getPrevenda2(), conf.getCasaDecimalVenda()));

            }

        }

        return v;


    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

}
