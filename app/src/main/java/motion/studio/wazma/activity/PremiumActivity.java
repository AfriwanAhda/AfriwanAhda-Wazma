//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import motion.studio.wazma.R;
import motion.studio.wazma.manager.BillingManager;
import motion.studio.wazma.manager.PremiumManager;

/**
 * Premium/Paywall Activity - Attractive subscription screen
 */
public class PremiumActivity extends AppCompatActivity implements BillingManager.BillingCallback {
    
    private BillingManager billingManager;
    private PremiumManager premiumManager;
    
    private Button btnSubscribe;
    private ImageButton btnClose;
    private ProgressBar progressBar;
    private TextView tvPrice;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        
        premiumManager = PremiumManager.getInstance(this);
        billingManager = new BillingManager(this, this);
        
        initViews();
        setupListeners();
    }
    
    private void initViews() {
        btnSubscribe = findViewById(R.id.btnSubscribe);
        btnClose = findViewById(R.id.btnClose);
        progressBar = findViewById(R.id.progressBar);
        tvPrice = findViewById(R.id.tvPrice);
        
        // Set price
        tvPrice.setText("$0.99/week");
        
        progressBar.setVisibility(View.GONE);
    }
    
    private void setupListeners() {
        btnSubscribe.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            btnSubscribe.setEnabled(false);
            billingManager.launchPurchaseFlow(this);
        });
        
        btnClose.setOnClickListener(v -> finish());
        
        // Restore purchases button
        TextView tvRestore = findViewById(R.id.tvRestore);
        tvRestore.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            billingManager.queryPurchases();
        });
    }
    
    @Override
    public void onPurchaseSuccess() {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            btnSubscribe.setEnabled(true);
            Toast.makeText(this, "🎉 Selamat! Anda sekarang Premium!", Toast.LENGTH_LONG).show();
            setResult(RESULT_OK);
            finish();
        });
    }
    
    @Override
    public void onPurchaseFailed(String error) {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            btnSubscribe.setEnabled(true);
            Toast.makeText(this, "Pembelian gagal: " + error, Toast.LENGTH_SHORT).show();
        });
    }
    
    @Override
    public void onBillingSetupFinished() {
        runOnUiThread(() -> {
            // Check if already premium
            if (premiumManager.isPremium()) {
                Toast.makeText(this, "Anda sudah Premium sampai: " + 
                    premiumManager.getSubscriptionEndDate(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingManager != null) {
            billingManager.destroy();
        }
    }
}
