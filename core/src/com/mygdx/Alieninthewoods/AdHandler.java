package com.mygdx.Alieninthewoods;

/**
 * Created by Ondra on 08.01.2017.
 */
public interface AdHandler {
    public void showBannerAd();
    public void hideBannerAd();
    public boolean isWifiConnected();
    public void showInterstitialAd (Runnable then);
}
