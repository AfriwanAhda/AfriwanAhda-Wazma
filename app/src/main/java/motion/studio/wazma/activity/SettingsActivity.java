//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import motion.studio.wazma.R;
import motion.studio.wazma.manager.PremiumManager;

/**
 * Settings Activity
 */
public class SettingsActivity extends AppCompatActivity {
    
    private PremiumManager premiumManager;
    private SharedPreferences prefs;
    
    private Switch switchDarkMode;
    private TextView tvPremiumStatus;
    private Button btnManageSubscription;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        premiumManager = PremiumManager.getInstance(this);
        prefs = getSharedPreferences("wazma_settings", MODE_PRIVATE);
        
        initViews();
        loadSettings();
    }
    
    private void initViews() {
        switchDarkMode = findViewById(R.id.switchDarkMode);
        tvPremiumStatus = findViewById(R.id.tvPremiumStatus);
        btnManageSubscription = findViewById(R.id.btnManageSubscription);
        
        // Dark mode toggle (Premium feature)
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!premiumManager.isPremium()) {
                // Revert the switch
                buttonView.setChecked(!isChecked);
                Toast.makeText(this, "Dark Mode is a Premium feature", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, PremiumActivity.class));
                return;
            }
            
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
            applyDarkMode(isChecked);
        });
        
        // Manage subscription button
        btnManageSubscription.setOnClickListener(v -> {
            if (premiumManager.isPremium()) {
                Toast.makeText(this, "Opening subscription management...", Toast.LENGTH_SHORT).show();
                // TODO: Open Google Play subscription management
            } else {
                startActivity(new Intent(this, PremiumActivity.class));
            }
        });
        
        updatePremiumStatus();
    }
    
    private void loadSettings() {
        boolean darkMode = prefs.getBoolean("dark_mode", false);
        switchDarkMode.setChecked(darkMode);
        
        // Only apply dark mode if premium
        if (premiumManager.isPremium() && darkMode) {
            applyDarkMode(true);
        }
    }
    
    private void applyDarkMode(boolean enable) {
        if (enable) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    
    private void updatePremiumStatus() {
        if (premiumManager.isPremium()) {
            tvPremiumStatus.setText("✨ Premium Active\nExpires: " + premiumManager.getSubscriptionEndDate());
            btnManageSubscription.setText("Manage Subscription");
        } else {
            tvPremiumStatus.setText("Free Version\nUpgrade to unlock all features");
            btnManageSubscription.setText("Upgrade to Premium");
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updatePremiumStatus();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
