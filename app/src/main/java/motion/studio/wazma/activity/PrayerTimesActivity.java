//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import motion.studio.wazma.R;
import motion.studio.wazma.utils.PrayerTimesCalculator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

/**
 * Prayer Times Activity - Premium Feature
 * Shows prayer times based on user's location
 */
public class PrayerTimesActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST = 200;
    
    private FusedLocationProviderClient fusedLocationClient;
    private TextView tvLocation, tvDate;
    private TextView tvFajr, tvDhuhr, tvAsr, tvMaghrib, tvIsha;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_times);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Prayer Times");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        initViews();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        
        // Set current date
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
        tvDate.setText(sdf.format(Calendar.getInstance().getTime()));
        
        requestLocationAndCalculate();
    }
    
    private void initViews() {
        tvLocation = findViewById(R.id.tvLocation);
        tvDate = findViewById(R.id.tvDate);
        tvFajr = findViewById(R.id.tvFajr);
        tvDhuhr = findViewById(R.id.tvDhuhr);
        tvAsr = findViewById(R.id.tvAsr);
        tvMaghrib = findViewById(R.id.tvMaghrib);
        tvIsha = findViewById(R.id.tvIsha);
    }
    
    private void requestLocationAndCalculate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST);
            return;
        }
        
        getLocationAndCalculate();
    }
    
    private void getLocationAndCalculate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
            != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        
                        tvLocation.setText(String.format(Locale.US, "%.4f, %.4f", latitude, longitude));
                        
                        // Calculate prayer times
                        Map<String, String> prayerTimes = PrayerTimesCalculator.calculate(latitude, longitude);
                        
                        tvFajr.setText(prayerTimes.get("Fajr"));
                        tvDhuhr.setText(prayerTimes.get("Dhuhr"));
                        tvAsr.setText(prayerTimes.get("Asr"));
                        tvMaghrib.setText(prayerTimes.get("Maghrib"));
                        tvIsha.setText(prayerTimes.get("Isha"));
                    } else {
                        Toast.makeText(PrayerTimesActivity.this, 
                            "Could not get location. Please enable GPS.", Toast.LENGTH_LONG).show();
                        // Use default location (Jakarta, Indonesia)
                        useDefaultLocation();
                    }
                }
            });
    }
    
    private void useDefaultLocation() {
        // Jakarta coordinates
        double latitude = -6.2088;
        double longitude = 106.8456;
        
        tvLocation.setText("Jakarta, Indonesia (Default)");
        
        Map<String, String> prayerTimes = PrayerTimesCalculator.calculate(latitude, longitude);
        
        tvFajr.setText(prayerTimes.get("Fajr"));
        tvDhuhr.setText(prayerTimes.get("Dhuhr"));
        tvAsr.setText(prayerTimes.get("Asr"));
        tvMaghrib.setText(prayerTimes.get("Maghrib"));
        tvIsha.setText(prayerTimes.get("Isha"));
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
                                          @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationAndCalculate();
            } else {
                Toast.makeText(this, "Location permission denied. Using default location.", 
                    Toast.LENGTH_LONG).show();
                useDefaultLocation();
            }
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
