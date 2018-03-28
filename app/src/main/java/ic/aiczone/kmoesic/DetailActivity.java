package ic.aiczone.kmoesic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ic.aiczone.kmoesic.model.KamusModel;
import ic.aiczone.kmoesic.prefs.ConfigPrefs;

public class DetailActivity extends AppCompatActivity {

    private String ARG_PARCEL_LIST = "bundle_kamus";

    @BindView(R.id.tvKey) TextView tvKey;
    @BindView(R.id.tvDescription) TextView tvDesc;
    @BindView(R.id.tvData) TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
    }

    public void initData(){
        ConfigPrefs config = new ConfigPrefs(getBaseContext());
        String mTitle = getString(config.getKamusTitle());

        Bundle data = getIntent().getExtras();
        if(data != null){
            KamusModel kamus = (KamusModel) data.getParcelable(ARG_PARCEL_LIST);
            if(kamus != null){
                tvKey.setText(kamus.getKey());
                if(config.getKamusType() == R.string.tb_kamus_eng){
                    tvDesc.setText(R.string.lb_desc_eng);
                }else{
                    tvDesc.setText(R.string.lb_desc_ina);
                }
                tvData.setText(kamus.getData());
                mTitle = kamus.getKey() + " (" + mTitle + ")";
            }
        }

        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
