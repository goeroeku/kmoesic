package ic.aiczone.kmoesic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import ic.aiczone.kmoesic.adapter.KamusAdapter;
import ic.aiczone.kmoesic.base.BaseActivity;
import ic.aiczone.kmoesic.database.KamusHelper;
import ic.aiczone.kmoesic.model.KamusModel;
import ic.aiczone.kmoesic.prefs.ConfigPrefs;

public class MainActivity extends BaseActivity {

    ListView listView;
    KamusAdapter adapter;

    private String ARG_PARCEL_LIST = "bundle_kamus";
    private String mType;
    private String mHintSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mType = getString(R.string.tb_kamus_eng);
        mHintSearch = getString(R.string.hint_search_eng);
        ConfigPrefs config = new ConfigPrefs(getBaseContext());
        int type = config.getKamusType();
        if (type == R.string.tb_kamus_ina) {
            mType = getString(R.string.tb_kamus_ina);
        }

        String mTitle = getString(config.getKamusTitle());
        getSupportActionBar().setTitle(mTitle);

        adapter = new KamusAdapter(this);
        adapter.setOnItemClickListener(new KamusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, KamusModel obj, int position) {
                Intent v = new Intent(getBaseContext(), DetailActivity.class);
                v.putExtra(ARG_PARCEL_LIST, obj);
                startActivity(v);
            }
        });

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        initData();
    }

    private void initData() {
        KamusHelper kh = new KamusHelper(getBaseContext());
        kh.open(mType);
        ArrayList<KamusModel> km = kh.gets();
        kh.close();
        adapter.setData(km);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(mHintSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String qry) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String qry) {
                KamusHelper kh = new KamusHelper(getBaseContext());
                kh.open(mType);
                ArrayList<KamusModel> km = kh.getDataByKey(qry);
                kh.close();
                adapter.setData(km);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                initData();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        super.onNavigationItemSelected(item);

        int id = item.getItemId();

        ConfigPrefs configPrefs = new ConfigPrefs(getBaseContext());
        if (id == R.id.nv_eng_ina) {
            configPrefs.setKamusType(R.string.tb_kamus_eng);
        } else {
            configPrefs.setKamusType(R.string.tb_kamus_ina);
        }
        Intent v = new Intent(getBaseContext(), MainActivity.class);
        startActivity(v);
        finish();

        return true;
    }

}
