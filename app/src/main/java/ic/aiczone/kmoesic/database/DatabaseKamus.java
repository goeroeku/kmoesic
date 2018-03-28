package ic.aiczone.kmoesic.database;

import android.provider.BaseColumns;

/**
 * Created by aic on 27/03/18.
 */

public class DatabaseKamus {
    static final String TABLE_NAME_ENG = "tb_kamus_eng";
    static final String TABLE_NAME_INA = "tb_kamus_ina";

    static final class Columns implements BaseColumns {
        static String KEY = "key";
        static String DATA = "data";
    }
}
