package br.com.loadti.catalogomobile.Adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import br.com.loadti.catalogomobile.R;
import br.com.loadti.catalogomobile.Serializable.ConfigGeralSerial;
import br.com.loadti.catalogomobile.Serializable.ProdutoSerial;
import br.com.loadti.catalogomobile.utilis.AndroidUtils;
import br.com.loadti.catalogomobile.utilis.Numeric;
import br.com.loadti.catalogomobile.utilis.OrientacaoUtils;

/**
 * Created by TI on 08/02/2016.
 */
public class ViewAlbumAdapter extends PagerAdapter {

    private Context context;
    private List<ProdutoSerial> mLProduto;
    private LayoutInflater layoutInflater;
    private ConfigGeralSerial conf;
    private float scale;
    private int width;
    private int height;
    private Activity activity;
    private int x;
    private int y;

    private LruCache<String, Bitmap> mMemoryCache;
    private Bitmap teste;

    public ViewAlbumAdapter(Context ctx, List<ProdutoSerial> prod, ConfigGeralSerial config, int hei, int wid) {

        context = ctx;
        mLProduto = prod;
        this.conf = config;

        y = hei;
        x = wid;

        scale = context.getResources().getDisplayMetrics().density;
        width = context.getResources().getDisplayMetrics().widthPixels - (int) (14 * scale + 0.5f);
        height = (width / 16) * 9;

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
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        return (0.5f);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.item_album, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.image_view);
        TextView tvProduto = (TextView) v.findViewById(R.id.tvItemAlbumProduto);
        TextView tvCifrao = (TextView) v.findViewById(R.id.tvCifraoAlbum);
        TextView tvPreco = (TextView) v.findViewById(R.id.tvValorItemAlbum);

        /*Verifica se o usuario colocou a tela na vertical*/
        if (OrientacaoUtils.isVertical(context)) {
            /*Celular com tela pequena*/
            if (y <= 320) {

                /*Verifica se o bitmap esta no cache
                * se tiver pega a imagem do cache de memoria
                * */
                Bitmap bitmapCache = getBitmapFromMemCache(mLProduto.get(position).getFotoProd());
                if (bitmapCache != null) {
                    imageView.setImageBitmap(bitmapCache);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(bitmapCache);
                } else {

                    Bitmap bitmap = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd());
                    addBitmapToMemoryCache(mLProduto.get(position).getFotoProd(), bitmap);
                    imageView.setImageBitmap(bitmap);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(bitmap);
                }
            } else if (y > 320) {

                Bitmap bitmapCache = getBitmapFromMemCache(mLProduto.get(position).getFotoProd());
                if (bitmapCache != null) {
                    imageView.setImageBitmap(bitmapCache);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(bitmapCache);
                } else {

                    Bitmap bitmap = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd());
                    addBitmapToMemoryCache(mLProduto.get(position).getFotoProd(), bitmap);
                    imageView.setImageBitmap(bitmap);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    //imageView.setScaleType(ImageView.ScaleType.FIT_END);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(bitmap);
                }

            }


        } else {

            //BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inJustDecodeBounds = false;
            //Bitmap scaledBitmap = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd(), options);
            //imageView.setImageBitmap(scaledBitmap);
            Bitmap bitmapCache = getBitmapFromMemCache(mLProduto.get(position).getFotoProd());
            if (bitmapCache != null) {
                imageView.setImageBitmap(bitmapCache);
            } else {
                // Bitmap bitmap1 = new getBitmaptask().execute(mLProduto.get(position).getFotoProd());
                //getBitmaptask tast = new getBitmaptask();
                // tast.execute(mLProduto.get(position).getFotoProd());
                Bitmap bitmap = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd());
                addBitmapToMemoryCache(mLProduto.get(position).getFotoProd(), bitmap);
                imageView.setImageBitmap(bitmap);

            }


        }



        /*Exibe a descricao do produto*/
        tvProduto.setText(mLProduto.get(position).getDescricao().toString());


        /*Exibe o valor do produto*/
        if (conf.getMostrarPreco().equals("S")) {

            if (conf.getTabelaPadrao() == 0) {

                tvCifrao.setVisibility(View.VISIBLE);
                tvPreco.setVisibility(View.VISIBLE);
                tvPreco.setText(Numeric.format(mLProduto.get(position).getPrevenda1(), conf.getCasaDecimalVenda()));

            } else if (conf.getTabelaPadrao() == 1) {

                tvCifrao.setVisibility(View.VISIBLE);
                tvPreco.setVisibility(View.VISIBLE);
                tvPreco.setText(Numeric.format(mLProduto.get(position).getPrevenda2(), conf.getCasaDecimalVenda()));

            }

        }

        container.addView(v);
        return v;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
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
