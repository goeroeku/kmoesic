package ic.aiczone.kmoesic.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static ic.aiczone.kmoesic.database.DatabaseKamus.Columns.DATA;
import static ic.aiczone.kmoesic.database.DatabaseKamus.Columns.KEY;
import static ic.aiczone.kmoesic.database.DatabaseKamus.TABLE_NAME_ENG;
import static ic.aiczone.kmoesic.database.DatabaseKamus.TABLE_NAME_INA;

/**
 * Created by aic on 27/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_ENG = "create table " + TABLE_NAME_ENG +
            " (" + _ID + " integer primary key autoincrement, " +
            KEY + " text not null, " +
            DATA + " text not null);";

    public static String CREATE_TABLE_INA = "create table " + TABLE_NAME_INA +
            " (" + _ID + " integer primary key autoincrement, " +
            KEY + " text not null, " +
            DATA + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENG);
        db.execSQL(CREATE_TABLE_INA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ENG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INA);
        onCreate(db);
    }
}
