package motion.studio.wazma.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import motion.studio.wazma.R;

/**
 * Created by Afriwan Ahda
 */

public class PlayActivity extends AppCompatActivity {
    private TextView title;
    private Button btnPlay, btnPause, btnStop;
    private MediaPlayer mp;
    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        initiate();
        stateAwal();
        setListener();
    }

    public void initiate() {
        getSupportActionBar().setTitle("Al Qur'an - Juz Amma");
        mp = new MediaPlayer();
        title = findViewById(R.id.tvTitle);
        btnPlay = findViewById(R.id.btnPLAY);
        btnPause = findViewById(R.id.btnPAUSE);
        btnStop = findViewById(R.id.btnSTOP);
        Intent intent = getIntent();
        index = intent.getIntExtra("position", 0);
        title.setText(intent.getStringExtra("surah"));
    }

    public void setListener() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = 0;
                if (index==0) {
                    item = getResources().getIdentifier("a" + (index+1), "raw", getPackageName());
                } else {
                    item = getResources().getIdentifier("a" + (index+77), "raw", getPackageName());
                }
                play(item);
                statePlay();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
    }

    /** State Awal */
    public void stateAwal(){
        btnPlay.setEnabled(true);
        btnPlay.setTextColor(getResources().getColor(R.color.white));

        btnPause.setEnabled(false);
        btnPause.setTextColor(getResources().getColor(R.color.black));

        btnStop.setEnabled(false);
        btnStop.setTextColor(getResources().getColor(R.color.black));
    }

    /** Dijalankan Oleh Tombol Play */
    public void play(int item) {
        /** Memanggil File MP3 */
        mp = MediaPlayer.create(this, item);

        try {
            mp.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /** Menjalankan Audio */
        mp.start();

        /** Penanganan Ketika Suara Berakhir */
        mp.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stateAwal();
            }
        });
    }

    /** Dijalankan Oleh Tombol Pause */
    public void pause(){
        if(mp.isPlaying()){
            if(mp!=null){
                mp.pause();
            }
        } else {
            if(mp!=null){
                mp.start();
            }
        }
    }

    /** Dijalankan Oleh Tombol Stop */
    public void stop(){
        mp.stop();
        try{
            mp.prepare();
            mp.seekTo(0);
        }catch (Throwable t) {
            t.printStackTrace();
        }
        stateAwal();
    }

    public void statePlay() {
        btnPlay.setEnabled(false);
        btnPlay.setTextColor(getResources().getColor(R.color.black));

        btnPause.setEnabled(true);
        btnPause.setTextColor(getResources().getColor(R.color.white));

        btnStop.setEnabled(true);
        btnStop.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stop();
    }
}