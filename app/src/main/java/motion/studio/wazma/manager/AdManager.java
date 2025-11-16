//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

/**
 * Manages AdMob advertisements for free tier users
 */
public class AdManager {
    private static final String TAG = "AdManager";
    
    // Test banner ad unit ID - replace with your actual ID in production
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
    
    private static AdManager instance;
    private Context context;
    private PremiumManager premiumManager;
    private boolean isInitialized = false;
    
    private AdManager(Context context) {
        this.context = context.getApplicationContext();
        this.premiumManager = PremiumManager.getInstance(context);
        initializeAds();
    }
    
    public static synchronized AdManager getInstance(Context context) {
        if (instance == null) {
            instance = new AdManager(context);
        }
        return instance;
    }
    
    private void initializeAds() {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                isInitialized = true;
                Log.d(TAG, "AdMob initialized successfully");
            }
        });
    }
    
    /**
     * Load and show banner ad in the given container
     * Only shows ads if user is not premium
     */
    public void showBannerAd(Activity activity, FrameLayout adContainer) {
        if (premiumManager.isPremium()) {
            // User is premium, hide ads
            if (adContainer != null) {
                adContainer.setVisibility(View.GONE);
            }
            return;
        }
        
        if (!isInitialized) {
            Log.w(TAG, "AdMob not initialized yet");
            return;
        }
        
        if (adContainer == null) {
            Log.w(TAG, "Ad container is null");
            return;
        }
        
        // Create AdView
        AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(BANNER_AD_UNIT_ID);
        
        // Add to container
        adContainer.removeAllViews();
        adContainer.addView(adView);
        adContainer.setVisibility(View.VISIBLE);
        
        // Load ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        
        Log.d(TAG, "Banner ad loaded");
    }
    
    /**
     * Hide ads (called when user becomes premium)
     */
    public void hideAds(FrameLayout adContainer) {
        if (adContainer != null) {
            adContainer.setVisibility(View.GONE);
            adContainer.removeAllViews();
        }
    }
}
