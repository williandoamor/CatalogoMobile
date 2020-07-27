package br.com.loadti.catalogomobile.utilis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by TI on 15/02/2016.
 */

public class DatabaseManager {
    /**
     * Essa classe faz com que o Android consigo utlizar o processamet
     * concorrente junto ao banco de dados
     **/
    private AtomicInteger mOpenCounter = new AtomicInteger();

    private static DatabaseManager instance;
    private static DB bd;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(Context ctx) {
        if (instance != null) {

            bd = new DB(ctx);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {

            instance = new DatabaseManager();
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            //Abre o banco de dados em modo leitura
            mDatabase = bd.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            //Fecha o banco de dados
            if (mDatabase.inTransaction()) {

                mDatabase.endTransaction();
            }
            mDatabase.close();
            bd.close();

        }
    }
}
