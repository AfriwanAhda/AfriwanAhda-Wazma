//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages premium subscription status
 */
public class PremiumManager {
    private static final String PREF_NAME = "wazma_premium";
    private static final String KEY_IS_PREMIUM = "is_premium";
    private static final String KEY_SUBSCRIPTION_END = "subscription_end";
    
    private static PremiumManager instance;
    private SharedPreferences prefs;
    
    private PremiumManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    public static synchronized PremiumManager getInstance(Context context) {
        if (instance == null) {
            instance = new PremiumManager(context.getApplicationContext());
        }
        return instance;
    }
    
    public boolean isPremium() {
        boolean isPremium = prefs.getBoolean(KEY_IS_PREMIUM, false);
        long endTime = prefs.getLong(KEY_SUBSCRIPTION_END, 0);
        
        // Check if subscription is still valid
        if (isPremium && endTime > 0) {
            if (System.currentTimeMillis() > endTime) {
                // Subscription expired
                setPremium(false, 0);
                return false;
            }
        }
        return isPremium;
    }
    
    public void setPremium(boolean isPremium, long endTimeMillis) {
        prefs.edit()
            .putBoolean(KEY_IS_PREMIUM, isPremium)
            .putLong(KEY_SUBSCRIPTION_END, endTimeMillis)
            .apply();
    }
    
    public long getSubscriptionEndTime() {
        return prefs.getLong(KEY_SUBSCRIPTION_END, 0);
    }
    
    public String getSubscriptionEndDate() {
        long endTime = getSubscriptionEndTime();
        if (endTime == 0) return "No active subscription";
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date(endTime));
    }
}
