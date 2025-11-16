//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;

import com.android.billingclient.api.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages Google Play Billing operations
 */
public class BillingManager implements PurchasesUpdatedListener {
    private static final String TAG = "BillingManager";
    
    // Product ID for weekly subscription - $0.99/week
    public static final String WEEKLY_SUBSCRIPTION_ID = "wazma_premium_weekly";
    
    private BillingClient billingClient;
    private Context context;
    private PremiumManager premiumManager;
    private BillingCallback billingCallback;
    
    public interface BillingCallback {
        void onPurchaseSuccess();
        void onPurchaseFailed(String error);
        void onBillingSetupFinished();
    }
    
    public BillingManager(Context context, BillingCallback callback) {
        this.context = context;
        this.billingCallback = callback;
        this.premiumManager = PremiumManager.getInstance(context);
        
        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases()
            .build();
            
        startConnection();
    }
    
    private void startConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Billing setup successful");
                    queryPurchases();
                    if (billingCallback != null) {
                        billingCallback.onBillingSetupFinished();
                    }
                } else {
                    Log.e(TAG, "Billing setup failed: " + billingResult.getDebugMessage());
                }
            }
            
            @Override
            public void onBillingServiceDisconnected() {
                Log.w(TAG, "Billing service disconnected");
                // Try to restart connection
                startConnection();
            }
        });
    }
    
    public void queryPurchases() {
        if (!billingClient.isReady()) {
            Log.w(TAG, "Billing client not ready");
            return;
        }
        
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build(),
            (billingResult, purchaseList) -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    handlePurchases(purchaseList);
                }
            }
        );
    }
    
    private void handlePurchases(List<Purchase> purchases) {
        if (purchases == null || purchases.isEmpty()) {
            premiumManager.setPremium(false, 0);
            return;
        }
        
        for (Purchase purchase : purchases) {
            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                if (!purchase.isAcknowledged()) {
                    acknowledgePurchase(purchase);
                }
                
                // Set premium status
                // Weekly subscription: 7 days
                long endTime = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L);
                premiumManager.setPremium(true, endTime);
                Log.d(TAG, "Premium subscription active");
            }
        }
    }
    
    private void acknowledgePurchase(Purchase purchase) {
        AcknowledgePurchaseParams params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.getPurchaseToken())
            .build();
            
        billingClient.acknowledgePurchase(params, billingResult -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                Log.d(TAG, "Purchase acknowledged");
            }
        });
    }
    
    public void launchPurchaseFlow(Activity activity) {
        if (!billingClient.isReady()) {
            Log.w(TAG, "Billing client not ready");
            if (billingCallback != null) {
                billingCallback.onPurchaseFailed("Billing not ready. Please try again.");
            }
            return;
        }
        
        List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(WEEKLY_SUBSCRIPTION_ID)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        );
        
        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build();
            
        billingClient.queryProductDetailsAsync(params, (billingResult, productDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK 
                && productDetailsList != null && !productDetailsList.isEmpty()) {
                
                ProductDetails productDetails = productDetailsList.get(0);
                
                List<ProductDetailsParams> productDetailsParamsList = new ArrayList<>();
                
                // Get subscription offer details
                if (productDetails.getSubscriptionOfferDetails() != null 
                    && !productDetails.getSubscriptionOfferDetails().isEmpty()) {
                    
                    String offerToken = productDetails.getSubscriptionOfferDetails().get(0).getOfferToken();
                    
                    productDetailsParamsList.add(
                        ProductDetailsParams.newBuilder()
                            .setProductDetails(productDetails)
                            .setOfferToken(offerToken)
                            .build()
                    );
                    
                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                        .setProductDetailsParamsList(productDetailsParamsList)
                        .build();
                        
                    billingClient.launchBillingFlow(activity, billingFlowParams);
                }
            } else {
                Log.e(TAG, "Failed to query product details");
                if (billingCallback != null) {
                    billingCallback.onPurchaseFailed("Product not available");
                }
            }
        });
    }
    
    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases);
            if (billingCallback != null) {
                billingCallback.onPurchaseSuccess();
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d(TAG, "User canceled purchase");
            if (billingCallback != null) {
                billingCallback.onPurchaseFailed("Purchase canceled");
            }
        } else {
            Log.e(TAG, "Purchase failed: " + billingResult.getDebugMessage());
            if (billingCallback != null) {
                billingCallback.onPurchaseFailed("Purchase failed: " + billingResult.getDebugMessage());
            }
        }
    }
    
    public void destroy() {
        if (billingClient != null && billingClient.isReady()) {
            billingClient.endConnection();
        }
    }
}
