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
public class ViewAlbumGridAdapterOld extends BaseAdapter {

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

    public ViewAlbumGridAdapterOld(Context ctx, List<ProdutoSerial> prod, ConfigGeralSerial config, int hei, int wid) {

        context = ctx;
        mLProduto = prod;
        this.conf = config;
        layoutInflater = LayoutInflater.from(context);

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
        tvCifrao = (TextView) v.getTag(R.id.tvCifraoAlbum);
        tvPreco = (TextView) v.getTag(R.id.tvValorItemAlbum);


        if (OrientacaoUtils.isVertical(context)) {

            //Bitmap bmp = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd());
            // bmp.createScaledBitmap(bmp, width, height, false);
            // imageView.setImageBitmap(bmp);
            //imageView.setImageBitmap(ajustaFoto(mLProduto.get(position).getFotoProd()));
            // imageView.setImageBitmap(LoadAndResizeBitmap(mLProduto.get(position).getFotoProd(), width, height));
            // BitmapFactory.Options options = new BitmapFactory.Options();
            //  options.inJustDecodeBounds = false;
            //  Bitmap scaledBitmap = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd(), options);
            //  imageView.setImageBitmap(scaledBitmap);
            //  imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inJustDecodeBounds = false;
            //Bitmap scaledBitmap = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd(), options);
            // imageView.setImageBitmap(scaledBitmap);
            // imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 700));
            // imageView.setScaleType(ImageView.ScaleType.FIT_END);
            //imageView.setImageBitmap(scaledBitmap);
            /*Celular com tela pequena*/
            if (y <= 320) {

                Bitmap bitmapCache = getBitmapFromMemCache(mLProduto.get(position).getFotoProd());
                if (bitmapCache != null) {
                    imageView.setImageBitmap(bitmapCache);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 190));
                    imageView.setScaleType(ImageView.ScaleType.FIT_END);
                    imageView.setImageBitmap(bitmapCache);
                } else {
                    //Bitmap bitmap1 = new getBitmaptask().execute(mLProduto.get(position).getFotoProd());
                    //getBitmaptask tast = new getBitmaptask();
                    // tast.execute(mLProduto.get(position).getFotoProd());
                    Bitmap bitmap = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd());
                    addBitmapToMemoryCache(mLProduto.get(position).getFotoProd(), bitmap);
                    imageView.setImageBitmap(bitmap);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 190));
                    imageView.setScaleType(ImageView.ScaleType.FIT_END);
                    imageView.setImageBitmap(bitmap);
                }
            } else if (y > 320) {

                Bitmap bitmapCache = getBitmapFromMemCache(mLProduto.get(position).getFotoProd());
                if (bitmapCache != null) {
                    imageView.setImageBitmap(bitmapCache);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 700));
                    imageView.setScaleType(ImageView.ScaleType.FIT_END);
                    imageView.setImageBitmap(bitmapCache);
                } else {
                    //Bitmap bitmap1 = new getBitmaptask().execute(mLProduto.get(position).getFotoProd());
                    //getBitmaptask tast = new getBitmaptask();
                    // tast.execute(mLProduto.get(position).getFotoProd());
                    Bitmap bitmap = BitmapFactory.decodeFile(mLProduto.get(position).getFotoProd());
                    addBitmapToMemoryCache(mLProduto.get(position).getFotoProd(), bitmap);
                    imageView.setImageBitmap(bitmap);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 700));
                    imageView.setScaleType(ImageView.ScaleType.FIT_END);
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
