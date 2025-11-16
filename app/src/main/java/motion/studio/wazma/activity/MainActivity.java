//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import motion.studio.wazma.R;
import motion.studio.wazma.adapter.ListAdapter;
import motion.studio.wazma.manager.AdManager;
import motion.studio.wazma.manager.PremiumManager;

/**
 * Created by Afriwan Ahda
 */

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PREMIUM = 100;

    private ArrayList<String> listSurah = new ArrayList<>();
    private PremiumManager premiumManager;
    private AdManager adManager;

    private Button btnUpgrade;
    private LinearLayout premiumBadge;
    private FrameLayout adContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportActionBar().setTitle("Wazma");

        // Initialize managers
        premiumManager = PremiumManager.getInstance(this);
        adManager = AdManager.getInstance(this);

        // Initialize views
        btnUpgrade = findViewById(R.id.btnUpgrade);
        premiumBadge = findViewById(R.id.premiumBadge);
        adContainer = findViewById(R.id.adContainer);

        ListView listView = findViewById(R.id.listPlayer);
        setListData();
        ListAdapter listAdapter = new ListAdapter(MainActivity.this, R.layout.simple_list_item, listSurah);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(MainActivity.this, PlayActivity.class);
                mIntent.putExtra("position", position);
                mIntent.putExtra("surah", listSurah.get(position));
                startActivity(mIntent);
            }
        });

        // Setup upgrade button
        btnUpgrade.setOnClickListener(v -> openPremiumScreen());

        // Update UI based on premium status
        updatePremiumUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePremiumUI();
    }

    private void updatePremiumUI() {
        if (premiumManager.isPremium()) {
            // User is premium
            btnUpgrade.setVisibility(View.GONE);
            premiumBadge.setVisibility(View.VISIBLE);
            adManager.hideAds(adContainer);
        } else {
            // User is free
            btnUpgrade.setVisibility(View.VISIBLE);
            premiumBadge.setVisibility(View.GONE);
            adManager.showBannerAd(this, adContainer);
        }
    }

    private void openPremiumScreen() {
        Intent intent = new Intent(this, PremiumActivity.class);
        startActivityForResult(intent, REQUEST_PREMIUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PREMIUM && resultCode == RESULT_OK) {
            updatePremiumUI();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_premium) {
            openPremiumScreen();
            return true;
        } else if (id == R.id.menu_prayer_times) {
            if (premiumManager.isPremium()) {
                startActivity(new Intent(this, PrayerTimesActivity.class));
            } else {
                openPremiumScreen();
            }
            return true;
        } else if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setListData() {
        listSurah.add("1. Al-Fatihah (Pembukaan) - 7 ayat");
        listSurah.add("78. An-Naba' (Berita besar) - 40 ayat");
        listSurah.add("79. An-Nazi'at (Malaikat² yang mencabut) - 46 ayat");
        listSurah.add("80. 'Abasa (Ia bermuka masam) - 42 ayat");
        listSurah.add("81. At-Takwir (Menggulung) - 29 ayat");
        listSurah.add("82. Al-Infitar (Terbelah) - 19 ayat");
        listSurah.add("83. Al Muthaffifin (Orang-orang yang curang) - 36 ayat");
        listSurah.add("84. Al-Insyiqaq (Terbelah) - 25 ayat");
        listSurah.add("85. Al-Buruj (Gugusan bintang) - 22 ayat");
        listSurah.add("86. At-Tariq (Yang datang di malam hari) - 17 ayat");
        listSurah.add("87. Al-A'la (Yang paling tinggi) - 19 ayat");
        listSurah.add("88. Al-Gasyiyah (Hari pembalasan) - 26 ayat");
        listSurah.add("89. Al-Fajr (Fajar) - 30 ayat");
        listSurah.add("90. Al-Balad (Negeri) - 20 ayat");
        listSurah.add("91. Asy-Syams (Matahari) - 15 ayat");
        listSurah.add("92. Al-Lail (Malam) - 21 ayat");
        listSurah.add("93. Ad-Duha (Waktu Dhuha) - 11 ayat");
        listSurah.add("94. Al-Insyirah (Melapangkan) - 8 ayat");
        listSurah.add("95. At-Tin (Buah Tin) - 8 ayat");
        listSurah.add("96. Al-'Alaq (Segumpal Darah) - 19 ayat");
        listSurah.add("97. Al-Qadr (Kemuliaan) - 5 ayat");
        listSurah.add("98. Al-Bayyinah (Pembuktian) - 8 ayat");
        listSurah.add("99. Az-Zalzalah (Kegoncangan) - 8 ayat");
        listSurah.add("100. Al-'Adiyat (Berlari kencang) - 11 ayat");
        listSurah.add("101. Al-Qari'ah (Hari kiamat) - 11 ayat");
        listSurah.add("102. At-Takasur (Bermegah-megahan) - 8 ayat");
        listSurah.add("103. Al-Ashr (Masa/waktu) - 3 ayat");
        listSurah.add("104. Al-Humazah (Pengumpat) - 9 ayat");
        listSurah.add("105. Al-Fil (Gajah) - 5 ayat");
        listSurah.add("106. Quraisy (Kaum Quraisy) - 4 ayat");
        listSurah.add("107. Al-Ma'un (Barang-barang yang berguna) - 7 ayat");
        listSurah.add("108. Al-Kausar (Nikmat yang berlimpah) - 3 ayat");
        listSurah.add("109. Al-Kafirun (Orang-orang kafir) - 6 ayat");
        listSurah.add("110. An-Nasr (Pertolongan) - 3 ayat");
        listSurah.add("111. Al-Lahab (Gejolak Api) - 5 ayat");
        listSurah.add("112. Al-Ikhlas (Ikhlas) - 4 ayat");
        listSurah.add("113. Al-Falaq (Waktu Subuh) - 4 ayat");
        listSurah.add("114. An-Nas (Umat manusia) - 6 ayat");
    }

}