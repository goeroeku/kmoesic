package ic.aiczone.kmoesic.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ic.aiczone.kmoesic.R;

/**
 * Created by aic on 27/03/18.
 */

public class ConfigPrefs {
    SharedPreferences prefs;
    Context context;

    public ConfigPrefs(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.config_init);
        editor.putBoolean(key, input);
        editor.apply();
    }

    public Boolean getFirstRun() {
        String key = context.getResources().getString(R.string.config_init);
        return prefs.getBoolean(key, true);
    }

    public void setKamusType(int input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.config_kamus_type);
        editor.putInt(key, input);
        editor.putInt(key, input);
        key = context.getResources().getString(R.string.config_kamus_title);
        if (input == R.string.tb_kamus_eng) {
            editor.putInt(key, R.string.lb_nav_eng_ina);
        } else {
            editor.putInt(key, R.string.lb_nav_ina_eng);
        }

        editor.apply();
    }

    public int getKamusType() {
        String key = context.getResources().getString(R.string.config_kamus_type);
        return prefs.getInt(key, -1);
    }

    public int getKamusTitle() {
        String key = context.getResources().getString(R.string.config_kamus_title);
        return prefs.getInt(key, -1);
    }
}
