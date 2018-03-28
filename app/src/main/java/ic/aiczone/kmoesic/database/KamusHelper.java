package ic.aiczone.kmoesic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ic.aiczone.kmoesic.model.KamusModel;

import static android.provider.BaseColumns._ID;
import static ic.aiczone.kmoesic.database.DatabaseKamus.Columns.DATA;
import static ic.aiczone.kmoesic.database.DatabaseKamus.Columns.KEY;

/**
 * Created by aic on 27/03/18.
 */

public class KamusHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private String TABLE_NAME = "tb_kamus";

    private SQLiteDatabase db;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open(String table_name) throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
        this.TABLE_NAME = table_name;
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<KamusModel> getDataByKey(String key) {
        String result = "";
        Cursor cursor = db.query(TABLE_NAME, null, KEY + " LIKE ?", new String[]{"%" + key + "%"}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel KamusModel;
        if (cursor.getCount() > 0) {
            do {
                KamusModel = new KamusModel();
                KamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                KamusModel.setKey(cursor.getString(cursor.getColumnIndexOrThrow(KEY)));
                KamusModel.setData(cursor.getString(cursor.getColumnIndexOrThrow(DATA)));

                arrayList.add(KamusModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusModel> gets() {
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel KamusModel;
        if (cursor.getCount() > 0) {
            do {
                KamusModel = new KamusModel();
                KamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                KamusModel.setKey(cursor.getString(cursor.getColumnIndexOrThrow(KEY)));
                KamusModel.setData(cursor.getString(cursor.getColumnIndexOrThrow(DATA)));

                arrayList.add(KamusModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long add(KamusModel KamusModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY, KamusModel.getKey());
        initialValues.put(DATA, KamusModel.getData());
        return db.insert(TABLE_NAME, null, initialValues);
    }

    public void beginTransaction() {
        db.beginTransaction();
    }

    public void setTransactionSuccess() {
        db.setTransactionSuccessful();
    }

    public void endTransaction() {
        db.endTransaction();
    }

    public void addTrans(KamusModel KamusModel) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + KEY + ", " + DATA
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, KamusModel.getKey());
        stmt.bindString(2, KamusModel.getData());
        stmt.execute();
        stmt.clearBindings();
    }

    public int edit(KamusModel KamusModel) {
        ContentValues args = new ContentValues();
        args.put(KEY, KamusModel.getKey());
        args.put(DATA, KamusModel.getData());
        return db.update(TABLE_NAME, args, _ID + "= '" + KamusModel.getId() + "'", null);
    }

    public int del(int id) {
        return db.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }
}
