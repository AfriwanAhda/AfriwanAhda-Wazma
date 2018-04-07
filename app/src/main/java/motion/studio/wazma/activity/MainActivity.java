//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import motion.studio.wazma.R;
import motion.studio.wazma.adapter.ListAdapter;

/**
 * Created by Afriwan Ahda
 */

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> listSurah = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportActionBar().setTitle("Wazma");
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