package ic.aiczone.kmoesic;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ic.aiczone.kmoesic.database.KamusHelper;
import ic.aiczone.kmoesic.model.KamusModel;
import ic.aiczone.kmoesic.prefs.ConfigPrefs;

/**
 * Created by aic on 27/03/18.
 */

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        new LoadData().execute();

        ConfigPrefs config = new ConfigPrefs(getBaseContext());
        config.setKamusType(R.string.tb_kamus_eng);
    }

    private class LoadData extends AsyncTask<String, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        ConfigPrefs configPrefs;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(getBaseContext());
            configPrefs = new ConfigPrefs(getBaseContext());
        }

        /*
        * Process data from raw by param
        *
        * @param param1 address raw resource
        * */
        @Override
        protected Void doInBackground(String... params) {
            Boolean firstRun;

            firstRun = configPrefs.getFirstRun();
            if (firstRun) {

                runImport(R.string.tb_kamus_eng);
                runImport(R.string.tb_kamus_ina);

                configPrefs.setFirstRun(false);

                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(1000);

                        publishProgress(50);

                        this.wait(1000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        private void runImport(int type) {
            ArrayList<KamusModel> mRaws;
            if (type == R.string.tb_kamus_eng) {
                mRaws = preLoadRaw(R.raw.eng_ina);
            } else {
                mRaws = preLoadRaw(R.raw.ina_eng);
            }

            kamusHelper.open(getString(type));

            progress = 30;
            publishProgress((int) progress);
            Double progressMaxInsert = 80.0;
            Double progressDiff = (progressMaxInsert - progress) / mRaws.size();

            kamusHelper.beginTransaction();
            try {
                for (KamusModel model : mRaws) {
                    kamusHelper.addTrans(model);
                    progress += progressDiff;
                    publishProgress((int) progress);
                }

                kamusHelper.setTransactionSuccess();
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: Exception");
            }
            kamusHelper.endTransaction();

            kamusHelper.close();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRaw(int mRaw) {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(mRaw);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusModels;
    }
}
